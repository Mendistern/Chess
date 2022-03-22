package GameApplication.view.board;

/**
 * Class that processes a move
 *
 * @author Nicolas Bouquiaux
 * @version 1.0
 */


public class Move {
    int oldX;
    int newX;
    int oldY;
    int newY;

    public Move() {
        oldX = 0;
        oldY = 0;
        newX = 1;
        newY = 1;
    }

    public Move(int oldX, int oldY, int newX, int newY) {
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
    }

    public String toString() {
        return (getCharLabel(oldX + 1) + (oldY + 1) + " to " + getCharLabel(newX + 1) + (newY + 1));
    }

    public int getOldX() {
        return this.oldX;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public int getOldY() {
        return this.oldY;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    public int getNewX() {
        return this.newX;
    }

    public void setNewX(int newX) {
        this.newX = newX;
    }

    public int getNewY() {
        return this.newY;
    }

    public void setNewY(int newX) {
        this.newY = newX;
    }

    public int getGapX() {
        return this.newX - this.oldX;
    }

    public int getGapY() {
        return this.newY - this.oldY;
    }

    // Converts x number poisition to character label
    public String getCharLabel(int i) {
        return i > 0 && i < 27 ? String.valueOf((char) (i + 64)) : null;
    }
    //probably unnecessary code but what gives?
}
