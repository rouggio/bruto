package bruto;

public class TestResult {

    public enum Result {
        SUCCESS,
        FAILURE
    }

    private Result result;
    private String comment;

    public TestResult(Result result) {
        this.result = result;
    }

    public TestResult(Result result, String comment) {
        this(result);
        this.comment = comment;
    }

    public Result getResult() {
        return result;
    }

    public String getComment() {
        return comment;
    }
}
