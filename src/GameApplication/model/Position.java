package GameApplication.model;


public class Position {

    private int lin;
    private char col;

    public Position(int lin, char col) {
        this.col = col;
        this.lin = lin;
    }

    @Override
    public String toString() {
        return col + "" + lin;
    }
}

