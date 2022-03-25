package gameapplication.model.chess.piece;

// Creating a new type of data called `PieceColor` that has two values: `BLACK` and `WHITE`.
public enum PieceColor {
    BLACK("b"),WHITE("w");
    private String name;

    PieceColor(String name){
        this.name = name;
    }

    /**
     * Returns the color name
     *
     * @return The name of the color.
     */
    public String getColorName() {
        return name;
    }
}
