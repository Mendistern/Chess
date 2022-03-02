package GameApplication.view.board.components;

public class Knight extends PieceComp {
    public Knight(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "knight";
    }
}
