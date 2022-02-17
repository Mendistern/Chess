package GameApplication.model.Chess;



/*
╔═╤═╤═╤═╤═╤═╤═╤═╗
║♜│♞│♝│♛│♚│♝│♞│♜║
╟─┼─┼─┼─┼─┼─┼─┼─╢
║♟│♟│♟│♟│♟│♟│♟│♟║
╟─┼─┼─┼─┼─┼─┼─┼─╢
║ │░│ │░│ │░│ │░║
╟─┼─┼─┼─┼─┼─┼─┼─╢
║░│ │░│ │░│ │░│ ║
╟─┼─┼─┼─┼─┼─┼─┼─╢
║ │░│ │░│ │░│ │░║
╟─┼─┼─┼─┼─┼─┼─┼─╢
║░│ │░│ │░│ │░│ ║
╟─┼─┼─┼─┼─┼─┼─┼─╢
║♙│♙│♙│♙│♙│♙│♙│♙║
╟─┼─┼─┼─┼─┼─┼─┼─╢
║♖│♘│♗│♕│♔│♗│♘│♖║
╚═╧═╧═╧═╧═╧═╧═╧═╝
 */


import GameApplication.model.Chess.piece.Piece;
import GameApplication.model.Chess.piece.PieceColor;
import GameApplication.model.Chess.piece.PieceSets;
import GameApplication.model.Chess.spot.Spot;

import java.util.Objects;
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

    Scanner key = new Scanner(System.in);


    public Board() {
        pieceSets[0] = new PieceSets(PieceColor.WHITE);
        pieceSets[1] = new PieceSets(PieceColor.BLACK);

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

                if (piece.getRow()==3){
                    System.out.println(piece.getPieceLocation().getPiece());
                }

                pieceIntern[piece.getColumn()][piece.getRow()] = piece;
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

        nextTurn();


    }

    public void nextTurn() {
        //initialiseer currentPlayer met waarde om later error te vermijden
        Player currentPlayer = players[0];
        for (Player pl :
                players) {
            if (pl.getColor() == lastTurnColor) {
                currentPlayer = pl;
            }
        }
        Piece originPiece;
        int columnDest;
        int rowDest;
        do {
            System.out.println();
            System.out.println("move format: kolomRij x kolomRij bv: wQ -> d3 = d1 x d3");
            System.out.println("Your turn: " + currentPlayer.getName());

            //krijg input van user
            String moveType = key.nextLine();
            //split de input in 2 volgens de X na upperCase
            String[] splittedMove = moveType.toUpperCase().split("X");
            //trim de 2 delen van spaties
            splittedMove[0] = splittedMove[0].trim();
            splittedMove[1] = splittedMove[1].trim();

            ////krijg de kolom in nummer formaat, dus van char naar int
            int columnOrigin = splittedMove[0].charAt(0) - 65;
            int rowOrigin = Integer.parseInt(String.valueOf(splittedMove[0].charAt(1))) - 1;

            columnDest = splittedMove[1].charAt(0) - 65;
            rowDest = Integer.parseInt(String.valueOf(splittedMove[1].charAt(1))) - 1;


            originPiece = getPieceFromSpot(columnOrigin, rowOrigin);

            if (originPiece ==null || originPiece.getPieceColor() != lastTurnColor) {
                System.out.println("Please choose one of your pieces.");
            }


        } while ( originPiece==null|| originPiece.getPieceColor() != lastTurnColor || !originPiece.moveTo(this, new Spot(columnDest, rowDest)) );

        lastTurnColor = currentPlayer.getColor() == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
        drawBoard();

        //TODO
        //
    }

    public Piece getPieceFromSpot(int column, int row) {
        return pieceIntern[column][row];
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
