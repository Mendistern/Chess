package GameApplication.model.chess.piece;

public enum PieceColor {
    BLACK("b"),WHITE("w");
    private String name;

    PieceColor(String name){
        this.name = name;
    }

    public String getColorName() {
        return name;
    }
}
