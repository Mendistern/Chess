import GameApplication.model.chess.Board;
import GameApplication.model.chess.piece.Piece;

import java.util.Scanner;

public class Game {

    private  Piece[][] piecesOnBoard;
    Scanner sc = new Scanner(System.in);


    /*
    public static void main(String[] args) {
        System.out.println("Chess game ");
//        piece[0][0] sizeX = 8;
//        piece[0][1] sizeY = 8;
        Board brd = new Board();
       // brd.drawBoard();

         piecesOnBoard = brd.getPieceIntern();
    }

     */

    public Game(){
        System.out.println("Chess game ");
//        piece[0][0] sizeX = 8;
//        piece[0][1] sizeY = 8;
        Board brd = new Board();
        // brd.drawBoard();

        piecesOnBoard = brd.getPieceIntern();
    }

    public  Piece[][] getPiecesOnBoard() {
        return piecesOnBoard;
    }


}

