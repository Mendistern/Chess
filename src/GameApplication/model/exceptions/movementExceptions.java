package GameApplication.model.exceptions;

public class movementExceptions extends Exception {
    public movementExceptions() {
    }

    public movementExceptions(String message) {
        super(message);
    }

    public movementExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public movementExceptions(Throwable cause) {
        super(cause);
    }
}
