package bruto.result;

import bruto.core.FormulaVerificationResult;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class MethodExplorationResults {

    private Method method;
    private long applicableFormulas;
    private long permutations;
    private Set<FormulaVerificationResult> violations;
    private Set<FormulaVerificationResult> successes;
    private long unparsedExecutions;
    private long unparsedExceptions;


    public MethodExplorationResults(Method method) {
        this.method = method;
        this.violations = new HashSet<>();
        this.successes = new HashSet<>();
    }

    public Method getMethod() {
        return method;
    }

    public long getPermutations() {
        return permutations;
    }

    public void setPermutations(long permutations) {
        this.permutations = permutations;
    }

    public void addViolation(FormulaVerificationResult result) {
        violations.add(result);
    }

    public void addSuccess(FormulaVerificationResult result) {
        successes.add(result);
    }

    public Set<FormulaVerificationResult> getViolations() {
        return violations;
    }

    public Set<FormulaVerificationResult> getSuccesses() {
        return successes;
    }

    public void incrementUnparsedExecutions() {
        unparsedExecutions++;
    }

    public void incrementUnparsedExceptions() {
        unparsedExceptions++;
    }

    public long getUnparsedExecutions() {
        return unparsedExecutions;
    }

    public long getUnparsedExceptions() {
        return unparsedExceptions;
    }

    public void setApplicableFormulas(long applicableFormulas) {
        this.applicableFormulas = applicableFormulas;
    }

    public void printResults(StringBuilder stringBuilder) {
        stringBuilder.append("-------------- Method Exploration Report --------------\n");
        stringBuilder.append(String.format("Method name: %s\n", method.getName()));
//        stringBuilder.append("------- successes -------\n");
//        for (FormulaVerificationResult success : successes) {
//            success.printResults(stringBuilder);
//        }
        stringBuilder.append("------- violations -------\n");
        for (FormulaVerificationResult violation : violations) {
            violation.printResults(stringBuilder);
        }
        if (violations.isEmpty()) {
            stringBuilder.append("No violations encountered\n");
        }
        stringBuilder.append("------- stats -------\n");
        stringBuilder.append(String.format("Number of applicable formulas: %s\n", applicableFormulas));
        stringBuilder.append(String.format("Number of permutations run: %s\n", permutations));
        stringBuilder.append(String.format("Number of successes encountered: %s\n", successes.size()));
        stringBuilder.append(String.format("Number of violations encountered: %s\n", violations.size()));
        stringBuilder.append(String.format("Number of executions not matching any formula: %s\n", unparsedExecutions));
        stringBuilder.append(String.format("Number of uncaught exceptions: %s\n", unparsedExceptions));
        stringBuilder.append(String.format("formula coverage: %f%%\n", ((permutations - unparsedExecutions) / (float) permutations * 100)));
        stringBuilder.append("-------------- Method Exploration Report End --------------\n");
    }
}
