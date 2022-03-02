package GameApplication.view.board.components;

public class Rook extends PieceComp {
    public Rook(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "rook";
    }

}

