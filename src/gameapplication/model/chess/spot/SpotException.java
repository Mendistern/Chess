package gameapplication.model.chess.spot;

/**
 * This class is used to represent exceptions that are thrown by the Spot class
 */
public class SpotException extends Exception{
    // The constructor of the class.
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
