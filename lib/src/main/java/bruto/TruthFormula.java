package bruto;

import java.lang.reflect.Method;

/**
 * describes a condition by which the output of a method should be considered valid, based upon it's input
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
    public abstract TestResult test(T result, Object[] arguments);

}