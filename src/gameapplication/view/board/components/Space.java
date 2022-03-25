package gameapplication.view.board.components;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Space extends Button {
    private int x;
    private int y;


    public PieceComp getPieceComp() {
        return pieceComp;
    }

    private PieceComp pieceComp;


    public Space(boolean light, int x, int y) {

        this.x = x;
        this.y = y;
        this.pieceComp = null;

        this.getStyleClass().add("chess-space");

        if (light)
            this.getStyleClass().add("chess-space-light");
        else
            this.getStyleClass().add("chess-space-dark");
    }

    public PieceComp getPiece() {
        return this.pieceComp;
    }

    public void setPiece(PieceComp pieceComp) {
        this.pieceComp = pieceComp;
        if (this.pieceComp != null)
            this.setGraphic(new ImageView(pieceComp.getImage()));
        else
            this.setGraphic(new ImageView());
    }

    public boolean isOccupied() {
        return (this.pieceComp != null);
    }

    public PieceComp releasePiece() {
        PieceComp tmpPieceComp = this.pieceComp;
        setPiece(null);
        return tmpPieceComp;
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
