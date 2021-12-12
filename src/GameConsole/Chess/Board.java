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


public class Board {


    private static final int numXandNumy = 8;

    //private Piece[] countPieces;
    private Piece[][] chess = new Piece[numXandNumy][numXandNumy];



    public Board() {


        drawBoard();
    }


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


}
