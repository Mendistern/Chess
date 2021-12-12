package GameConsole.Chess.piece;

public enum Piecetype {
    QUEEN('Q'),KING('K'),PAWN('P'),ROOK('R'),BISHOP('B'),KNIGHT('K');

    char type;

   Piecetype(char type){
        this.type = type;
    }
}
