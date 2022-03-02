package GameApplication.view.board.components;

import GameApplication.model.MoveList;


public class Bishop extends Piece {
    public Bishop(boolean color) {
        super(color);
    }

    @Override
    public String getName() {
        return "bishop";
    }

    @Override
    public MoveList[] getPieceMoves() {
        MoveList[] m =
                {
                        MoveList.UP_RIGHT,
                        MoveList.DOWN_RIGHT,
                        MoveList.DOWN_LEFT,
                        MoveList.UP_LEFT
                };
        return m;
    }

}
