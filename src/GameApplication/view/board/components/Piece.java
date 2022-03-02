package GameApplication.view.board.components;

import GameApplication.model.MoveList;
import javafx.scene.image.Image;

public abstract class Piece {
    protected Image image;
    protected boolean color;

    protected boolean hasMoved;

    public Piece(boolean color) {
        this.color = color;

        hasMoved = false;

        String location = "resources/";
        String filename = this.getColor() + "_" + this.getName() + ".png";
        this.image = new Image(location + filename);
    }

    public boolean getHasMoved() {
        return this.hasMoved;
    }

    public void setHasMoved(boolean shouldBeTrue) {
        this.hasMoved = shouldBeTrue;
    }

    public Image getImage() {
        return this.image;
    }

    public String getColor() {
        if (this.color == true)
            return "white";
        else
            return "black";
    }

    public boolean isWhite() {
        return this.color;
    }

    public String toString() {
        return (this.getName() + " " + this.getColor());
    }

    public abstract String getName();

    public abstract MoveList[] getPieceMoves();

    public boolean usesSingleMove() {
        return false;
    }
}
