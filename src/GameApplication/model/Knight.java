package GameApplication.model;

public class Knight extends Piece {
    public Knight(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "knight";
    }
}
