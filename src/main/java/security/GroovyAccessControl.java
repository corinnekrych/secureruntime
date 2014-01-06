package security;

import org.codehaus.groovy.ast.expr.MethodCall;

public class GroovyAccessControl {
    // TODO arguments list
    public static void checkCall(Object object, String methodCall) {
       System.out.println("Inside checkCall" + object.getClass() + "." + methodCall);
    }
}
