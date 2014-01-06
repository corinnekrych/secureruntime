package security;


import org.codehaus.groovy.ast.ClassCodeExpressionTransformer;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.control.SourceUnit;

public class RuntimeSecureVisitor extends ClassCodeExpressionTransformer {
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
    public void visitMethod(MethodNode node) {
        super.visitMethod(node);
        System.out.println("inside visitMethod:" + node.getName());
    }
    @Override
    public Expression transform(Expression exp) {
        if (exp instanceof MethodCallExpression) {
            MethodCallExpression methodCallExpression = (MethodCallExpression)exp;
            ArgumentListExpression arguments = new ArgumentListExpression();
            arguments.addExpression(methodCallExpression.getObjectExpression());
            arguments.addExpression(methodCallExpression.getMethod());
            //TODO args list
            Expression expression = new StaticMethodCallExpression(new ClassNode(GroovyAccessControl.class), "checkCall", arguments);
            //TODO call method itself

            return expression;
        }

        return super.transform(exp);
    }
}
