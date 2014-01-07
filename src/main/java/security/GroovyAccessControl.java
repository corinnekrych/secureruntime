package security;

import groovy.lang.Closure;

import java.util.List;


public class GroovyAccessControl {

    // methods for a given receiver, syntax like MyReceiver.myMethod
    private List<String> methodsOnReceiverWhitelist;
    private List<String> methodsOnReceiverBlacklist;

    public GroovyAccessControl() {
        System.out.println("in default constructor");
    }

    public void setMethodsOnReceiverBlacklist(final List<String> methodsOnReceiverBlacklist) {
        this.methodsOnReceiverBlacklist = methodsOnReceiverBlacklist;
    }

    public void setMethodsOnReceiverWhitelist(final List<String> methodsOnReceiverWhitelist) {
        this.methodsOnReceiverWhitelist = methodsOnReceiverWhitelist;
    }

    // TODO arguments list
    public Object checkCall(Object object, String methodCall, Closure closure) {
        System.out.println("Inside checkCall" + object.getClass() + "." + methodCall);
//        if (methodsOnReceiverBlacklist != null) {
//            if(methodsOnReceiverBlacklist.contains(object.getClass().getName() + "." + methodCall)) {
//                throw new SecurityException(object.getClass().getName() + "." + methodCall + " is not allowed");
//            }
//        }
//        if (methodsOnReceiverWhitelist != null) {
//            if(!methodsOnReceiverWhitelist.contains(object.getClass().getName() + "." + methodCall)) {
//                throw new SecurityException(object.getClass().getName() + "." + methodCall + " is not allowed");
//            }
//        }
        if (closure != null) {
            Object o = closure.call(object);
            return o;
        } else return null;


    }
    public Object checkCall(Class clazz, String methodCall, Closure closure) {
        System.out.println("Inside checkCallClazz" + clazz + "." + methodCall);
        return closure.call();
    }



}
