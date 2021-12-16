import GameConsole.Chess.Board;
import GameConsole.Chess.piece.Piece;

import java.util.Scanner;

public class Game {

    private static Piece[][] piece;
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Chess game ");
//        piece[0][0] sizeX = 8;
//        piece[0][1] sizeY = 8;
        Board brd = new Board();
       // brd.drawBoard();

        Piece[][] piecesOnBoard = brd.getPieceIntern();

        /*
        System.out.println( brd.checkIfSpotIsFree(0,1)==null?"false": brd.checkIfSpotIsFree(0,1).getPieceType());
        System.out.println( brd.checkIfSpotIsFree(4,1)==null?"false":true);
        System.out.println( brd.checkIfSpotIsFree(7,0)==null?"false":true);
        System.out.println( brd.checkIfSpotIsFree(7,3)==null?"false":true);

        System.out.println( brd.checkIfSpotIsFree(7,0)==null?"false":true);
        System.out.println( brd.checkIfSpotIsFree(7,7)==null?"false":true);
         */





    }


}

