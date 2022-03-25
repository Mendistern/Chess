package gameapplication.view.board.components;

/**
 * A rook is a piece that can move any number of squares vertically or horizontally
 */
public class Rook extends PieceComp {
    public Rook(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "rook";
    }

}

