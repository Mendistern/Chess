package GameApplication.view.board.components;

import GameApplication.view.board.components.Piece;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Space extends Button {
    private int x;
    private int y;
    private Piece piece;

    public Space(boolean light, int x, int y) {
        super();
        this.x = x;
        this.y = y;
        this.piece = null;
        this.getStyleClass().add("chess-space");

        if (light)
            this.getStyleClass().add("chess-space-light");
        else
            this.getStyleClass().add("chess-space-dark");
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        if (this.piece != null)
            this.setGraphic(new ImageView(piece.getImage()));
        else
            this.setGraphic(new ImageView());
    }

    public boolean isOccupied() {
        return (this.piece != null);
    }

    public Piece releasePiece() {
        Piece tmpPiece = this.piece;
        setPiece(null);
        return tmpPiece;
    }

    public String getPieceColor() {
        if (getPiece() != null)
            return getPiece().getColor();
        else
            return "";
    }

    public int getX() {
        return this.x;
    }

    public void setX(int xIn) {
        this.x = xIn;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int yIn) {
        this.y = yIn;
    }

}
