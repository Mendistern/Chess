package GameApplication.model.chess.spot;

public class SpotException extends Exception{
    public SpotException() {
    }

    public SpotException(String message) {
        super(message);
    }

    public SpotException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpotException(Throwable cause) {
        super(cause);
    }
}
