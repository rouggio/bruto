package bruto.variability;

import bruto.variability.type.CompositeBeanArgumentVariability;
import bruto.variability.type.IntegerArgumentVariability;
import bruto.variability.type.StringArgumentVariability;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
