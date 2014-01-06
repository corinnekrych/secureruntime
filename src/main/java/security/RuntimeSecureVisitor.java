package security;


import org.codehaus.groovy.ast.ClassCodeExpressionTransformer;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.control.SourceUnit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RuntimeSecureVisitor extends ClassCodeVisitorSupport {
    private SourceUnit sourceUnit;

    public RuntimeSecureVisitor(SourceUnit sourceUnit) {
        super();
        this.sourceUnit = sourceUnit;
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return sourceUnit;
    }

    @Override
    public void visitBlockStatement(BlockStatement block) {
        super.visitBlockStatement(block);
        java.util.List<org.codehaus.groovy.ast.stmt.Statement> statements = block.getStatements();
        Iterator<Statement> it = statements.iterator();
        List<Statement> newList = new ArrayList<Statement>();
        while (it.hasNext()) {
            Statement statement = it.next();
            if (statement instanceof ExpressionStatement) {
                ExpressionStatement expressionStatement = (ExpressionStatement)statement;
                Expression exp = expressionStatement.getExpression();
                if (exp instanceof MethodCallExpression) {
                    MethodCallExpression methodCallExpression = (MethodCallExpression)exp;
                    ArgumentListExpression arguments = new ArgumentListExpression();
                    arguments.addExpression(methodCallExpression.getObjectExpression());
                    arguments.addExpression(methodCallExpression.getMethod());
                    Expression expression = new StaticMethodCallExpression(new ClassNode(GroovyAccessControl.class), "checkCall", arguments);
                    newList.add(new ExpressionStatement(expression));
                    newList.add(statement);
                    it.remove();
                }
            }
        }
        block.addStatements(newList);

    }

}
