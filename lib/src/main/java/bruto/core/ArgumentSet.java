package bruto.core;

/**
 * an instance of possible arguments permutation for a given method
 */
public class ArgumentSet {

    // the value for the arguments to be provided to the target method
    private Object[] arguments;

    public ArgumentSet(Object[] arguments) {
        this.arguments = arguments;
    }

    public Object[] getArguments() {
        return arguments;
    }

}
