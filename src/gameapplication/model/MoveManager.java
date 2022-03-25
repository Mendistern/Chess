package gameapplication.model;

import gameapplication.model.chess.Board;
import gameapplication.model.chess.piece.Piece;
import gameapplication.model.chess.piece.pieces.Pawn;
import gameapplication.model.chess.piece.pieces.Piecetype;
import gameapplication.model.chess.spot.Spot;

import java.util.ArrayList;
import java.util.List;



/**
 * The MoveManager class is used to manage the moves of the pieces. It has a list of spots, which are used to store the
 * last spot clicked. It also has a list of moves, which are used to store the moves made
 */
/**
 * Given a string of the form "column.row:column.row", return a list of the two spots
 *
 * @param column the column of the spot that was clicked
 * @param row the row of the piece that was clicked on
 */
public class MoveManager {

    // This is creating a list of spots, which is used to store the last spot clicked.
    private List<Spot> spots = new ArrayList<>();


    // This is creating a list of strings, which is used to store the moves made.
    private List<String> movesList;


    // Creating a reference to the board.
    private Board board;


    // This is creating a new MoveManager object, and initializing the board and movesList.
    public MoveManager(Board board) {
        this.board = board;
        movesList = new ArrayList<>();
    }


    /**
     * If the first spot in the spots list is empty, add the current spot to the spots list. If the first spot in the spots
     * list is not empty, check if the current spot is a valid move for the piece in the first spot. If the current spot is
     * a valid move, add the current spot to the spots list. If the current spot is not a valid move, clear the spots list
     *
     * @param column the column of the spot that was clicked
     * @param row    the row of the piece that was clicked on
     */
    public void addMove(int column, int row) {

        // Getting the piece from the spot that was clicked on.
        Piece clickedOnPiece = board.getPieceFromSpot(column, row);

        // This is checking if the spots list is empty. If it is empty, it means that the first spot has not been clicked
        // yet.
        if (spots.isEmpty()) {

            // Kijk of er op niks geklicked werd, zoja doe niks
            if (clickedOnPiece == null) return;
            // als er op een andere piece gecklicked werd, doe ook niks.
            if (clickedOnPiece.getPieceColor() != board.getLastTurnColor()) return;

            // This is adding the spot that was clicked on to the spots list.
            spots.add(new Spot(column, row));
            return;
        }


        // The above code is checking if the piece that was clicked on is the same color as the last piece that was moved.
        // If it is the same color, it will remove the first spot from the list of spots. If it is not the same color, it
        // will add the spot to the list of spots.
        if (spots.size() == 1) {

            Spot firstSpot = spots.get(0);

            Piece pieceFromSpot = board.getPieceFromSpot(firstSpot.getColumn(), firstSpot.getRow());

            Piece currentPiece = board.getPieceFromSpot(column, row);

            if (board.getPieceFromSpot(column, row) != null && board.getPieceFromSpot(column, row).getPieceType() == Piecetype.KING) {
                return;
            }


            //Als huidige geklickde piece heeft dezelfde kleur als de vorige piece,
            if (currentPiece != null && currentPiece.getPieceColor() == board.getLastTurnColor()) {
                // verwijder de vorige spot,
                spots.remove(0);
                //en maak een recursieve oproep naar de addMove methode
                addMove(column, row);
            } else {
                //Als move niet mogelijk is
                try {
                    if (!pieceFromSpot.moveTo(new Spot(column, row))) {
                        spots.clear();
                        return;
                    }
                } catch (NullPointerException npe) {
                    pieceFromSpot.setBoard(getBoard());
                    if (!pieceFromSpot.moveTo(new Spot(column, row))) {
                        spots.clear();
                        return;
                    }
                }


                //^De else betekent dat er op een andere Spot met of zonder Piece geklicked werd.
                //Nu bekijken we als de 2de spot van de lijst, 1 van de valid moves van de eerste piece is.
                for (Spot[] validMove : pieceFromSpot.validMoves(board)) {
                    for (Spot spot : validMove) {
                        if (spot != null && spot.getColumn() == column && spot.getRow() == row) {
                            //zoja, add de 2de spot naar de lijst, en roep de make move methode op.


                            //Check if next move will cause check, or disable the check.
                            if (testMove(new Spot(column, row), null)) {
                                return;
                            }


                            //if not in a checked state, or testMove return true
                            spots.add(new Spot(column, row));


                            //prepare next turn
                            makeMove();
                        }
                    }
                }

            }

        }


    }

