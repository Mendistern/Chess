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






    }


}

