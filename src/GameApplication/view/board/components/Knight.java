package GameApplication.view.board.components;

import GameApplication.model.MoveList;
import GameApplication.view.board.components.Piece;

public class Knight extends Piece {
    public Knight(boolean color) {
        super(color);
    }

    @Override
    public String getName() {
        return "knight";
    }

    @Override
    public MoveList[] getPieceMoves() {
        MoveList[] m =
                {
                        MoveList.KNIGHT_LEFT_UP,
                        MoveList.KNIGHT_UP_LEFT,
                        MoveList.KNIGHT_UP_RIGHT,
                        MoveList.KNIGHT_RIGHT_UP,
                        MoveList.KNIGHT_RIGHT_DOWN,
                        MoveList.KNIGHT_DOWN_RIGHT,
                        MoveList.KNIGHT_DOWN_LEFT,
                        MoveList.KNIGHT_LEFT_DOWN
                };
        return m;
    }


}
