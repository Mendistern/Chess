package GameApplication.view.board.components;

import GameApplication.model.MoveList;

public class King extends Piece {
    public King(boolean color) {
        super(color);
    }

    @Override
    public String getName() {
        return "king";
    }

    @Override
    public MoveList[] getPieceMoves() {
        MoveList[] m =
                {
                        MoveList.UP,
                        MoveList.UP_RIGHT,
                        MoveList.RIGHT,
                        MoveList.DOWN_RIGHT,
                        MoveList.DOWN,
                        MoveList.DOWN_LEFT,
                        MoveList.LEFT,
                        MoveList.UP_LEFT
                };
        return m;
    }

}
