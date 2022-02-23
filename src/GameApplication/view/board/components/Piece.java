package GameApplication.view.board.components;

import javafx.scene.image.Image;

public abstract class Piece {
    protected Image image;
    protected boolean color;

    public Piece(boolean color) {
        this.color = color;

        String location = "resources/";
        String filename = this.getColor() + "_" + this.getName() + ".png";
        this.image = new Image(location + filename);
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

    protected abstract String getName();
}
