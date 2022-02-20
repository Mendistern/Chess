package GameApplication.model.exceptions;

public class PieceSetsExceptions extends Exception{
    public PieceSetsExceptions() throws Exception {
        throw new Exception("Error in PieceSets");
    }

    public PieceSetsExceptions(String message) {
        super(message);
    }

    public PieceSetsExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public PieceSetsExceptions(Throwable cause) {
        super(cause);
    }
}
