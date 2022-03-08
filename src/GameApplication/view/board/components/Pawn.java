package GameApplication.view.board.components;

public class Pawn extends PieceComp {
    public Pawn(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "pawn";
    }

}
