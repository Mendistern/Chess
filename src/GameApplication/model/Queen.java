package GameApplication.model;

public class Queen extends Piece {
    public Queen(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "queen";
    }

}
