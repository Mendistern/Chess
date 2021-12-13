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
import GameConsole.Chess.piece.Piecetype;
import GameConsole.Chess.spot.Spot;

public class Board {


    private static final int numXandNumy = 8;

    //creer 64 niewe lege spots, dus een lege schaken bord
    private Spot[][] spots = new Spot[8][8];

    //creer 2 pieceSets voor black and white
    PieceSets[] pieceSets = new PieceSets[2];

    //private Piece[] countPieces;
  //  private Piece[][] chess = new Piece[numXandNumy][numXandNumy];



    public Board() {
        pieceSets[0] = new PieceSets(PieceColor.WHITE);
        pieceSets[1] = new PieceSets(PieceColor.BLACK);



        // drawBoard();

        for (PieceSets ps :
                pieceSets) {
            if (ps != null){
                System.out.println(ps);
            }

        }
    }



/*
    public void drawBoard() {

        //this.chess = new Piece[sizeX][sizeY];
//        this.countX = countX;
//        this.countY = countY;
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


    }
*/

}
