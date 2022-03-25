package gameapplication.view.board.components;

/**
 * A Pawn is a Piece that can move forward one space
 */
public class Pawn extends PieceComp {
    public Pawn(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "pawn";
    }

}
