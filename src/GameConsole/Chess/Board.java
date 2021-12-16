package GameConsole.Chess;



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


import GameConsole.Chess.piece.Piece;
import GameConsole.Chess.piece.PieceColor;
import GameConsole.Chess.piece.PieceSets;
import GameConsole.Chess.spot.Spot;

import java.util.List;

public class Board {


    //creer 64 niewe lege spots, dus een lege schaken bord
    private String[][] spots = new String[8][8];

    //creatie van variabele arary, die voor elke spots waarde, zijn Piece gaat inzetten. (of null)
    private Piece[][] pieceIntern;
    //creer 2 pieceSets voor black and white
    PieceSets[] pieceSets = new PieceSets[2];



    public Board() {
        pieceSets[0] = new PieceSets(PieceColor.WHITE);
        pieceSets[1] = new PieceSets(PieceColor.BLACK);



        drawBoard();


    }




    public void drawBoard() {

        pieceIntern = new Piece[8][8];

        //Update each spot with the latest piece -> get it's type
        for (PieceSets pieceSet:
             pieceSets) {
            //pieceSet 0
            // List van pieces


            for (Piece piece :
                    pieceSet.getPieces()) {
                //System.out.println(piece.getColumn()+ " "+ piece.getRow());

                //voor de spot met waarde [][]
                spots[piece.getColumn()][piece.getRow()] = pieceSet.getColorName() + piece.getPieceType().getType();
                pieceIntern[piece.getColumn()][piece.getRow()] =piece;

            }
        }
        System.out.println("\ta\tb\tc\td\te\tf\tg\th");
        for (int i = 7; i >=0; i--) {
            System.out.print(i+1+"\t");
            for (int j = 0; j <8; j++) {
                if (spots[j][i]==null){
                    System.out.print(".\t");
                    pieceIntern[j][i]=null;
                }else{
                    System.out.print(spots[j][i]+"\t");
                }
            }
            System.out.println();
        }


        /*
        for (int i = 0; i < pieceSets.length; i++) {
            List<Piece> pieces = pieceSets[i].getPieces();
            for (int j = 0; j < pieces.size(); j++) {
                spots[]
            }

        }

         */

        //this.chess = new Piece[sizeX][sizeY];
//        this.countX = countX;
//        this.countY = countY;

        /*
        System.out.println("\ta\tb\tc\td\te\tf\tg\th");
        for (int y = 0; y < 8; y++) {

            System.out.print(8 - y + ".\t");
            for (int x = y; x < chess[y].length; x++) {
                if (this.chess[0][0] != null) {
                    this.chess[y][x].draw();

                    //System.out.print("\t");
                } else {
                    //System.out.print("\t");

                }
            }
            System.out.println();

        }
*/

    }

    public Piece getPieceFromSpot(int column, int row){
        return pieceIntern[column][row];
    }


    public String[][] getSpots() {
        return spots;
    }

    public Piece[][] getPieceIntern() {
        return pieceIntern;
    }
}
