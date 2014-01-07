package security;

import org.codehaus.groovy.ast.expr.MethodCall;
import groovy.lang.Closure;

public class GroovyAccessControl {
    // TODO arguments list
    public static Object checkCall(Object object, String methodCall, Closure closure) {
        System.out.println("Inside checkCall" + object.getClass() + "." + methodCall);
        return closure.call();
    }
    public static Object checkCall(Class clazz, String methodCall, Closure closure) {
        System.out.println("Inside checkCallClazz" + clazz + "." + methodCall);
        return closure.call();
    }
}
