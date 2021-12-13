package GameConsole.Chess.piece.pieces;

public enum Piecetype {
    QUEEN('Q'),KING('K'),PAWN('P'),ROOK('R'),BISHOP('B'),KNIGHT('N');

    char type;

   Piecetype(char type){
        this.type = type;
    }
}
