package bruto.core;

import java.lang.reflect.Method;

/**
 * describes a condition by which the output of a method should be considered valid, based upon its input
 */
public abstract class TruthFormula<T> {

    private String methodName;
    private Class<?>[] parameterTypes;

    protected TruthFormula() {
    }

    public TruthFormula(String methodName) {
        this.methodName = methodName;
    }

    public TruthFormula(String methodName, Class<?>... parameterTypes) {
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
    }

    protected boolean applicable(Method method) {
        if (methodName != null) {
            if (method.getName().equals(methodName)) {
                if (parameterTypes != null) {
                    for (int i = 0; i < parameterTypes.length; i++) {
                        Class<?> parameterType = parameterTypes[i];
                        if (!parameterType.equals(method.getParameterTypes()[i])) {
                            return false;
                        }
                    }
                    return true;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    //the expectation to meet
    protected FormulaVerificationResult internalVerify(T result, Object[] arguments) {
        FormulaVerificationResult formulaVerificationResult = verify(result, arguments);
        formulaVerificationResult.setArgumentSet(arguments);
        return formulaVerificationResult;
    }

    //the expectation to meet
    public abstract FormulaVerificationResult verify(T result, Object[] arguments);

}
