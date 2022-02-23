package GameApplication.view.board.components;

import GameApplication.view.board.components.Piece;

public class Knight extends Piece {
    public Knight(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "knight";
    }
}
