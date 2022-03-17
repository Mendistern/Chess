package GameApplication.model.chess.piece.pieces;

import GameApplication.model.chess.Board;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
import GameApplication.model.chess.spot.Spot;

public class Bishop extends Piece {
    private Board board;
    private Spot[][] validAttackSpots ;



    public Bishop(PieceColor pieceColor, Spot pieceLocation) {
        super(pieceColor, pieceLocation);
    }

    public Piecetype getPieceType(){
        return Piecetype.BISHOP;
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

       if (board.getPieceIntern()[targetColumn][targetRow]!=null&&board.getPieceIntern()[targetColumn][targetRow].getPieceColor()==board.getLastTurnColor()){
           return false;
       }

       //calculate offset value
       int rowOffset = getRow()<targetRow?1:-1;
       int columnOffset = getColumn()<targetColumn?1:-1;


       //set x (columns) to roll with rows
        int x = getColumn()+columnOffset;

        for (int y=getRow()+rowOffset;y!=targetRow;y+=rowOffset){
            if (board.getPieceIntern()[x][y]!=null){
                return false;
            }
            x+=columnOffset;
        }




       return true;
    }


    @Override
    public boolean checkIfAttacking(Spot spot) {

        if (board.getPieceIntern()[spot.getColumn()][spot.getRow()]!=null&&board.getPieceIntern()[spot.getColumn()][spot.getRow()].getPieceColor()!=board.getLastTurnColor()){

            validAttackSpots[spot.getColumn()][spot.getRow()] = new Spot(spot.getColumn(),spot.getRow());
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
        //haal geattackeerde piece
        Piece attackedPiece = board.getPieceIntern()[spot.getColumn()][spot.getRow()];
        board.getPieceIntern()[attackedPiece.getColumn()][attackedPiece.getRow()] = null;

        //board.getPieceIntern()[attackedPiece.getColumn()][attackedPiece.getRow()] = null;


        board.getPieceSets()[board.getArrayIndexForColor(getAttackerColor())].removePiece(attackedPiece);
        spot.setPiece(this);
        //System.out.println(board.getPieceSets()[board.getArrayIndexForColor(getAttackerColor())]);
        getPieceLocation().setSpot(spot.getColumn(), spot.getRow());
        setMoved(true);
    }



    public Spot[][] validMoves(Board board){
        this.board = board;


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


    @Override
    public boolean moveToSpot(Board board, Spot spot) {
        this.board = board;
        if(!moveTo(spot)) return false;

        if(checkIfAttacking(spot)){
            attack(spot);
            return true;
        }
        //Als alles ok is, beweeg de Piece
        getPieceLocation().setSpot(spot.getColumn(), spot.getRow());
        board.getPieceIntern()[spot.getColumn()][spot.getRow()] = this;

        setMoved(true);
        return true;
    }


    public void setBoard(Board board) {
        this.board = board;
    }
}
