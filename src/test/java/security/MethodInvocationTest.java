package security;

import groovy.lang.GroovyShell;
import junit.framework.TestCase;
import org.codehaus.groovy.control.CompilerConfiguration;

public class MethodInvocationTest extends TestCase {

    public void testInvocationMethodIsDenied() {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.addCompilationCustomizers(new RuntimeSecureCustomizer());
        GroovyShell sh = new GroovyShell(cc);
        sh.evaluate("println 'toto'");
    }
}
