package GameConsole.Chess.piece;

import GameConsole.Chess.piece.pieces.Piecetype;
import GameConsole.Chess.spot.Spot;

public abstract class Piece {
    //type enum BLACK of WHITE
    private PieceColor pieceColor;
    //Locatie van de Piece in rij kolom
    private Spot pieceLocation;
    //belangrijk voor castling en enpassant?
    private boolean moved=false;

    public abstract Piecetype getPieceType();

    public Spot getPieceLocation(){
        return pieceLocation;
    }

    public int getColumn(){
        return pieceLocation.getColumn();
    }

    public int getRow(){
        return pieceLocation.getRow();
    }


    //retourneert een array van Spots die deze Piece naartoe kan gaan.
    protected abstract Spot validMoves();

    public Piece(PieceColor pieceColor, Spot pieceLocation) {
        this.pieceColor = pieceColor;
        this.pieceLocation = pieceLocation;

    }

    @Override
    public String toString() {
        return "Piece{" +
                "pieceColor=" + pieceColor +
                ", pieceLocation=" + pieceLocation +
                ", moved=" + moved +
                ", pieceType=" + this.getPieceType() +
                '}';
    }
}
