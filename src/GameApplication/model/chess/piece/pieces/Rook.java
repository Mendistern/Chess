package GameApplication.model.chess.piece.pieces;

import GameApplication.model.chess.Board;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
import GameApplication.model.chess.spot.Spot;


public class Rook extends Piece {
    //private Board board;
    private Spot[][] validAttackSpots ;

    public Rook(PieceColor pieceColor, Spot pieceLocation) {
        super(pieceColor, pieceLocation);
    }


    @Override
    public boolean moveTo(Spot spot) {
        int currentColumn = getColumn();
        int currentRow = getRow();


        int destinationColumn = spot.getColumn();
        int destinationRow = spot.getRow();



        //check if destination contains piece of same color
        if (getBoard().getPieceIntern()[destinationColumn][destinationRow] != null && getBoard().getPieceIntern()[destinationColumn][destinationRow].getPieceColor() == getPieceColor()) {
            return false;
        }

        //Check if rook makes a valid direction move
        if (currentColumn != destinationColumn && currentRow != destinationRow) {
            return false;
        }



        //if moving up or down
        if (currentColumn == destinationColumn) {
            int dx;
            int y;

            //check whether up or down.
            dx = currentRow < destinationRow ? 1 : -1;


            //set y to next row. loop till you get to destination. dx to y to check next spot (up or down = dx)
            for (y = currentRow + dx; y != destinationRow; y += dx) {

                //If board on spot dColumn and row y is occupied
                if (getBoard().getPieceIntern()[destinationColumn][y] != null) {

                    return false;
                }
            }

        } else {
            //moves left or right

            int dy,y;
            //check whether up or down.
            dy = currentColumn < destinationColumn ? 1 : -1;
                for (y = currentColumn+dy; y !=destinationColumn; y+=dy) {

                    if (getBoard().getPieceIntern()[y][destinationRow] != null) {

                        return false;
                    }
                }


        }

        return true;

    }


    @Override
    public boolean checkIfAttacking(Spot spot) {

        if (getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()]!=null){
            validAttackSpots[spot.getColumn()][spot.getRow()]=new Spot(spot.getColumn(),spot.getRow());
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


    public Piecetype getPieceType() {
        return Piecetype.ROOK;
    }

    public Spot[][] validMoves(Board board) {
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


}
