package GameApplication.model.chess;


import GameApplication.model.FileManager;
import GameApplication.model.MoveManager;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
import GameApplication.model.chess.piece.PieceSets;
import GameApplication.model.chess.piece.pieces.King;
import GameApplication.model.chess.piece.pieces.Piecetype;
import GameApplication.model.chess.spot.Spot;

import java.util.*;

public class Board {



    //creatie van variabele arary, die voor elke spots waarde, zijn Piece gaat inzetten. (of null)
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



    public void drawBoard() {

        pieceIntern = new Piece[8][8];




        //Update each spot with the latest piece -> get it's type
        for (PieceSets pieceSet :
                pieceSets) {



            for (Piece piece :
                    pieceSet.getPieces()) {

                pieceIntern[piece.getColumn()][piece.getRow()] = piece;
                //Zet de piece bij de bijhorende spot
                pieceIntern[piece.getColumn()][piece.getRow()].getPieceLocation().setPiece(piece);
                piece.getPieceLocation().setPiece(piece);


            }
        }


    }

    public void switchPlayer() {

        //Loop through players
        for (Player pl :
                players) {
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

        if (isCheck){

        checkForCheckmate();
        }
    }


    public void checkForCheckmate() {
        // Get all pieces from current player
        List<Piece> pieces = getPieceSets()[getArrayIndexForColor(getCurrentPlayer().getColor())].getPieces();
        //Keep a list of available moves to make during chess
        List<Spot> availableMovesDuringChess = new ArrayList<>();

        //loop through the pieces
        for (Iterator<Piece> iterator = pieces.iterator(); iterator.hasNext(); ) {
            Piece next = iterator.next();

            //get the array of the current piece valid move
            Spot[][] validSpots =next.validMoves(this);

            //loop through them
            for (Spot[] columns: validSpots){
                for (Spot spot: columns){

                    if (spot==null)continue;
                    //if the test move return false, then the spot will evade the check
                    if (!moveManager.testMove(spot,next.getPieceLocation())){
                        availableMovesDuringChess.add(spot);
                    }
                }
            }
        }

        //if list is empty, then checkmate
        if (availableMovesDuringChess.isEmpty()){
            setGameOver(true);
        }
    }

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
    public List<Piece> getPiecesFromInternalBoard(PieceColor color) {
        //Initialize a list of piece as type
        List<Piece> pieces = new ArrayList<>();

        //Loop through pieces
        for (Piece[] pieceColumns : getPieceIntern()
        ) {
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
    public int getArrayIndexForColor(PieceColor color) {
        return color == PieceColor.WHITE ? 0 : 1;
    }


    public Piece[][] getPieceIntern() {
        return pieceIntern;
    }


    public void setCheckedState(boolean checkedState) {
        this.checkedState = checkedState;
    }

    public boolean getCheckedState() {
        return checkedState;
    }

    public King getKing(PieceColor color) {
        return pieceSets[getArrayIndexForColor(color)].getKing();
    }

    public PieceColor getOpponentColor() {
        return getCurrentPlayer().getColor() == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
    }

    public void setCheckedColor(PieceColor checkedColor) {
        this.checkedColor = checkedColor;
    }

    public PieceColor getCheckedColor() {
        return checkedColor;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    private void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void addPlayer(String name, int index) {
        players[index] = new Player(name);
    }

    public PieceSets[] getPieceSets() {
        return pieceSets;
    }


}
