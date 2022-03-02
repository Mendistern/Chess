package GameApplication.view.board.components;

public class King extends PieceComp {
    public King(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "king";
    }

}
