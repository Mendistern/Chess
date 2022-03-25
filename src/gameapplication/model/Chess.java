package gameapplication.model;

import gameapplication.model.chess.Board;
import gameapplication.model.chess.Player;
import gameapplication.model.chess.piece.Piece;
import gameapplication.model.chess.spot.Spot;

import java.util.Iterator;
import java.util.List;

/**
 * The chess class is the main class of the program. It contains all the methods and attributes that are needed to play the
 * game
 */
public class Chess {

    // `piecesOnBoard` is a 2D array of pieces.
    //     `board` is a board object.
    //     `restarted` is a boolean that is used to check if the game has been restarted.
    //     `fileManager` is a file manager object.
    private Piece[][] piecesOnBoard;
    private Board board;
    private boolean restarted = false;
    private FileManager fileManager;

    /**
     * Instantiates new a game by invoking the method CreateGame into the default constructor
     */
    public Chess() {

        // Creating a new game by invoking the method `createGame` into the default constructor.
        createGame();
    }

    public void createGame() {
        //initialisation of the attributes for creating a new game
        // This is creating a new board, and a new file manager.
        //         The file manager is created with the chess object as a parameter.
        //         The piecesOnBoard is created with the board object as a parameter.
        this.board = new Board();
        this.fileManager = new FileManager(this);
        this.piecesOnBoard = board.getPieceIntern();
    }

    // Method to load a saved game, and to set the pieces on the correct place.
    public void restartSavedGame(List<String> readLines) {
        //First restart the game
        restartGame();


        // This is looping through the lines in the file, and then looping through the spots in the line.
        for (Iterator<String> iterator = readLines.iterator(); iterator.hasNext(); ) {
            String next = iterator.next();
            // torn every line into 2 spots
            List<Spot> spot = board.getMoveManager().getSpotFromString(next);

            //loop through those 2 spots
            for (Iterator<Spot> spotIterator = spot.iterator(); spotIterator.hasNext(); ) {
                Spot moveSpot = spotIterator.next();

                //add each spot to the move manager
                board.getMoveManager().addMove(moveSpot.getColumn(), moveSpot.getRow());
            }

        }


    }

    /**
     * This function is called when the user clicks the restart button. It resets the game by creating a new instance of
     * the board, and then setting the players to the old players
     */
    public void restartGame() {
        //save players before creating new instantiation of the board
        Player[] players = getBoard().getPlayers();
        //create new board, and file manager
        createGame();
        //set the players
        board.setPlayers(players);
        //add the players to the game (color and currentPlayer)
        board.generatePlayers();
        //reset the state to false
        board.setCheckedState(false);
        //set restarted for presenter purposes
        setRestarted(true);

    }

    /**
     * Getters and setters of the game properties
     */
    // Returning the board object.
    public Board getBoard() {
        return board;
    }


    /**
     * Returns the 2D array of pieces on the board
     *
     * @return A 2D array of pieces.
     */
    public Piece[][] getPiecesOnBoard() {
        piecesOnBoard = getBoard().getPieceIntern();
        return piecesOnBoard;
    }



    /**
     * Returns true if the game has been restarted
     *
     * @return A boolean value.
     */
    // Returning the value of the restarted boolean, and setting the value of the restarted boolean.
    public boolean isRestarted() {
        return restarted;
    }
    public void setRestarted(boolean restarted) {
        this.restarted = restarted;
    }

    /**
     * Returns the file manager
     *
     * @return The fileManager object.
     */
    public FileManager getFileManager() {
        return fileManager;
    }

}
