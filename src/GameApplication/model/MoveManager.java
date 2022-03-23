package GameApplication.model;

import GameApplication.model.chess.Board;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
import GameApplication.model.chess.piece.pieces.King;
import GameApplication.model.chess.piece.pieces.Pawn;
import GameApplication.model.chess.piece.pieces.Piecetype;
import GameApplication.model.chess.spot.Spot;

import java.util.ArrayList;
import java.util.Iterator;
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






            //Krijg de Piece van de huidige click (spot)
            Piece currentPiece = board.getPieceFromSpot(column,row);

            if (board.getPieceFromSpot(column,row)!=null&&board.getPieceFromSpot(column,row).getPieceType()==Piecetype.KING){
                return;
            }


            //Als huidige geklickde piece heeft dezelfde kleur als de vorige piece,
            if (currentPiece!=null &&  currentPiece.getPieceColor()==board.getLastTurnColor()){
               // verwijder de vorige spot,
               spots.remove(0);
               //en maak een recursieve oproep naar de addMove methode
               addMove(column,row);
            }else{
                //Als move niet mogelijk is
                if (!pieceFromSpot.moveTo(new Spot(column,row))){
                    spots.clear();
                    return;
                }



                //^De else betekent dat er op een andere Spot met of zonder Piece geklicked werd.
                //Nu bekijken we als de 2de spot van de lijst, 1 van de valid moves van de eerste piece is.
                for (Spot[] validMove : pieceFromSpot.validMoves(board)) {
                    for (Spot spot : validMove){
                        if (spot!=null && spot.getColumn()==column&&spot.getRow()==row){
                            //zoja, add de 2de spot naar de lijst, en roep de make move methode op.



                            //if it's check, then test the second spot, if i'ts going to disable the check
                            if (board.getCheckedState()&&testMove(new Spot(column,row))){
                                return;
                            }


                            //if not in a checked state, or testMove return true
                            spots.add(new Spot(column,row));



                            //prepare next turn
                            makeMove();
                        }
                    }
                }

            }

        }


    }

    //move the piece and prepare next turn
    public void makeMove(){

        Piece piece =  board.getPieceFromSpot(spots.get(0).getColumn(),spots.get(0).getRow());

        // Actually move the piece
       piece.moveToSpot(board,spots.get(1));



       //check if it's a pawn, if yes check if it's promotable
        if (piece.getPieceType()==Piecetype.PAWN){
            Pawn pawn = (Pawn) piece;

            System.out.println("Promotion: "+pawn.checkIfPromotionAvailable());
        }

        //clear the list for next players turn
        spots.clear();

        // switch player
        board.switchPlayer();

        //check if it's check
        board.checkForCheck();

        //draw the board again
        board.nextTurn();



    }


    //Method to check if the next move during check, will evade the check
    public boolean testMove(Spot secondSpot){
        //create a reference to the old board
        Board tempBoard = board;
        //create a copy of the old piece, in case there is one on the second spot
        Piece oldPiece =  tempBoard.getPieceIntern()[secondSpot.getColumn()][secondSpot.getRow()];

        // get the piece from the first spot
        Piece piece = board.getPieceFromSpot(spots.get(0).getColumn(),spots.get(0).getRow());

        //if there was a piece on the second spot, remove it
        if(oldPiece!=null){
            tempBoard.getPieceIntern()[secondSpot.getColumn()][secondSpot.getRow()]=null;
        }
        // remove the piece from the first spot
        tempBoard.getPieceIntern()[spots.get(0).getColumn()][spots.get(0).getRow()]=null;
        // set the piece from the first spot in the second spot
        tempBoard.getPieceIntern()[secondSpot.getColumn()][secondSpot.getRow()] = piece;

        //check if after doing this, the check is still there
        if (board.getKing(board.getCurrentPlayer().getColor()).isCheck(tempBoard)){

            //if yes, put everything back in place
            tempBoard.getPieceIntern()[secondSpot.getColumn()][secondSpot.getRow()]=null;
            tempBoard.getPieceIntern()[spots.get(0).getColumn()][spots.get(0).getRow()] = piece;
            if(oldPiece!=null){
                tempBoard.getPieceIntern()[secondSpot.getColumn()][secondSpot.getRow()]=oldPiece;
            }
            return true;
        }
        //if not, also put everything back in place
        tempBoard.getPieceIntern()[secondSpot.getColumn()][secondSpot.getRow()]=null;
        tempBoard.getPieceIntern()[spots.get(0).getColumn()][spots.get(0).getRow()] = piece;
        if(oldPiece!=null){
            tempBoard.getPieceIntern()[secondSpot.getColumn()][secondSpot.getRow()]=oldPiece;
        }
        return false;


    }

    public List<Spot> getMoves() {
        return spots;
    }

}
