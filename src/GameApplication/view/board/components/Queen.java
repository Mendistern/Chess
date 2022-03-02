package GameApplication.view.board.components;

import GameApplication.model.MoveList;

public class Queen extends Piece {
    public Queen(boolean color) {
        super(color);
    }

    @Override
    public String getName() {
        return "queen";
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
