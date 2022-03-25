package gameapplication.view.board.components;

/**
 * A knight is a piece that can move in an L shape
 */
public class Knight extends PieceComp {
    public Knight(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "knight";
    }
}
