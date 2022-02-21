package GameApplication.model;

public class King extends Piece {
    public King(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "king";
    }

}
