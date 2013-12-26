package bruto.variability;

import java.util.Enumeration;

/**
 * describes the range of values which might be assumed by a given parameter
 */
public abstract class ArgumentVariabilityWalker implements Enumeration {

    public abstract Object getCurrentElement();

    public abstract Object restart();

}
