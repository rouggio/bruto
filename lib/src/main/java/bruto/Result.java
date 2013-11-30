package bruto;

import bruto.core.ArgumentSet;

public class Result {

    private ArgumentSet argumentSet;
    //todo too many result related classes around!
    private TestResult result;


    public Result(ArgumentSet argumentSet, TestResult result) {
        this.argumentSet = argumentSet;
        this.result = result;
    }

    public ArgumentSet getArgumentSet() {
        return argumentSet;
    }

    public void printResults(StringBuilder stringBuilder) {
        StringBuilder sb = new StringBuilder();
        for (Object argument : getArgumentSet().getArguments()) {
            sb.append(argument).append("|");
        }
        stringBuilder.append(String.format("comment: %s - arguments: %s\n", result.getComment(), sb.toString()));
    }

}
