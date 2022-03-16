package GameApplication.model.chess.piece.pieces;

import GameApplication.model.chess.Board;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
import GameApplication.model.chess.spot.Spot;

public class Queen extends Piece {
    public Queen(PieceColor pieceColor, Spot pieceLocation) {
        super(pieceColor, pieceLocation);
    }

    //private Board board;

    private Spot[][] validAttackSpots ;


    @Override
    public boolean moveToSpot(Board board, Spot spot) {

        super.setBoard(board);
        if(!moveTo(spot)) return false;

        if(checkIfAttacking(spot)){
            attack(spot);
            return true;
        }

        //Als alles ok is, beweeg de Piece
        getPieceLocation().setSpot(spot.getColumn(), spot.getRow());
        getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()] = this;

        setMoved(true);
        return true;
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
    public void attack(Spot spot) {
        Piece attackedPiece = getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()];
        getBoard().getPieceIntern()[attackedPiece.getColumn()][attackedPiece.getRow()] = null;

        //board.getPieceIntern()[attackedPiece.getColumn()][attackedPiece.getRow()] = null;


        getBoard().getPieceSets()[getBoard().getArrayIndexForColor(getAttackerColor())].removePiece(attackedPiece);
        spot.setPiece(this);
        //System.out.println(board.getPieceSets()[board.getArrayIndexForColor(getAttackerColor())]);
        getPieceLocation().setSpot(spot.getColumn(), spot.getRow());
        setMoved(true);
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

        Piece[][] boardPieces = getBoard().getPieceIntern();
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