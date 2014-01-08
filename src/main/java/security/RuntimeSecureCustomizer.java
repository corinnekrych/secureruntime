package security;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.classgen.VariableScopeVisitor;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;
import org.codehaus.groovy.syntax.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RuntimeSecureCustomizer extends CompilationCustomizer {
    @Override
    public void call(SourceUnit sourceUnit, GeneratorContext generatorContext, ClassNode classNode) throws CompilationFailedException {
//        VariableExpression whiteList = new VariableExpression("whitelist", new ClassNode(ArrayList.class));
//        ConstructorCallExpression ctorWhiteList =  new ConstructorCallExpression(new ClassNode(ArrayList.class), new ArgumentListExpression());
//        DeclarationExpression constructionExpression = new DeclarationExpression(whiteList, Token.newSymbol("=", -1, -1), ctorWhiteList);
//        ExpressionStatement ctorStmt = new ExpressionStatement(constructionExpression);
//
//        ArgumentListExpression args = new ArgumentListExpression();
//        args.addExpression(new ConstantExpression("Foo.bar"));
//        MethodCallExpression addExp = new MethodCallExpression(whiteList, "add", args);
//        ExpressionStatement addStmt = new ExpressionStatement(addExp);
//
//
//        //new StaticMethodCallExpression(new ClassNode(Collections.class), "unmodifiableList", whiteList)
//
//        ArgumentListExpression args2 = new ArgumentListExpression();
//        args2.addExpression();
//        args2.addExpression(new ConstantExpression("null"));
//        ConstructorCallExpression right =  new ConstructorCallExpression(new ClassNode(GroovyAccessControl.class), args2);

        // insert at beginning of script
        BlockStatement block = sourceUnit.getAST().getStatementBlock();


//        List<Statement> myStatements = block.getStatements();
//        myStatements.add(0, ctorStmt);
//        myStatements.add(1, addStmt);

        new RuntimeSecureVisitor(sourceUnit).visitBlockStatement(block);

        classNode.addField("groovyAccessControl", MethodNode.ACC_PRIVATE | MethodNode.ACC_FINAL, new ClassNode(GroovyAccessControl.class), new ConstructorCallExpression(new ClassNode(GroovyAccessControl.class), new ArgumentListExpression()));

        VariableScopeVisitor scopeVisitor = new VariableScopeVisitor(sourceUnit);
        scopeVisitor.visitClass(classNode);
    }

    public RuntimeSecureCustomizer() {
        super(CompilePhase.CANONICALIZATION);
    }
}
