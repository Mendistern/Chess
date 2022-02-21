package GameApplication.model;

public class Bishop extends Piece {
    public Bishop(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "bishop";
    }

}
