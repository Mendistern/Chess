package GameApplication.model.Chess.piece.pieces;

import GameApplication.model.Chess.Board;
import GameApplication.model.Chess.piece.Piece;
import GameApplication.model.Chess.piece.PieceColor;
import GameApplication.model.Chess.spot.Spot;

public class King extends Piece {

    public King(PieceColor pieceColor, Spot pieceLocation) {
        super(pieceColor, pieceLocation);
    }

    @Override
    public boolean moveTo(Board board, Spot spot) {
        boolean returnBoolean = false;
        return returnBoolean;
    }

    public Piecetype getPieceType(){
        return Piecetype.KING;
    }

    public Spot validMoves(Board board){
        //todo

        //change later to initialize the spots to the number of valid moves
        Spot[][] spot = new Spot[1][1];
        return spot[3][4];
    }
}
