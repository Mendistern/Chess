package GameConsole.Chess.piece.pieces;

public enum Piecetype {
    QUEEN("Q"),KING("K"),PAWN("P"),ROOK("R"),BISHOP("B"),KNIGHT("N");

    String type;

   Piecetype(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
