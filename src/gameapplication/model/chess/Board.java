package gameapplication.model.chess;


import gameapplication.model.MoveManager;
import gameapplication.model.chess.piece.Piece;
import gameapplication.model.chess.piece.PieceColor;
import gameapplication.model.chess.piece.PieceSets;
import gameapplication.model.chess.piece.pieces.King;
import gameapplication.model.chess.piece.pieces.Piecetype;
import gameapplication.model.chess.spot.Spot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * The Board class is the main class of the game. It contains all the pieces and the methods to move them
 */
public class Board {


    // Creating an array of pieces.
    private Piece[][] pieceIntern;
    //creer 2 pieceSets voor black and white
    private PieceSets[] pieceSets = new PieceSets[2];

    private Player[] players;

    //initiliseer laatse ronde kleur met wit;
    private PieceColor lastTurnColor = PieceColor.WHITE;

    //creer een moveManager
    private MoveManager moveManager;

    private Player currentPlayer;

    private boolean checkedState;

    private PieceColor checkedColor;

    private boolean gameOver = false;


    public Board() {
        //Creer 2 pieceSets
        pieceSets[0] = new PieceSets(PieceColor.WHITE);
        pieceSets[1] = new PieceSets(PieceColor.BLACK);

        moveManager = new MoveManager(this);

        players = new Player[2];

        drawBoard();


    }


    public void generatePlayers() {


        Random random = new Random();
        int generatedRandomColor = random.nextInt(2);
        //Zet een random kleur
        players[0].setColor(PieceColor.values()[generatedRandomColor]);
        //Zet de omgekeerde van de eerste kleur
        players[1].setColor(PieceColor.values()[generatedRandomColor == 0 ? 1 : 0]);


        //Zet huidige speler
        currentPlayer = players[0];
    }


    /**
     * For each piece set, for each piece in the piece set, set the piece's location to the piece's location
     */
    public void drawBoard() {

        pieceIntern = new Piece[8][8];


        //Update each spot with the latest piece -> get it's type
        for (PieceSets pieceSet : pieceSets) {


            for (Piece piece : pieceSet.getPieces()) {

                pieceIntern[piece.getColumn()][piece.getRow()] = piece;
                //Zet de piece bij de bijhorende spot
                pieceIntern[piece.getColumn()][piece.getRow()].getPieceLocation().setPiece(piece);
                piece.getPieceLocation().setPiece(piece);


            }
        }


    }

    /**
     * Find the next player in the list of players
     */
    public void switchPlayer() {

        //Loop through players
        for (Player pl : players) {
            // Find the next player
            if (pl.getColor() != lastTurnColor) {
                currentPlayer = pl;
            }
        }
        lastTurnColor = currentPlayer.getColor();
    }

    public void nextTurn() {

        drawBoard();

    }

    /**
     * Check if the king is in check, and if so, check if it's checkmate
     */
    public void checkForCheck() {
        // Get all pieces from current player
        List<Piece> pieces = getPieceSets()[getArrayIndexForColor(getCurrentPlayer().getColor())].getPieces();
        // Initialize the king
        King king = null;
        // Find the king
        for (Iterator<Piece> iterator = pieces.iterator(); iterator.hasNext(); ) {
            Piece next = iterator.next();
            if (next.getPieceType() == Piecetype.KING) {
                king = (King) next;
                break;
            }

        }

        // Check if king is in check
        boolean isCheck = king.isCheck(this);

        // Put the status of isCheck, in the current players pieceSet
        pieceSets[getArrayIndexForColor(getCurrentPlayer().getColor())].setChecked(isCheck);

        // If, it's check
        if (isCheck) {
            // Set the color of the player, to later be retrieved by the presenter
            setCheckedColor(getCurrentPlayer().getColor());
        }

        // Add the state to the board
        setCheckedState(isCheck);

        if (isCheck) {

            checkForCheckmate();
        }
    }


