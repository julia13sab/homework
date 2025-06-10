package lessons.task_1.exception;

public class TestRunException extends RuntimeException {

    public TestRunException(String message, Exception e) {
        super(message, e);
    }

    public TestRunException(String message) {
        super(message);
    }
}
