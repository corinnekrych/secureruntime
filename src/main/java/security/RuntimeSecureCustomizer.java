package security;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;
import org.codehaus.groovy.syntax.Token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RuntimeSecureCustomizer extends CompilationCustomizer {
    @Override
    public void call(SourceUnit sourceUnit, GeneratorContext generatorContext, ClassNode classNode) throws CompilationFailedException {
        ModuleNode ast = sourceUnit.getAST();
        RuntimeSecureVisitor visitor = new RuntimeSecureVisitor(sourceUnit);

        // new GroovyAccessControl
        VariableExpression left = new VariableExpression("groovyAccessControl", new ClassNode(GroovyAccessControl.class));
        ArgumentListExpression args = new ArgumentListExpression();
        ConstructorCallExpression right =  new ConstructorCallExpression(new ClassNode(GroovyAccessControl.class), args);
        DeclarationExpression declarationExpression = new DeclarationExpression(left, Token.newSymbol("=", -1, -1), right);
        ExpressionStatement expressionStatement = new ExpressionStatement(declarationExpression);

        // set white list
        // TODO hard coded whitelist for mock
        // define variable: ArrayList<String> whitelist = new ArrayList<String>();
        ArgumentListExpression args2 = new ArgumentListExpression();
        VariableExpression left2 = new VariableExpression("whitelist", new ClassNode(ArrayList.class));
        ConstructorCallExpression right2 =  new ConstructorCallExpression(new ClassNode(ArrayList.class), args2);
        DeclarationExpression declarationExpression2 = new DeclarationExpression(left2, Token.newSymbol("=", -1, -1), right2);
        ExpressionStatement expressionStatement2 = new ExpressionStatement(declarationExpression2);

        // add content to whitelist: whitelist.add("Foo.bar");
        ArgumentListExpression args3 = new ArgumentListExpression();
        args3.addExpression(new ConstantExpression("Foo.bar"));
        MethodCallExpression methodCallExpression2 = new MethodCallExpression(new VariableExpression("whitelist"), "add", args3);
        ExpressionStatement expressionStatement3 = new ExpressionStatement(methodCallExpression2);

        // call: groovyAccessControl.setMethodsOnReceiverWhitelist(whitelist)
        ArgumentListExpression argumentListExpression = new ArgumentListExpression();
        argumentListExpression.addExpression(new VariableExpression("whitelist"));
        MethodCallExpression methodCallExpression = new MethodCallExpression(new VariableExpression("groovyAccessControl"), "setMethodsOnReceiverWhitelist", argumentListExpression);
        ExpressionStatement expressionStatement4 = new ExpressionStatement(methodCallExpression);

        // insert at beginning of script
        BlockStatement block = sourceUnit.getAST().getStatementBlock();
        List<Statement> myStatements = block.getStatements();
        myStatements.add(0, expressionStatement);
        myStatements.add(1, expressionStatement2);
        myStatements.add(2, expressionStatement3);
        myStatements.add(3, expressionStatement4);

        visitor.visitBlockStatement(block);
    }

    public RuntimeSecureCustomizer() {
        super(CompilePhase.CANONICALIZATION);
    }
}
