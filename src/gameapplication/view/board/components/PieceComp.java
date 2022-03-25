package gameapplication.view.board.components;

import gameapplication.model.chess.piece.Piece;
import gameapplication.model.chess.piece.PieceColor;
import javafx.scene.image.Image;

import java.io.Serializable;

/**
 * A piece is a piece on the board
 */
public abstract class PieceComp implements Serializable {
    // A way to make the image and color private.
    protected Image image;
    protected boolean color;

    // Initializing the image and color.
    public PieceComp(boolean color) {
        this.color = color;

        // This is to set the image of the piece.
        String location = "resources/";
        String filename = this.getColor() + "_" + this.getName() + ".png";
        this.image = new Image(location + filename);
    }

    /**
     * Returns the image of the button
     *
     * @return The image object.
     */
    public Image getImage() {
        return this.image;
    }

    // Returning the color of the piece.
    public String getColor() {
        if (this.color)
            return "black";
        else

        return "white";
    }

    /**
     * Given a piece, return true if the piece is black, false otherwise
     *
     * @param piece the piece that was moved
     * @return A boolean value.
     */

    // This is a helper method that returns true if the piece is black, false otherwise.
    public boolean isBlack(Piece piece) {
        return piece.getPieceColor() == PieceColor.BLACK;
    }


    /**
     * This function returns a string representation of the object
     *
     * @return The name and color of the object.
     */
    public String toString() {
        return (this.getName() + " " + this.getColor());
    }

    /**
     * This function returns the name of the class
     *
     * @return The name of the class.
     */
    /**
     * Returns the name of the function
     *
     * @return The name of the class.
     */

    protected abstract String getName();

    /**
     * Given a piece, return a pieceComp that represents that piece
     *
     * @param piece The piece to be converted.
     * @return A PieceComp object
     */
    public static PieceComp fromPieceToPieceComp(Piece piece){
        boolean isBlack = piece.getPieceColor() == PieceColor.BLACK;
        PieceComp pieceComp;
        switch (piece.getPieceType()){
            case PAWN:
                pieceComp= new Pawn(isBlack);
                break;
            case KING:
                pieceComp =  new King(isBlack);
            break;
            case BISHOP:
                pieceComp =  new Bishop(isBlack);
            break;
            case KNIGHT:
                pieceComp =  new Knight(isBlack);
            break;
            case ROOK:
                pieceComp =  new Rook(isBlack);
            break;
            case QUEEN:
                pieceComp =  new Queen(isBlack);
            break;

            default:
                throw new IllegalStateException("Unexpected value: " + piece.getPieceType());
        }

        return pieceComp;
    }

    /**
     * It sets the image of the question.
     *
     * @param image The image to be displayed.
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * It sets the color of the turtle to true or false.
     *
     * @param color A boolean value that indicates whether the text is colored or not.
     */
    public void setColor(boolean color) {
        this.color = color;
    }
}
