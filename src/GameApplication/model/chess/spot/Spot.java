package GameApplication.model.chess.spot;

import GameApplication.model.chess.piece.Piece;

import java.io.Serializable;

public class Spot implements Serializable {
    //X
    private int column;
    //Y
    private int row;

    Piece piece;

    public Spot(int column, int row) {
        try {
            setInitialSpot(column, row);
            piece = null;
        }catch(SpotException se){
            System.out.println(se.getMessage());
            System.out.println(se.getStackTrace());
        }

    }

    public int getColumn(){
        return column;
    }

    public int getRow(){
        return row;
    }

    public Piece getPiece() {
        return piece;
    }
    public void setPiece(Piece piece){

        this.piece = piece;
    }

    //only used to set initial spot
    //also check if spot is valid
    public void setInitialSpot(int column, int row) throws SpotException{
        //TODO check if spot is already taken. (initially, so basically a bug)

        if (column>=8||column<0){
            throw new SpotException("Column number has to be between 0-7");
        }
        if (row>=8||row<0){
            throw new SpotException("row number has to be between 0-7");
        }

        this.column=column;
        this.row=row;

    }

    public void setSpot(int column, int row){
        this.column=column;
        this.row=row;
    }

    public String getFormattedName(){
        return getPiece().getPieceColor().getColorName() + piece.getPieceType().getType();
    }

    //The column number is converted to ASCII. ROOK is in position 0 +65 = 65 -> 65 in ASCII is A. so char 65 is A
    @Override
    public String toString() {
        return String.format("(%c%d)",column+65,row+1);
    }
}
