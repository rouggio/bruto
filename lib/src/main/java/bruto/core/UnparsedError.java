package bruto.core;

import java.lang.reflect.Method;

public class UnparsedError {

    private Method method;
    private Object[] argumentSet;
    private Throwable throwable;

    public UnparsedError(Method method, Object[] argumentSet, Throwable throwable) {
        this.method = method;
        this.argumentSet = argumentSet;
        this.throwable = throwable;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgumentSet() {
        return argumentSet;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void printResults(StringBuilder stringBuilder) {
        StringBuilder sb = new StringBuilder();
        for (Object argument : getArgumentSet()) {
            sb.append(argument).append("|");
        }
        stringBuilder.append(String.format("arguments: %s -> exception: %s\n", sb.toString(), throwable.getClass().getName()));
    }
}
