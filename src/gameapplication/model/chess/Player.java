package gameapplication.model.chess;

import gameapplication.model.chess.piece.PieceColor;
/**
 * A player is a person who plays the game
 */

/**
 * A Player is a class that represents a player in the game
 */
public class Player {
    // This is a constructor for the Player class. The constructor takes a String name as a parameter and sets the name
    // attribute to the name passed in.
    private final String name;
    private PieceColor color;
    public Player(String name) {
        this.name = name;
    }

    /**
     * It sets the color of the piece.
     *
     * @param color The color of the piece.
     */
    public void setColor(PieceColor color) {
        this.color = color;
    }
    // A getter method for the name and color attributes.
    public String getName() {
        return name;
    }

    public PieceColor getColor() {
        return color;
    }



}