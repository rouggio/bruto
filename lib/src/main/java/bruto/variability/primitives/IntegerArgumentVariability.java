package bruto.variability.primitives;

import bruto.variability.ArgumentVariability;
import bruto.variability.ArgumentVariabilityWalker;
import bruto.variability.DiscreteSetWalker;

import java.lang.reflect.Type;

public class IntegerArgumentVariability extends ArgumentVariability {

    private Integer[] variants = new Integer[]{null, 0, 1, 2, Integer.MAX_VALUE};

    @Override
    public boolean matches(Type aType) {
        return Integer.class == aType;
    }

    @Override
    public ArgumentVariabilityWalker newWalker(Type aType) {
        return new DiscreteSetWalker(variants);
    }

}
