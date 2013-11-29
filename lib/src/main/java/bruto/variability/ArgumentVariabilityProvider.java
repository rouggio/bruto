package bruto.variability;

import bruto.variability.type.IntegerArgumentVariability;
import bruto.variability.type.StringArgumentVariability;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides all implementations of ArgumentVariablity
 */
public class ArgumentVariabilityProvider {

    private Set<ArgumentVariability> argumentVariabilities;

    public ArgumentVariabilityProvider() {
        argumentVariabilities = new HashSet<>();
        argumentVariabilities.add(new IntegerArgumentVariability());
        argumentVariabilities.add(new StringArgumentVariability());
    }

    public ArgumentVariabilityWalker buildArgumentVariability(Type type) {
        for (ArgumentVariability argumentVariability : argumentVariabilities) {
            if (argumentVariability.matches(type)) {
                return argumentVariability.newWalker();
            }
        }
        throw new IllegalStateException("unable to provide variability for type " + type);
    }
}
