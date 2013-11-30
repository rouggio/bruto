package bruto.core;

import bruto.variability.ArgumentVariabilityWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;

/**
 * builds the permutation matrix for all valid argument variants
 */
public class PermutationEnumeration implements Enumeration<ArgumentSet> {

    private static final Logger log = LoggerFactory.getLogger(PermutationEnumeration.class);


    private ArgumentVariabilityWalker[] argumentsVariabilityWalkers;


    public PermutationEnumeration(ArgumentVariabilityWalker[] argumentsVariabilityWalkers) {
        this.argumentsVariabilityWalkers = argumentsVariabilityWalkers;
    }

    @Override
    public boolean hasMoreElements() {
        for (ArgumentVariabilityWalker walker : argumentsVariabilityWalkers) {
            if (walker.hasMoreElements()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArgumentSet nextElement() {
        Object[] arguments = new Object[argumentsVariabilityWalkers.length];
        boolean needToIncreaseHigherVariability = true;

        //iterate the array from the least to the most significant position
        for (int i = argumentsVariabilityWalkers.length - 1; i >= 0; i--) {

            //inspect current variability
            ArgumentVariabilityWalker variabilityWalker = argumentsVariabilityWalkers[i];

            if (needToIncreaseHigherVariability) {
                //increment this position

                if (variabilityWalker.hasMoreElements()) {
                    //we can increment this position, do it
                    arguments[i] = variabilityWalker.nextElement();
                    needToIncreaseHigherVariability = false;
                } else {
                    //we cannot increment the current position, restart it
                    arguments[i] = variabilityWalker.restart();
                }

            } else {

                arguments[i] = variabilityWalker.getCurrentElement();

            }
        }
        if (log.isDebugEnabled()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Object argument : arguments) {
                stringBuilder.append(argument).append("|");
            }
            log.debug("adding tuple {}", stringBuilder.toString());
        }
        return new ArgumentSet(arguments);
    }

}
