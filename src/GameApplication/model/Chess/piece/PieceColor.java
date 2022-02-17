package GameApplication.model.Chess.piece;

public enum PieceColor {
    BLACK("b"),WHITE("w");
    String name;

    PieceColor(String name){
        this.name = name;
    }

    public String getColorName() {
        return name;
    }
}