    // The above code is looping through all the pieces of the current player and checking if the piece can move to any of
    // the spots in the valid moves array. If the spot is null, then it is not a valid move. If the spot is not null, then
    // it is a valid move. If the spot is not null and the test move returns false, then the spot will evade the check.
    public void checkForCheckmate() {
        // Get all pieces from current player
        List<Piece> pieces = getPieceSets()[getArrayIndexForColor(getCurrentPlayer().getColor())].getPieces();
        //Keep a list of available moves to make during chess
        List<Spot> availableMovesDuringChess = new ArrayList<>();

        //loop through the pieces
        for (Iterator<Piece> iterator = pieces.iterator(); iterator.hasNext(); ) {
            Piece next = iterator.next();

            //get the array of the current piece valid move
            Spot[][] validSpots = next.validMoves(this);

            //loop through them
            for (Spot[] columns : validSpots) {
                for (Spot spot : columns) {

                    if (spot == null) continue;
                    //if the test move return false, then the spot will evade the check
                    if (!moveManager.testMove(spot, next.getPieceLocation())) {
                        availableMovesDuringChess.add(spot);
                    }
                }
            }
        }

        //if list is empty, then checkmate
        if (availableMovesDuringChess.isEmpty()) {
            setGameOver(true);
        }
    }

    // This is a getter method. It is used to get the value of a variable.
    public Piece getPieceFromSpot(int column, int row) {
        return pieceIntern[column][row];
    }
    public PieceColor getLastTurnColor() {
        return lastTurnColor;
    }
    public MoveManager getMoveManager() {
        return moveManager;
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    //get pieces by color from board
    /**
     * Get all the pieces from the internal board with the same color as the given color
     *
     * @param color The color of the pieces you want to get.
     * @return A list of pieces with the same color as the parameter.
     */
    public List<Piece> getPiecesFromInternalBoard(PieceColor color) {
        //Initialize a list of piece as type
        List<Piece> pieces = new ArrayList<>();

        //Loop through pieces
        for (Piece[] pieceColumns : getPieceIntern()) {
            for (Piece piece : pieceColumns) {

                if (piece != null && piece.getPieceColor() == color) {
                    //add the piece with same color
                    pieces.add(piece);
                }

            }
        }
        return pieces;
    }

    //Get array index of piecesets array
    /**
     * Given a color, return the array index for that color
     *
     * @param color The color of the piece.
     * @return The index of the array that corresponds to the color.
     */
    public int getArrayIndexForColor(PieceColor color) {
        return color == PieceColor.WHITE ? 0 : 1;
    }
    /**
     * This function returns the piece array
     *
     * @return The pieceIntern array.
     */
    public Piece[][] getPieceIntern() {
        return pieceIntern;
    }
    /**
     * It sets the checked state of the checkbox.
     *
     * @param checkedState A boolean value that indicates whether the checkbox is checked or not.
     */
    public void setCheckedState(boolean checkedState) {
        this.checkedState = checkedState;
    }
    /**
     * Returns the checked state of the checkbox
     *
     * @return The checked state of the checkbox.
     */
    public boolean getCheckedState() {
        return checkedState;
    }
    /**
     * Get the king of a given color
     *
     * @param color The color of the piece you want to get.
     * @return The King object for the specified color.
     */
    public King getKing(PieceColor color) {
        return pieceSets[getArrayIndexForColor(color)].getKing();
    }
    /**
     * Given the current player's color, return the opponent's color
     *
     * @return The color of the opponent.
     */
    public PieceColor getOpponentColor() {
        // This is a ternary operator. It is a shorthand way of writing an if statement.
        return getCurrentPlayer().getColor() == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
    }
    /**
     * It sets the checked color of the checkbox.
     *
     * @param checkedColor The color of the piece that is currently checked.
     */
    public void setCheckedColor(PieceColor checkedColor) {
        this.checkedColor = checkedColor;
    }
    /**
     * Returns the color of the piece that is currently being checked
     *
     * @return The color that is checked.
     */
    public PieceColor getCheckedColor() {
        return checkedColor;
    }
    /**
     * Returns an array of all the players in the game
     *
     * @return An array of Player objects.
     */
    public Player[] getPlayers() {
        return players;
    }
    /**
     * It sets the players array to the players array passed in.
     *
     * @param players An array of Player objects.
     */
    public void setPlayers(Player[] players) {
        this.players = players;
    }
    // Setting the value of the variable `gameOver` to the value of the parameter `gameOver`.
    /**
     * It sets the gameOver variable to the value passed in.
     *
     * @param gameOver boolean
     */
    private void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    /**
     * Return true if the game is over, otherwise return false.
     *
     * @return A boolean value.
     */
    public boolean isGameOver() {
        return gameOver;
    }
    /**
     * Add a player to the game
     *
     * @param name The name of the player.
     * @param index The index of the player to add.
     */
    public void addPlayer(String name, int index) {
        players[index] = new Player(name);
    }
    /**
     * This function returns an array of PieceSets
     *
     * @return An array of PieceSets.
     */
    public PieceSets[] getPieceSets() {
        return pieceSets;
    }

}
