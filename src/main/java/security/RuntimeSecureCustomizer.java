package security;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;
import org.codehaus.groovy.syntax.Token;

import java.util.Iterator;
import java.util.List;

public class RuntimeSecureCustomizer extends CompilationCustomizer {
    @Override
    public void call(SourceUnit sourceUnit, GeneratorContext generatorContext, ClassNode classNode) throws CompilationFailedException {
        ModuleNode ast = sourceUnit.getAST();
        RuntimeSecureVisitor visitor = new RuntimeSecureVisitor(sourceUnit);

        VariableExpression left = new VariableExpression("groovyAccessControl", new ClassNode(GroovyAccessControl.class));
        ArgumentListExpression args = new ArgumentListExpression();
        ConstructorCallExpression right =  new ConstructorCallExpression(new ClassNode(GroovyAccessControl.class), args);
        DeclarationExpression declarationExpression = new DeclarationExpression(left, Token.newSymbol("=", -1, -1), right);
        ExpressionStatement expressionStatement = new ExpressionStatement(declarationExpression);

        BlockStatement block = sourceUnit.getAST().getStatementBlock();
        List<Statement> myStatements = block.getStatements();
        myStatements.add(0, expressionStatement);

        visitor.visitBlockStatement(block);
    }

    public RuntimeSecureCustomizer() {
        super(CompilePhase.CANONICALIZATION);
    }
}
