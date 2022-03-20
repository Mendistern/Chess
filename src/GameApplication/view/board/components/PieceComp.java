package GameApplication.view.board.components;

import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
import javafx.scene.image.Image;

import java.io.Serializable;

public abstract class PieceComp implements Serializable {
    protected Image image;
    protected boolean color;

    public PieceComp(boolean color) {
        this.color = color;

        String location = "resources/";
        String filename = this.getColor() + "_" + this.getName() + ".png";
        this.image = new Image(location + filename);
    }

    public Image getImage() {
        return this.image;
    }

    public String getColor() {
        if (this.color)
            return "black";
        else

        return "white";
    }

    public boolean isBlack(Piece piece) {
        return piece.getPieceColor() == PieceColor.BLACK;
    }

    public String toString() {
        return (this.getName() + " " + this.getColor());
    }

    protected abstract String getName();

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

    public void setImage(Image image) {
        this.image = image;
    }

    public void setColor(boolean color) {
        this.color = color;
    }
}
