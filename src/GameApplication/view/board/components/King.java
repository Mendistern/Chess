package GameApplication.view.board.components;

import GameApplication.view.board.components.Piece;

public class King extends Piece {
    public King(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "king";
    }

}
