package gameapplication.model.chess.spot;

import gameapplication.model.chess.piece.Piece;

import java.io.Serializable;


/**
 * A spot is a location on the board. It has a column and a row. It also has a piece
 */
public class Spot implements Serializable {

    // Declaring a private variable called column, row & piece.
    private int column;
    private int row;
    private Piece piece;

    // A constructor. It is used to set the initial spot.
    public Spot(int column, int row) {
        try {
            setInitialSpot(column, row);
            piece = null;
        }catch(SpotException se){
          System.exit(1);
        }

    }




    //only used to set initial spot
    //also check if spot is valid
    public void setInitialSpot(int column, int row) throws SpotException{

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

    public String getLocationSpotName(){
        return String.format("%d.%d",column,row);
    }

    //The column number is converted to ASCII. ROOK is in position 0 +65 = 65 -> 65 in ASCII is A. so char 65 is A
    @Override
    public String toString() {
        return String.format("(%c%d)",column+65,row+1);
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
}
