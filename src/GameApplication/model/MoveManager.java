package GameApplication.model;

import GameApplication.model.chess.Board;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.spot.Spot;

import java.util.ArrayList;
import java.util.List;

public class MoveManager {

    //Creer een lijst met laatste spots
    List<Spot> spots = new ArrayList<>();

    //behoud de staat van de board.
    private Board board;

    //initialisser het board
    public MoveManager(Board board) {
        this.board = board;
    }



    //bij elke click op de board, wordt een niewe oproep naar addMove gemaakt.

    public void addMove(int column, int row){

        Piece clickedOnPiece = board.getPieceFromSpot(column,row);

        //als de moves lijst is leeg,
        if (spots.isEmpty()){

            // Kijk of er op niks gecklicked werd, zoja doe niks
            if (clickedOnPiece == null)return;
            // als er op een andere piece gecklicked werd, doe ook niks.
            if (clickedOnPiece.getPieceColor()!=board.getLastTurnColor())return;

            //anders, add de spot van de click in de lijst, om vervolgens de move te kunnen maken.
            spots.add(new Spot(column,row));

            //loop niet verder naar de volgende IF-statement
            return;
        }

        //Als er al op een Piece werd geclicked, analyzeer de volgende click.
        if (spots.size()==1){
            //krijg de eerste Spot
            Spot firstSpot = spots.get(0);
            //krijg de Piece van de eerste Spot
            Piece pieceFromSpot = board.getPieceFromSpot(firstSpot.getColumn(), firstSpot.getRow());

            //---
            if (!pieceFromSpot.moveTo(new Spot(column,row))){
                spots.clear();
                return;
            }

            //Krijg de Piece van de huidige click (spot)
            Piece currentPiece = board.getPieceFromSpot(column,row);

            //Als huidige geklickde piece heeft dezelfde kleur als de vorige piece,
            if (currentPiece!=null &&  currentPiece.getPieceColor()==board.getLastTurnColor()){
               // verwijder de vorige spot,
               spots.remove(0);
               //en maak een recursieve oproep naar de addMove methode
               addMove(column,row);
            }else{
                //^De else betekent dat er op een andere Spot met of zonder Piece geklicked werd.
                //Nu bekijken we als de 2de spot van de lijst, 1 van de valid moves van de eerste piece is.
                for (Spot[] validMove : pieceFromSpot.validMoves(board)) {
                    for (Spot spot : validMove){
                        if (spot!=null && spot.getColumn()==column&&spot.getRow()==row){
                            //zoja, add de 2de spot naar de lijst, en roep de make move methode op.
                            spots.add(new Spot(column,row));
                            System.out.println("Valid Move");
                            makeMove();
                        }
                    }
                }

            }

        }


    }

    public void makeMove(){

        board.getPieceFromSpot(spots.get(0).getColumn(),spots.get(0).getRow()).moveToSpot(board,spots.get(1));

        spots.clear();

        board.nextTurn();
        //TODO
    }

    public List<Spot> getMoves() {
        return spots;
    }

}
