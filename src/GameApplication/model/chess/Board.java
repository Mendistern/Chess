package GameApplication.model.chess;


import GameApplication.model.FileManager;
import GameApplication.model.MoveManager;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
import GameApplication.model.chess.piece.PieceSets;
import GameApplication.model.chess.piece.pieces.Bishop;
import GameApplication.model.chess.piece.pieces.King;
import GameApplication.model.chess.piece.pieces.Piecetype;
import GameApplication.model.chess.spot.Spot;

import java.util.*;

public class Board {


    //creer 64 niewe lege spots, dus een lege schaken bord
    private String[][] spots = new String[8][8];

    //creatie van variabele arary, die voor elke spots waarde, zijn Piece gaat inzetten. (of null)
    private Piece[][] pieceIntern;
    //creer 2 pieceSets voor black and white
    private PieceSets[] pieceSets = new PieceSets[2];

    private Player[] players;

    //initiliseer laatse ronde kleur met wit;
    private PieceColor lastTurnColor = PieceColor.WHITE;

    //creer een moveManager
    private MoveManager moveManager;

    private Scanner key = new Scanner(System.in);

    private Player currentPlayer;

    private boolean checkedState;

    private PieceColor checkedColor;


    public Board() {
        pieceSets[0] = new PieceSets(PieceColor.WHITE);
        pieceSets[1] = new PieceSets(PieceColor.BLACK);

        moveManager = new MoveManager(this);

        players = new Player[2];
        // generatePlayers();

        drawBoard();


    }

    public void addPlayer(String name, int index) {
        players[index] = new Player(name);
    }


    public void generatePlayers() {

        String player1;
        String player2;


        Random random = new Random();
        int generatedRandomColor = random.nextInt(2);
        players[0].setColor(PieceColor.values()[generatedRandomColor]);
        players[1].setColor(PieceColor.values()[generatedRandomColor == 0 ? 1 : 0]);

        for (Player player :
                players) {

            System.out.printf("%s is %s%n", player.getName(), player.getColor());
        }

        currentPlayer = players[0];
    }


    public PieceSets[] getPieceSets() {
        return pieceSets;
    }

    public int getArrayIndexForColor(PieceColor color) {
        return color == PieceColor.WHITE ? 0 : 1;
    }

    public void drawBoard() {

        pieceIntern = new Piece[8][8];


        //Empy spots
        spots = new String[8][8];

        //Update each spot with the latest piece -> get it's type
        for (PieceSets pieceSet :
                pieceSets) {
            //pieceSet 0
            // List van pieces


            for (Piece piece :
                    pieceSet.getPieces()) {
                //System.out.println(piece.getColumn()+ " "+ piece.getRow());

                //voor de spot met waarde [][]
                //!!
                //spots[piece.getColumn()][piece.getRow()] = pieceSet.getColorName() + piece.getPieceType().getType();


                pieceIntern[piece.getColumn()][piece.getRow()] = piece;
                pieceIntern[piece.getColumn()][piece.getRow()].getPieceLocation().setPiece(piece);
                piece.getPieceLocation().setPiece(piece);


            }
        }


        System.out.println("\ta\tb\tc\td\te\tf\tg\th");


        for (int i = 7; i >= 0; i--) {
            System.out.print(i + 1 + "\t");
            for (int j = 0; j < 8; j++) {
                if (pieceIntern[j][i] == null) {
                    System.out.print(".\t");
                    pieceIntern[j][i] = null;
                } else {
                    System.out.print(pieceIntern[j][i].getPieceLocation().getFormattedName() + "\t");
                }
            }

            System.out.println();
        }


        //Go to next turn

        //nextTurn();


    }

    public void switchPlayer() {

        for (Player pl :
                players) {
            if (pl.getColor() != lastTurnColor) {
                currentPlayer = pl;
            }
        }
        lastTurnColor = currentPlayer.getColor();
        // lastTurnColor = currentPlayer.getColor() == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
    }

    public void nextTurn() {
        System.out.println("Next turn");
        //initialiseer currentPlayer met waarde om later error te vermijden


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


    /*
    public String[][] getSpots() {
        return spots;
    }

     */

    public List<Piece> getPiecesFromInternalBoard(PieceColor color) {
        List<Piece> pieces = new ArrayList<>();
        for (Piece[] pieceColumns : getPieceIntern()
        ) {
            for (Piece piece : pieceColumns) {
                if (piece != null && piece.getPieceColor() == color) {
                    pieces.add(piece);
                }

            }
        }
        return pieces;
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


}
