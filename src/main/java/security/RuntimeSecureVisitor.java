package security;


import org.codehaus.groovy.ast.ClassCodeExpressionTransformer;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.control.SourceUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RuntimeSecureVisitor extends ClassCodeExpressionTransformer  {
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
    public Expression transform(Expression exp) {
        if(exp instanceof MethodCallExpression) {
            MethodCallExpression expression = (MethodCallExpression)exp;


            BlockStatement blockStatement = new BlockStatement();
            ExpressionStatement expressionStatement = new ExpressionStatement(expression);

            blockStatement.addStatement(expressionStatement);
            ClosureExpression closureExpression = new ClosureExpression(null, blockStatement);
            closureExpression.setVariableScope(new VariableScope());

            ArgumentListExpression arguments = new ArgumentListExpression();
            arguments.addExpression(expression.getObjectExpression());
            arguments.addExpression(expression.getMethod());
            arguments.addExpression(closureExpression);

            return new StaticMethodCallExpression(new ClassNode(GroovyAccessControl.class), "checkCall", arguments);
        } /*else if(exp instanceof StaticMethodCallExpression) {
            StaticMethodCallExpression expression = (StaticMethodCallExpression)exp;
            ArgumentListExpression arguments = new ArgumentListExpression();
            arguments.addExpression(expression.getReceiver());
            arguments.addExpression(expression.getMethod());
            BlockStatement blockStatement = new BlockStatement();
            ExpressionStatement expressionStatement = new ExpressionStatement(expression);
            blockStatement.addStatement(expressionStatement);
            ClosureExpression closureExpression = new ClosureExpression(null, blockStatement);
            closureExpression.setVariableScope(new VariableScope());
            arguments.addExpression(closureExpression);
            return new StaticMethodCallExpression(new ClassNode(GroovyAccessControl.class), "checkCall", arguments);
        }*/
        return exp;
    }
}
