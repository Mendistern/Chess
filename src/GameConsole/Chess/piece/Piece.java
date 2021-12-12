package GameConsole.Chess.piece;

import GameConsole.Chess.spot.Spot;

public abstract class Piece {
    //type enum BLACK of WHITE
    private PieceColor pieceColor;
    //Locatie van de Piece in rij kolom
    private Spot[][] pieceLocation;
    //belangrijk voor castling en enpassant?
    private boolean moved=false;

    private Piecetype pieceType;

    //retourneert een array van Spots die deze Piece naartoe kan gaan.
    abstract Spot[][] validMoves();

}
