package bruto.variability;

import bruto.variability.primitives.IntegerArgumentVariability;
import bruto.variability.primitives.StringArgumentVariability;
import bruto.variability.type.CompositeBeanArgumentVariability;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides all implementations of ArgumentVariablity
 */
public class ArgumentVariabilityProvider {

    private List<ArgumentVariability> argumentVariabilities;

    public ArgumentVariabilityProvider() {
        argumentVariabilities = new ArrayList<>();
        argumentVariabilities.add(new IntegerArgumentVariability());
        argumentVariabilities.add(new StringArgumentVariability());
        argumentVariabilities.add(new CompositeBeanArgumentVariability(this));
    }

    public ArgumentVariabilityWalker buildArgumentVariability(Type type) {
        for (ArgumentVariability argumentVariability : argumentVariabilities) {
            if (argumentVariability.matches(type)) {
                return argumentVariability.newWalker(type);
            }
        }
        throw new IllegalStateException("unable to provide variability for type " + type);
    }
}
