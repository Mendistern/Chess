package gameapplication.model.chess.piece.pieces;

import gameapplication.model.chess.Board;
import gameapplication.model.chess.piece.Piece;
import gameapplication.model.chess.piece.PieceColor;
import gameapplication.model.chess.spot.Spot;

public class Queen extends Piece {
    private Spot[][] validAttackSpots ;

    public Queen(PieceColor pieceColor, Spot pieceLocation) {
        super(pieceColor, pieceLocation);
    }


    @Override
    public boolean moveTo(Spot spot) {

        Rook queenAsRook = new Rook(getPieceColor(),new Spot(getColumn(),getRow()));

        queenAsRook.setBoard(getBoard());

        if (queenAsRook.moveTo(spot)){
            return true;
        }

        Bishop queenAsBishop = new Bishop(getPieceColor(),new Spot(getColumn(),getRow()));
        queenAsBishop.setBoard(getBoard());

        if (queenAsBishop.moveTo(spot)){
            return true;
        }


        return false;
    }

    @Override
    public Spot[][] getValidAttackSpots() {
        return validAttackSpots;
    }


    @Override
    public boolean checkIfAttacking(Spot spot) {

        if (getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()]!=null){
            validAttackSpots[spot.getColumn()][spot.getRow()]=new Spot(spot.getColumn(),spot.getRow());
            return true;
        }

        return false;
    }
    public Piecetype getPieceType(){
        return Piecetype.QUEEN;
    }

    public Spot[][] validMoves(Board board){
        super.setBoard(board);

        validAttackSpots = new Spot[8][8];

        Spot[][] validSpots = new Spot[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (moveTo(new Spot(i, j))) {
                    checkIfAttacking(new Spot(i,j));
                    validSpots[i][j] = new Spot(i, j);
                }
            }
        }

        return validSpots;

    }
}