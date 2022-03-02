package GameApplication.view.board.components;

import GameApplication.model.MoveList;

/**
 * Implements abstract class Piece that represents a Chess piece on the board
 *
 * @author Nicolas Bouquiaux
 * @version 1.0
 */

public class Rook extends Piece {
    public Rook(boolean color) {
        super(color);
    }

    @Override
    public String getName() {
        return "rook";
    }

    @Override
    public MoveList[] getPieceMoves() {
        MoveList[] m =
                {
                        MoveList.UP,
                        MoveList.RIGHT,
                        MoveList.DOWN,
                        MoveList.LEFT
                };
        return m;
    }

}

