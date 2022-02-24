package GameApplication.view.board.components;

import GameApplication.view.board.components.Piece;

public class Pawn extends Piece {
    public Pawn(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "pawn";
    }

}
