package bruto;


import bruto.core.BrutoEngine;
import bruto.core.TruthFormula;
import bruto.result.BeanExplorationResults;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.SimpleBean;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SimpleBeanTest {

    private static final Logger log = LoggerFactory.getLogger(SimpleBeanTest.class);

    @Test
    public void simpleBeanTest() {
        BrutoEngine engine = new BrutoEngine();

        SimpleBean simpleBean = new SimpleBean();

        Set<TruthFormula> truthFormulas = new HashSet<>();
        truthFormulas.add(new TruthFormula<Float>() {
            @Override
            public TestResult test(Float result, Object[] arguments) {
                int a = (arguments[0] != null) ? (Integer) arguments[0] : 0;
                int b = (arguments[1] != null) ? (Integer) arguments[1] : 0;
                boolean computationCorrect = (result) == (a + b);
                if (computationCorrect) {
                    return new TestResult(TestResult.Result.SUCCESS);
                } else {
                    return new TestResult(TestResult.Result.FAILURE, "incorrect computation result");
                }
            }
        });

        BeanExplorationResults results = engine.exploreBean(simpleBean, truthFormulas);
        assertNotNull(results);
        StringBuilder report = new StringBuilder();
        results.printResults(report);
        log.info("Report:\n{}", report.toString());
        assertTrue(results.getMethodExplorationResults().isEmpty());
        log.info("results size {}", results.getMethodExplorationResults().size());
    }

}
