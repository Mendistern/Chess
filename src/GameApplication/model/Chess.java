package GameApplication.model;

import GameApplication.model.chess.Board;
import GameApplication.model.chess.Player;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.spot.Spot;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Chess {

    private Piece[][] piecesOnBoard;
    private Board board;
    private boolean restarted = false;
    private FileManager fileManager;


    public Chess() {
        System.out.println("Chess game ");
//        piece[0][0] sizeX = 8;
//        piece[0][1] sizeY = 8;
        createGame();
    }

    public void createGame(){
        this.board = new Board();
        this.fileManager = new FileManager(this);
        // board.drawBoard();

        this.piecesOnBoard = board.getPieceIntern();
    }

    public Board getBoard() {
        return board;
    }

    /*
    public static void main(String[] args) {
        System.out.println("Chess game ");
//        piece[0][0] sizeX = 8;
//        piece[0][1] sizeY = 8;
        Board brd = new Board();
       // brd.drawBoard();

         piecesOnBoard = brd.getPieceIntern();
    }

     */


    public  Piece[][] getPiecesOnBoard() {
        piecesOnBoard = getBoard().getPieceIntern();
        return piecesOnBoard;
    }


    public void restartGame(){
       Player[] players= getBoard().getPlayers();
        createGame();
        board.setPlayers(players);
        board.generatePlayers();
        board.setCheckedState(false);
        setRestarted(true);

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

    public void restarSavedGame(List<String> readLines) {
        restartGame();

        for (Iterator<String> iterator = readLines.iterator(); iterator.hasNext(); ) {
            String next = iterator.next();
            List<Spot> spot= board.getMoveManager().getSpotFromString(next);

            for (Iterator<Spot> spotIterator = spot.iterator(); spotIterator.hasNext(); ) {
                Spot moveSpot = spotIterator.next();
                board.getMoveManager().addMove(moveSpot.getColumn(),moveSpot.getRow());
            }

        }



    }
}
