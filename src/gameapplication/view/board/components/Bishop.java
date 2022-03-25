package gameapplication.view.board.components;


public class Bishop extends PieceComp {
    public Bishop(boolean color) {
        super(color);
    }

    @Override
    protected String getName() {
        return "bishop";
    }

}
