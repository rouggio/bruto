package bruto.core;

public class FormulaVerificationResult {

    public enum Result {
        SUCCESS,
        FAILURE
    }

    private Object[] argumentSet;
    private Result result;
    private String comment;

    public FormulaVerificationResult(Result result) {
        this.result = result;
    }

    public FormulaVerificationResult(Result result, String comment) {
        this.result = result;
        this.comment = comment;
    }

    public Result getResult() {
        return result;
    }

    public Object[] getArgumentSet() {
        return argumentSet;
    }

    protected void setArgumentSet(Object[] argumentSet) {
        this.argumentSet = argumentSet;
    }

    public String getComment() {
        return comment;
    }

    public boolean isFailure() {
        return Result.FAILURE.equals(result);
    }


    public void printResults(StringBuilder stringBuilder) {
        StringBuilder sb = new StringBuilder();
        for (Object argument : getArgumentSet()) {
            sb.append(argument).append("|");
        }
        stringBuilder.append(String.format("comment: %s - arguments: %s\n", comment, sb.toString()));
    }

}
