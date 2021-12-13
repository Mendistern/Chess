package GameConsole.Chess.piece;

import GameConsole.Chess.piece.pieces.Rook;
import GameConsole.Chess.spot.Spot;

import java.util.ArrayList;
import java.util.List;

public class PieceSets {
    List<Piece> pieceSets = new ArrayList<>();
    private PieceColor color;


    public PieceSets(PieceColor color){

        //TODO generate 16 pieces
        setPieceStartPositions(color);

        this.color = color;
    }



    public void setPieceStartPositions(PieceColor color){
        //TODO Set each piece on its position based on piece color.
        if(color == PieceColor.WHITE){
            pieceSets.add(new Rook(color,new Spot(0,0)));
        }else{
            pieceSets.add(new Rook(color,new Spot(0,7)));
        }


    }

    @Override
    public String toString() {
        StringBuilder pieces = new StringBuilder("");
        for (Piece ps:
             pieceSets) {
            pieces.append(" "+ps.toString());
        }
        return pieces.toString();
    }
}
