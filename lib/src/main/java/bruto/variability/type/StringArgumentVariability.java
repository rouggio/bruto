package bruto.variability.type;

import bruto.variability.ArgumentVariability;
import bruto.variability.ArgumentVariabilityWalker;
import bruto.variability.DiscreteSetWalker;

import java.lang.reflect.Type;

public class StringArgumentVariability extends ArgumentVariability {

    private String[] variants = new String[]{null, "", " ", " a", " a ", "ab", " ab "};

    @Override
    public boolean matches(Type type) {
        return String.class.equals(type);
    }

    @Override
    public ArgumentVariabilityWalker newWalker(Type type) {
        return new DiscreteSetWalker<>(variants);
    }
}
