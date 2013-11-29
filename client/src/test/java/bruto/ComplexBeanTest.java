package bruto;


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
            public TestResult test(Boolean result, Object[] arguments) {
                boolean value = complexBean.containsUser((String) arguments[0]) == result;
                return new TestResult(value ? TestResult.Result.SUCCESS : TestResult.Result.FAILURE);
            }
        });

        truthFormulas.add(new TruthFormula<ComplexBean.User>("create") {
            @Override
            public TestResult test(ComplexBean.User result, Object[] arguments) {
                return new TestResult(TestResult.Result.SUCCESS);
            }
        });

        truthFormulas.add(new TruthFormula<IllegalArgumentException>("create") {
            @Override
            public TestResult test(IllegalArgumentException exception, Object[] arguments) {
                if ((arguments[0] == null) || (arguments[1] == null)) {
                    return new TestResult(TestResult.Result.SUCCESS, "expected behaviour");
                } else {
                    return new TestResult(TestResult.Result.FAILURE, "unexpected IllegalArgumentException");
                }
            }
        });

        truthFormulas.add(new TruthFormula<DuplicateUserException>("create") {
            @Override
            public TestResult test(DuplicateUserException exception, Object[] arguments) {
                if (complexBean.containsUser((String) arguments[0])) {
                    return new TestResult(TestResult.Result.SUCCESS, "expected behaviour");
                } else {
                    return new TestResult(TestResult.Result.FAILURE, "user should now have been found as duplicate");
                }
            }
        });

        BeanExplorationResults beanExplorationResults = engine.exploreBean(complexBean, truthFormulas);
        assertNotNull(beanExplorationResults);
        StringBuilder report = new StringBuilder();
        beanExplorationResults.printResults(report);
        log.info("Report\n{}", report.toString());
        log.info("bean methods tested size {}", beanExplorationResults.getMethodExplorationResults().size());
        assertEquals(2, beanExplorationResults.getMethodExplorationResults().size());
    }

}
