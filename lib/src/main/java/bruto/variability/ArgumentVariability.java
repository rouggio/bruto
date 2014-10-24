package bruto.variability;

import java.lang.reflect.Type;

/**
 * describes the range of values which might be assumed by a given parameter
 */
public abstract class ArgumentVariability {

    public abstract boolean matches(Type aType);

    public abstract ArgumentVariabilityWalker newWalker(Type aType);

}
