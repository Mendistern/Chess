package GameApplication.model.chess;

import GameApplication.model.chess.piece.PieceColor;


public class Player {
    private final String name;
    private PieceColor color;



    public Player(String name) {
        this.name = name;
    }

    public void setColor(PieceColor color) {
        this.color = color;
    }



    //getter for player attributes

    public String getName() {
        return name;
    }

    public PieceColor getColor() {
        return color;
    }



}