package gameapplication.model.chess.piece;

import gameapplication.model.chess.Board;
import gameapplication.model.chess.piece.pieces.Piecetype;
import gameapplication.model.chess.spot.Spot;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Piece is a piece on the board. It has a color, a location, and a type
 */
public abstract class Piece implements Serializable {


    private Board board;
    //type enum BLACK of WHITE
    private PieceColor pieceColor;
    //Locatie van de Piece in rij kolom
    private Spot pieceLocation;
    //belangrijk voor castling en enpassant?
    private boolean moved = false;



    /**
     * Given a board, return a list of all the valid moves that can be made on that board
     *
     * @param board The board to check for valid moves.
     * @return An array of spots.
     */
    public abstract Spot[][] validMoves(Board board);

    public Piece(PieceColor pieceColor, Spot pieceLocation) {
        this.pieceColor = pieceColor;
        this.pieceLocation = pieceLocation;

    }


    /**
     * Returns the color of the piece that is attacking the current piece
     *
     * @return The color of the piece that is attacking the king.
     */
    public PieceColor getAttackerColor() {
        return this.getPieceColor()==PieceColor.WHITE ? PieceColor.BLACK:PieceColor.WHITE;
    }


    /**
     * It sets the board field to the board parameter.
     *
     * @param board The board that the game is played on.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Returns the board
     *
     * @return The board object.
     */
    public Board getBoard() {
        return board;
    }


    /**
     * Move the piece to the spot, if the spot is occupied by an enemy piece, attack it
     *
     * @param board The board object that the piece is on.
     * @param spot The spot to move to.
     * @return A boolean value.
     */
    public boolean moveToSpot(Board board,Spot  spot){
       // It sets the board field to the board parameter.
       setBoard(board);
        if(!moveTo(spot)) return false;

        // This is checking if the current piece is attacking the given spot. If it is, it will attack the piece on that
        // spot and return true.
        if(checkIfAttacking(spot)){
            attack(spot);
            return true;
        }

        // This is setting the location of the piece to the spot that was given.
        //         It is also setting the piece on the board to the current piece.
        getPieceLocation().setSpot(spot.getColumn(), spot.getRow());
        getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()] = this;

        // This is setting the moved field to true. This is used to check if the piece has moved.
        setMoved(true);
        return true;
    };

    //get all valid attack spots
    public abstract Spot[][] getValidAttackSpots();

    //execute attack on opponents piece
    /**
     * This function is called when a piece is moved. It checks if the piece is attacking another piece. If it is, it
     * removes the piece from the board and removes it from the piece set
     *
     * @param spot The spot that the piece is attacking.
     */
    public void attack(Spot spot){
        Piece attackedPiece = getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()];
        getBoard().getPieceIntern()[attackedPiece.getColumn()][attackedPiece.getRow()] = null;



        getBoard().getPieceSets()[getBoard().getArrayIndexForColor(getAttackerColor())].removePiece(attackedPiece);
        spot.setPiece(this);
        getPieceLocation().setSpot(spot.getColumn(), spot.getRow());
        setMoved(true);
    };

    //Check if current Piece object is attacking the given Spot paramater. (through spot.getColumn() and spot.getRow)
/**
 * This function checks if the spot is attacking the king
 *
 * @param spot The spot that the player is trying to attack.
 * @return A boolean value.
 */
public abstract boolean checkIfAttacking(Spot spot);

    //is een boolean om een loop rond de move te zetten (zolang move niet valid is moet de player opnieuw een move inzetten)
    /**
     * Move the agent to the given spot
     *
     * @param spot The spot to move to.
     * @return The return type is boolean, which means that the method either returns true or false.
     */
    public abstract boolean moveTo(Spot spot);

    @Override
    // This is the equals method. It is used to check if two objects are equal.
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return pieceColor == piece.pieceColor && getRow() == ((Piece) o).getRow() && getColumn()==((Piece) o).getColumn();
    }

    @Override
    // Overriding the hashcode method.
    public int hashCode() {
        return Objects.hash(pieceColor, getRow(),getColumn());
    }

    @Override
    public String toString() {
        return "Piece{" +
                "pieceColor=" + pieceColor +
                ", pieceLocation=" + pieceLocation +
                ", moved=" + moved +
                ", pieceType=" + this.getPieceType() +
                '}';
    }


    // These are the methods that are used to get the information of the piece.
    public abstract Piecetype getPieceType();
    public Spot getPieceLocation() {
        return pieceLocation;
    }
    public int getColumn(){
        return pieceLocation.getColumn();
    }
    public int getRow(){
        return pieceLocation.getRow();
    }
    public PieceColor getPieceColor() {
        return pieceColor;
    }
    public boolean isMoved() {
        return moved;
    }
    public void setMoved(boolean moved) {
        this.moved = moved;
    }

}
