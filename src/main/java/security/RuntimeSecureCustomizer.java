package security;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;

import java.util.Iterator;

public class RuntimeSecureCustomizer extends CompilationCustomizer {
    @Override
    public void call(SourceUnit sourceUnit, GeneratorContext generatorContext, ClassNode classNode) throws CompilationFailedException {
        ModuleNode ast = sourceUnit.getAST();
        RuntimeSecureVisitor visitor = new RuntimeSecureVisitor(sourceUnit);
        java.util.List<MethodNode> methods = classNode.getMethods();
        Iterator<MethodNode> it = methods.iterator();
        while(it.hasNext()) {
            MethodNode method = it.next();
            visitor.visitMethod(method);
        }
    }

    public RuntimeSecureCustomizer() {
        super(CompilePhase.CANONICALIZATION);
    }
}
