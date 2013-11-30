package bruto;


import bruto.core.BrutoEngine;
import bruto.core.FormulaVerificationResult;
import bruto.core.TruthFormula;
import bruto.result.BeanExplorationResults;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.ComplexBean;
import sample.DuplicateUserException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ComplexBeanTest {

    private static final Logger log = LoggerFactory.getLogger(ComplexBeanTest.class);

    @Test
    public void test() {
        BrutoEngine engine = new BrutoEngine();

        final ComplexBean complexBean = new ComplexBean();

        Set<TruthFormula> truthFormulas = new HashSet<>();

        truthFormulas.add(new TruthFormula<Boolean>("containsUser", String.class) {
            @Override
            public FormulaVerificationResult verify(Boolean result, Object[] arguments) {
                boolean value = complexBean.containsUser((String) arguments[0]) == result;
                return new FormulaVerificationResult(value ? FormulaVerificationResult.Result.SUCCESS : FormulaVerificationResult.Result.FAILURE, arguments);
            }
        });

        truthFormulas.add(new TruthFormula<ComplexBean.User>("create") {
            @Override
            public FormulaVerificationResult verify(ComplexBean.User result, Object[] arguments) {
                return new FormulaVerificationResult(FormulaVerificationResult.Result.SUCCESS, arguments);
            }
        });

        truthFormulas.add(new TruthFormula<IllegalArgumentException>("create") {
            @Override
            public FormulaVerificationResult verify(IllegalArgumentException exception, Object[] arguments) {
                if ((arguments[0] == null) || (arguments[1] == null)) {
                    return new FormulaVerificationResult(FormulaVerificationResult.Result.SUCCESS, arguments, "expected behaviour");
                } else {
                    return new FormulaVerificationResult(FormulaVerificationResult.Result.FAILURE, arguments, "unexpected IllegalArgumentException");
                }
            }
        });

        truthFormulas.add(new TruthFormula<DuplicateUserException>("create") {
            @Override
            public FormulaVerificationResult verify(DuplicateUserException exception, Object[] arguments) {
                if (complexBean.containsUser((String) arguments[0])) {
                    return new FormulaVerificationResult(FormulaVerificationResult.Result.SUCCESS, arguments, "expected behaviour");
                } else {
                    return new FormulaVerificationResult(FormulaVerificationResult.Result.FAILURE, arguments, "user should now have been found as duplicate");
                }
            }
        });

        BeanExplorationResults beanExplorationResults = engine.exploreBean(complexBean, truthFormulas);
        assertNotNull(beanExplorationResults);
        StringBuilder report = new StringBuilder();
        beanExplorationResults.printResults(report);
        log.info("Report\n{}", report.toString());
        assertEquals(2, beanExplorationResults.getMethodExplorationResults().size());
    }

}
