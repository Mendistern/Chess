package GameApplication.model;

import GameApplication.model.chess.Board;
import GameApplication.model.chess.Player;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.spot.Spot;

import java.util.Iterator;
import java.util.List;

public class Chess {

    private Piece[][] piecesOnBoard;
    private Board board;
    private boolean restarted = false;
    private FileManager fileManager;


    public Chess() {
        // extra method and not inline, so that restart is possible.
        createGame();
    }

    public void createGame() {
        //initialize
        this.board = new Board();
        this.fileManager = new FileManager(this);


        this.piecesOnBoard = board.getPieceIntern();
    }

    public Board getBoard() {
        return board;
    }


    public Piece[][] getPiecesOnBoard() {
        piecesOnBoard = getBoard().getPieceIntern();
        return piecesOnBoard;
    }


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


    // Method to load a saved game, and to set the pieces on the correct place.
    public void restarSavedGame(List<String> readLines) {
        //First restart the game
        restartGame();

        //loop through all lines from the file
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

    public boolean isRestarted() {
        return restarted;
    }

    public void setRestarted(boolean restarted) {
        this.restarted = restarted;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

}
