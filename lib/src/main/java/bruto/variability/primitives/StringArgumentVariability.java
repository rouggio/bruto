package bruto.variability.primitives;

import bruto.variability.ArgumentVariability;
import bruto.variability.ArgumentVariabilityWalker;
import bruto.variability.DiscreteSetWalker;

import java.lang.reflect.Type;

public class StringArgumentVariability extends ArgumentVariability {

    private String[] variants = new String[]{null, "", " ", " a", " a ", "ab", " ab "};

    @Override
    public boolean matches(Type aType) {
        return String.class == aType;
    }

    @Override
    public ArgumentVariabilityWalker newWalker(Type aType) {
        return new DiscreteSetWalker(variants);
    }

}
