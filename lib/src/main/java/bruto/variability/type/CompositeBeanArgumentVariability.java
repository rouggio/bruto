package bruto.variability.type;

import bruto.variability.ArgumentVariability;
import bruto.variability.ArgumentVariabilityProvider;
import bruto.variability.ArgumentVariabilityWalker;
import bruto.variability.CompositeBeanWalker;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class CompositeBeanArgumentVariability extends ArgumentVariability {

    private ArgumentVariabilityProvider argumentVariabilityProvider;

    public CompositeBeanArgumentVariability(ArgumentVariabilityProvider argumentVariabilityProvider) {
        this.argumentVariabilityProvider = argumentVariabilityProvider;
    }

    @Override
    public boolean matches(Type type) {
        if (!(type instanceof Class)) {
            return false;
        }
        if (((Class) type).isInterface()) {
            return false;
        }
        return true;
    }

    @Override
    public ArgumentVariabilityWalker newWalker(Type type) {
        Class clazz = (Class) type;
        CompositeBeanWalker walker = new CompositeBeanWalker(clazz);
        for (int i = 0; i < clazz.getDeclaredFields().length; i++) {
            Field field = clazz.getDeclaredFields()[i];
            walker.addWalker(argumentVariabilityProvider.buildArgumentVariability(field.getType()));
        }
        return walker;
    }
}
