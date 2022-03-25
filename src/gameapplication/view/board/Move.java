package gameapplication.view.board;

/**
 * Class that processes a move
 *
 * @author Nicolas Bouquiaux
 * @version 1.0
 */


/**
 * A move is a change in the position of a piece
 */
public class Move {
    int oldX;
    int newX;
    int oldY;
    int newY;

    // This is the constructor of the class. It is used to create a new object of type Move.
    public Move() {
        oldX = 0;
        oldY = 0;
        newX = 1;
        newY = 1;
    }

    // This is the constructor of the class. It is used to create a new object of type Move.
    public Move(int oldX, int oldY, int newX, int newY) {
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
    }

    /**
     * This function returns a string that represents the move
     *
     * @return The string representation of the move.
     */
    public String toString() {
        return (getCharLabel(oldX + 1) + (oldY + 1) + " to " + getCharLabel(newX + 1) + (newY + 1));
    }

    // The getter and setter methods are used to get and set the values of the private fields.
    //     The getter methods are used to get the values of the private fields.
    //     The setter methods are used to set the values of the private fields.
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

}
