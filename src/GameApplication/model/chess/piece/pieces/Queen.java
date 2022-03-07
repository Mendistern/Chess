package GameApplication.model.chess.piece.pieces;

import GameApplication.model.chess.Board;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
import GameApplication.model.chess.spot.Spot;

public class Queen extends Piece {
    public Queen(PieceColor pieceColor, Spot pieceLocation) {
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

    @Override
    public Spot[][] getValidAttackSpots() {
        return new Spot[0][];
    }

    @Override
    public void attack(Spot spot) {

    }

    @Override
    public boolean checkIfAttacking(Spot spot) {
        return false;
    }
    public Piecetype getPieceType(){
        return Piecetype.QUEEN;
    }

    public Spot[][] validMoves(Board board){
        //todo

        //change later to initialize the spots to the number of valid moves
        Spot[][] spot = new Spot[1][1];
        return new Spot[8][8];
    }
}
