package gameapplication.model.chess.piece.pieces;

import gameapplication.model.chess.Board;
import gameapplication.model.chess.piece.Piece;
import gameapplication.model.chess.piece.PieceColor;
import gameapplication.model.chess.spot.Spot;


public class Rook extends Piece {
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

                //voorkom out of bound
                if (y<0||y>8)return false;
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

                    //voorkom out of bound
                    if (y<0||y>8)return false;
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





}