    //move the piece and prepare next turn

    /**
     * This function moves a piece from one spot to another
     */
    public void makeMove() {

        Piece piece = board.getPieceFromSpot(spots.get(0).getColumn(), spots.get(0).getRow());

        // Actually move the piece
        piece.moveToSpot(board, spots.get(1));

        // This is checking if the piece is a pawn. If it is, it will check if it is promotable.
        if (piece.getPieceType() == Piecetype.PAWN) {
            Pawn pawn = (Pawn) piece;
            pawn.checkIfPromotionAvailable();
        }


        // This is clearing the list of spots, and switching the player.
        addMoveToList();
        spots.clear();
        board.switchPlayer();
        // Checking if the current player is in check. If it is, it will check if the player is in checkmate.
        board.checkForCheck();
        // Switching the player.
        board.nextTurn();

    }


    //Method to check if the next move during check, will evade the check
    /**
     * This function checks if the move is valid by checking if the king is in check after the move
     *
     * @param secondSpot the spot where the piece is moving to
     * @param firstSpot the spot where the piece is currently located
     * @return A boolean value.
     */
    public boolean testMove(Spot secondSpot, Spot firstSpot) {

        //second parameter to check for checkmate, only available if called from the board
        Spot newFirstSpot = firstSpot != null ? firstSpot : spots.get(0);

        //create a reference to the old board
        Board tempBoard = board;
        //create a copy of the old piece, in case there is one on the second spot
        Piece oldPiece = tempBoard.getPieceIntern()[secondSpot.getColumn()][secondSpot.getRow()];

        // get the piece from the first spot
        Piece piece = board.getPieceFromSpot(newFirstSpot.getColumn(), newFirstSpot.getRow());

        //if there was a piece on the second spot, remove it
        if (oldPiece != null) {
            tempBoard.getPieceIntern()[secondSpot.getColumn()][secondSpot.getRow()] = null;
        }
        // remove the piece from the first spot
        tempBoard.getPieceIntern()[newFirstSpot.getColumn()][newFirstSpot.getRow()] = null;
        // set the piece from the first spot in the second spot
        tempBoard.getPieceIntern()[secondSpot.getColumn()][secondSpot.getRow()] = piece;

        //check if after doing this, the check is still there
        if (board.getKing(board.getCurrentPlayer().getColor()).isCheck(tempBoard)) {

            //if yes, put everything back in place
            tempBoard.getPieceIntern()[secondSpot.getColumn()][secondSpot.getRow()] = null;
            tempBoard.getPieceIntern()[newFirstSpot.getColumn()][newFirstSpot.getRow()] = piece;
            if (oldPiece != null) {
                tempBoard.getPieceIntern()[secondSpot.getColumn()][secondSpot.getRow()] = oldPiece;
            }
            return true;
        }
        //if not, also put everything back in place
        tempBoard.getPieceIntern()[secondSpot.getColumn()][secondSpot.getRow()] = null;
        tempBoard.getPieceIntern()[newFirstSpot.getColumn()][newFirstSpot.getRow()] = piece;
        if (oldPiece != null) {
            tempBoard.getPieceIntern()[secondSpot.getColumn()][secondSpot.getRow()] = oldPiece;
        }
        return false;


    }

    /**
     * Add the move to the list of moves
     */
    public void addMoveToList() {
        movesList.add(String.format("%s:%s", spots.get(0).getLocationSpotName(), spots.get(1).getLocationSpotName()));
    }

    /**
     * Given a string of the form "column.row:column.row", return a list of the two spots
     *
     * @param spotString a string that represents a list of spots.
     * @return A list of spots.
     */
    public List<Spot> getSpotFromString(String spotString) {
        String[] twoSpots = spotString.split(":");
        List<Spot> spot = new ArrayList<>();

        if (twoSpots.length == 0) {
            return null;
        }

        for (String singleSpot : twoSpots) {
            String[] columnAndRow = singleSpot.split("\\.");


            try {
                spot.add(new Spot(Integer.parseInt(columnAndRow[0]), Integer.parseInt(columnAndRow[1])));

            } catch (Exception e) {
                return null;
            }


        }
        return spot;

    }

    /**
     * Returns the board
     *
     * @return The board object.
     */
    public Board getBoard() {
        return board;
    }

    public List<String> getMovesList() {
        return movesList;
    }

    public List<Spot> getMoves() {
        return spots;
    }

}
