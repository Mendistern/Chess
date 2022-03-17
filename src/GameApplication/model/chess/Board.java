package GameApplication.model.chess;


import GameApplication.model.MoveManager;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
import GameApplication.model.chess.piece.PieceSets;

import java.util.Random;
import java.util.Scanner;

public class Board {


    //creer 64 niewe lege spots, dus een lege schaken bord
    private String[][] spots = new String[8][8];

    //creatie van variabele arary, die voor elke spots waarde, zijn Piece gaat inzetten. (of null)
    private Piece[][] pieceIntern;
    //creer 2 pieceSets voor black and white
    PieceSets[] pieceSets = new PieceSets[2];

    private Player[] players;

    //initiliseer laatse ronde kleur met wit;
    PieceColor lastTurnColor = PieceColor.WHITE;

    //creer een moveManager
    MoveManager moveManager;

    //create int for ruleOf50
    private int ruleOf50 = 0;

    public int get50MoveRuleTurns() {
        return ruleOf50;
    }
    public void set50MoveRuleTurns(int turns) {
        this.ruleOf50 = turns;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    private int currentTurn = 1;

    Scanner key = new Scanner(System.in);


    public Board() {
        pieceSets[0] = new PieceSets(PieceColor.WHITE);
        pieceSets[1] = new PieceSets(PieceColor.BLACK);

        moveManager = new MoveManager(this);

        generatePlayers();

        drawBoard();


    }


    private void generatePlayers() {
        players = new Player[2];
        String player1;
        String player2;

        do {

            System.out.print("Please enter your name: ");
            player1 = key.nextLine();
        } while (player1.length() == 0);
        players[0] = new Player(player1);

        do {

            System.out.print("Please enter your opponents name: ");
            player2 = key.nextLine();
            System.out.println(player2);
        } while (player2.length() == 0);
        players[1] = new Player(player2);


        Random random = new Random();
        int generatedRandomColor = random.nextInt(2);
        players[0].setColor(PieceColor.values()[generatedRandomColor]);
        players[1].setColor(PieceColor.values()[generatedRandomColor == 0 ? 1 : 0]);

        for (Player player :
                players) {

            System.out.printf("%s is %s%n", player.getName(), player.getColor());
        }

    }


    public PieceSets[] getPieceSets() {
        return pieceSets;
    }

    public int getArrayIndexForColor(PieceColor color){
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
    }

    public void nextTurn() {
        System.out.println("Next turn");
        //initialiseer currentPlayer met waarde om later error te vermijden
        Player currentPlayer = players[0];
        for (Player pl :
                players) {
            if (pl.getColor() == lastTurnColor) {
                currentPlayer = pl;
            }
            currentTurn++;
            ruleOf50++;
        }
        Piece originPiece;
        int columnDest;
        int rowDest;


        lastTurnColor = currentPlayer.getColor() == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
        drawBoard();

        //TODO
        //
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
    /*
    public String[][] getSpots() {
        return spots;
    }

     */

    public Piece[][] getPieceIntern() {
        return pieceIntern;
    }
}
