package gameapplication.view.board.components;

/**
 * A queen is a piece that can move any number of squares in any direction
 */
public class Queen extends PieceComp {
    public Queen(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "queen";
    }

}
