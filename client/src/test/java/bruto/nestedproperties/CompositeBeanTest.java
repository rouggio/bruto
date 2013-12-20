package bruto.nestedproperties;

import bruto.core.BrutoEngine;
import bruto.core.FormulaVerificationResult;
import bruto.core.TruthFormula;
import bruto.result.BeanExplorationResults;
import org.junit.Test;
import sample.composite.LoginService;

import java.util.HashSet;

public class CompositeBeanTest {

    @Test
    public void testLoginService() {
        BrutoEngine brutoEngine = new BrutoEngine();
        StringBuilder sb = new StringBuilder();

        LoginService loginService = new LoginService();
        HashSet<TruthFormula> truthFormulas = new HashSet<>();
        truthFormulas.add(new TruthFormula<Boolean>("authenticate") {
            @Override
            public FormulaVerificationResult verify(Boolean result, Object[] arguments) {
                return new FormulaVerificationResult(FormulaVerificationResult.Result.SUCCESS);
            }
        });

        BeanExplorationResults beanExplorationResults = brutoEngine.exploreBean(loginService, truthFormulas);
        beanExplorationResults.printResults(sb);

        System.out.println(sb.toString());
    }

}
