package gameapplication.view.board.components;

/**
 * A King is a Piece that can move one square in any direction
 */
public class King extends PieceComp {
    public King(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "king";
    }

}
