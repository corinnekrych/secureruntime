package security;

import groovy.lang.Closure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GroovyAccessControl {

    // methods for a given receiver, syntax like MyReceiver.myMethod
    private final List<String> methodsOnReceiverWhitelist;
    private final List<String> methodsOnReceiverBlacklist;

    public GroovyAccessControl() {
        System.out.println("in default constructor");
        this.methodsOnReceiverWhitelist = new ArrayList<String>();
        methodsOnReceiverWhitelist.add("Script1.println");
        this.methodsOnReceiverBlacklist = null;
    }

    public GroovyAccessControl(List<String> whitelist, List<String> blacklist) {
        this.methodsOnReceiverWhitelist = Collections.unmodifiableList(whitelist);
        this.methodsOnReceiverBlacklist = Collections.unmodifiableList(blacklist);
    }

//    public void setMethodsOnReceiverBlacklist(final List<String> methodsOnReceiverBlacklist) {
//        this.methodsOnReceiverBlacklist = methodsOnReceiverBlacklist;
//    }
//
//    public void setMethodsOnReceiverWhitelist(final List<String> methodsOnReceiverWhitelist) {
//        this.methodsOnReceiverWhitelist = methodsOnReceiverWhitelist;
//    }
//
    // TODO arguments list
    public Object checkCall(Object object, String methodCall, Closure closure) {
        System.out.println("Inside checkCall" + object.getClass() + "." + methodCall);
        if (methodsOnReceiverBlacklist != null) {
            if(methodsOnReceiverBlacklist.contains(object.getClass().getName() + "." + methodCall)) {
                throw new SecurityException(object.getClass().getName() + "." + methodCall + " is not allowed");
            }
        }
        if (methodsOnReceiverWhitelist != null) {
            if(!methodsOnReceiverWhitelist.contains(object.getClass().getName() + "." + methodCall)) {
                throw new SecurityException(object.getClass().getName() + "." + methodCall + " is not allowed");
            }
        }
        if (closure != null) {
            return closure.call();
        } else return null;


    }
    public Object checkCall(Class clazz, String methodCall, Closure closure) {
        System.out.println("Inside checkCallClazz" + clazz + "." + methodCall);
        return closure.call();
    }



}
