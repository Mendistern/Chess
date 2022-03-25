package gameapplication.model.chess.piece.pieces;

public enum Piecetype {
    QUEEN("Q"),KING("K"),PAWN("P"),ROOK("R"),BISHOP("B"),KNIGHT("N");

    private String type;

   Piecetype(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
