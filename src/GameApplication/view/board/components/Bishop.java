package GameApplication.view.board.components;

import GameApplication.view.board.components.Piece;

public class Bishop extends Piece {
    public Bishop(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "bishop";
    }

}
