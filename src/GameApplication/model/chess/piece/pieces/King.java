package GameApplication.model.chess.piece.pieces;

import GameApplication.model.chess.Board;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
import GameApplication.model.chess.spot.Spot;

public class King extends Piece {

    public King(PieceColor pieceColor, Spot pieceLocation) {
        super(pieceColor, pieceLocation);
    }

    @Override
    public boolean moveToSpot(Board board, Spot spot) {
        return false;
    }

    @Override
    public boolean moveTo(Spot spot) {
        boolean returnBoolean = false;
        return returnBoolean;
    }

    public Piecetype getPieceType(){
        return Piecetype.KING;
    }

    public Spot[][] validMoves(Board board){
        //todo

        //change later to initialize the spots to the number of valid moves
        Spot[][] spot = new Spot[1][1];
        return new Spot[8][8];
    }
}
