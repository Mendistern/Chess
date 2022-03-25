package GameApplication.model.chess.piece;

import GameApplication.model.chess.piece.pieces.*;
import GameApplication.model.chess.spot.Spot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PieceSets {

    //hier gaan we alle Piece's insteken
    private List<Piece> pieceSets = new ArrayList<>();
    private boolean checked;



    private PieceColor color;


    public PieceSets(PieceColor color) {


        setPieceStartPositions(color);

        this.color = color;
    }

    public King getKing(){

        for (Piece piece:pieceSets){
            if (piece.getPieceType()==Piecetype.KING){
                return (King) piece;
            }
        }
        return null;
    }


    public void setPieceStartPositions(PieceColor color) {
        //If color is white then row should be 0 (bottom) else 7 (top of board)
        int valueForRow = PieceColor.WHITE == color ? 0 : 7;


        //Sets King and Queen
        pieceSets.add(new King(color, new Spot(4, valueForRow)));
        pieceSets.add(new Queen(color, new Spot(3, valueForRow)));


        //sets the initial value for ROOK , KNIGHT and BISHOP
        for (int i = 0; i < 2; i++) {
            pieceSets.add(new Rook(color, new Spot(i * 7, valueForRow)));
            pieceSets.add(new Knight(color, new Spot(1 + i * 5, valueForRow)));
            pieceSets.add(new Bishop(color, new Spot(2 + i * 3, valueForRow)));
        }
        for (int i = 0; i < 8; i++) {
            pieceSets.add(new Pawn(color, new Spot(i, valueForRow == 0 ? 1 : 6)));
        }

        //add piece to spot
        for (Piece piece : pieceSets) {
            piece.getPieceLocation().setPiece(piece);
        }


    }

    public List<Piece> getPieces() {
        return pieceSets;
    }

    public PieceColor getColor() {
        return color;
    }


    public void removePiece(Piece piece) {
        pieceSets.remove(piece);
    }




    @Override
    public String toString() {
        StringBuilder pieces = new StringBuilder("");
        for (Piece ps :
                pieceSets) {
            pieces.append(String.format("%s%n", ps.toString()));
        }
        return pieces.toString();
    }


    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setColor(PieceColor color) {
        this.color = color;
    }
}