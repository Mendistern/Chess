package gameapplication.model.chess.piece.pieces;

import gameapplication.model.chess.Board;
import gameapplication.model.chess.piece.Piece;
import gameapplication.model.chess.piece.PieceColor;
import gameapplication.model.chess.spot.Spot;

public class Bishop extends Piece {

    private Spot[][] validAttackSpots ;



    public Bishop(PieceColor pieceColor, Spot pieceLocation) {
        super(pieceColor, pieceLocation);
    }



    @Override
    public boolean moveTo(Spot spot) {
        int targetColumn = spot.getColumn();
        int targetRow = spot.getRow();


        //check with or, although && is also good
       if (getColumn()==targetColumn||getRow()==targetRow){
           return false;
       }

       //Because bishop can only move diagonally,
        // the absolute value of the subtraction of the column and row with the target column and row respectively,
        // should be the same
       if (Math.abs(getColumn()-targetColumn)!= Math.abs(getRow()-targetRow)){
           return false;
       }

       if (getBoard().getPieceIntern()[targetColumn][targetRow]!=null&&getBoard().getPieceIntern()[targetColumn][targetRow].getPieceColor()==this.getPieceColor()){
           return false;
       }

       //calculate offset value
       int rowOffset = getRow()<targetRow?1:-1;
       int columnOffset = getColumn()<targetColumn?1:-1;


       //set x (columns) to roll with rows
        int x = getColumn()+columnOffset;

        for (int y=getRow()+rowOffset;y!=targetRow;y+=rowOffset){
            if (getBoard().getPieceIntern()[x][y]!=null){
                return false;
            }
            x+=columnOffset;
        }

       return true;
    }


    @Override
    public boolean checkIfAttacking(Spot spot) {

        //If destination isn't empty && the piece at destination is from the opponent
        if (getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()]!=null&&getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()].getPieceColor()!=getBoard().getLastTurnColor()){

            //add it to the attack aray
            validAttackSpots[spot.getColumn()][spot.getRow()] = new Spot(spot.getColumn(),spot.getRow());
            return true;
        }
        return false;
    }






    public Spot[][] validMoves(Board board){
        super.setBoard(board);


        validAttackSpots = new Spot[8][8];

        Spot[][] validSpots = new Spot[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0;j<8; j++) {
                if(moveTo(new Spot(i,j))){
                    checkIfAttacking(new Spot(i,j));
                    validSpots[i][j] = new Spot(i,j);
                }
            }

        }

        return validSpots;
    }




    public Piecetype getPieceType(){
        return Piecetype.BISHOP;
    }
    @Override
    public Spot[][] getValidAttackSpots() {
        return validAttackSpots;
    }

}
