package bruto.result;

import java.util.ArrayList;
import java.util.List;

public class BeanExplorationResults {

    private Class clazz;
    private List<MethodExplorationResults> methodExplorationResults = new ArrayList<>();

    public BeanExplorationResults(Class clazz) {
        this.clazz = clazz;
    }

    public void add(MethodExplorationResults methodExplorationResults) {
        this.methodExplorationResults.add(methodExplorationResults);
    }

    public java.util.List<MethodExplorationResults> getMethodExplorationResults() {
        return methodExplorationResults;
    }

    public void printResults(StringBuilder stringBuilder) {
        stringBuilder.append("-------------- Bean Exploration Report --------------\n");
        stringBuilder.append(String.format("Class: %s\n", clazz.getName()));
        stringBuilder.append(String.format("Testable methods: %s\n", methodExplorationResults.size()));

        for (MethodExplorationResults methodExplorationResult : methodExplorationResults) {
            methodExplorationResult.printResults(stringBuilder);
        }

        stringBuilder.append("-------------- Bean Exploration Report End --------------\n");
    }

}
