package bruto.variability.type;

import bruto.variability.ArgumentVariability;
import bruto.variability.ArgumentVariabilityWalker;
import bruto.variability.DiscreteSetWalker;

import java.lang.reflect.Type;

public class IntegerArgumentVariability extends ArgumentVariability {

    private Integer[] variants = new Integer[]{null, 0, 1, 2, Integer.MAX_VALUE};

    @Override
    public boolean matches(Type type) {
        return Integer.class.equals(type);
    }

    @Override
    public ArgumentVariabilityWalker newWalker() {
        return new DiscreteSetWalker(variants);
    }

}
