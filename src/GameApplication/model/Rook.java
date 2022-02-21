package GameApplication.model;

public class Rook extends Piece {
    public Rook(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "rook";
    }

}

