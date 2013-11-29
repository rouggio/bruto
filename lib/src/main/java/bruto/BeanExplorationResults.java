package bruto;

import java.util.ArrayList;
import java.util.List;

public class BeanExplorationResults {

    private List<MethodExplorationResults> methodExplorationResults = new ArrayList<>();

    public void add(MethodExplorationResults methodExplorationResults) {
        this.methodExplorationResults.add(methodExplorationResults);
    }

    public List<MethodExplorationResults> getMethodExplorationResults() {
        return methodExplorationResults;
    }

    public void printResults(StringBuilder stringBuilder) {
        for (MethodExplorationResults methodResults : methodExplorationResults) {
            methodResults.printResults(stringBuilder);
        }
    }
}
