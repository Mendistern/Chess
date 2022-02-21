package GameApplication.model;

public class Pawn extends Piece {
    public Pawn(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "pawn";
    }

}
