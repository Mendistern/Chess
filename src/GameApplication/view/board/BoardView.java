package GameApplication.view.board;


import GameApplication.view.board.components.Space;
import GameApplication.view.board.components.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;


public class BoardView extends BorderPane {
    public Button[][] buttons = new Button[8][8];
    private Space[][] space;



    private List<Space> validMovesSpaces;

    public Space activeSpace = null;
    public ChessBoard board;

    private PieceComp[][] piecesFromModel;

//    public Space[][] getSpace() {
//        return space;
//    }

    private boolean playerIsWhite;
    private GridPane gamePane;

    public BoardView() {
        initialiseNodes();
        layoutNodes();
        validMovesSpaces = new ArrayList<>();
    }



    private void initialiseNodes() {

        board = new ChessBoard(playerIsWhite);





        space = new Space[8][8];


//
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                boolean light = ((i + j) % 2 != 0);
//                space[i][j] = new Space(playerIsWhite, i, j);
//
//
//            }
//        }

    }

    private void layoutNodes() {
        this.setCenter(board);
    }


    public Space getActiveSpace() {
        return activeSpace;
    }


    public List<Space> getValidMovesSpaces() {
        return validMovesSpaces;
    }

    public void setValidMovesSpaces(Space validMovesSpace) {
        this.validMovesSpaces.add(validMovesSpace);
    }

    public void clearValidMovesSpaces() {
        this.validMovesSpaces.clear();
    }

    public ChessBoard getBoard() {
        return board;
    }

    public boolean isPlayerIsWhite() {
        return playerIsWhite;
    }

    public GridPane getGamePane() {
        return gamePane;
    }


//    private void defineStartPositions() {
//        // white pieces
//        space[0][0].setPiece(new Rook(true));
//        space[1][0].setPiece(new Knight(true));
//        space[2][0].setPiece(new Bishop(true));
//        space[3][0].setPiece(new Queen(true));
//        space[4][0].setPiece(new King(true));
//        space[5][0].setPiece(new Bishop(true));
//        space[6][0].setPiece(new Knight(true));
//        space[7][0].setPiece(new Rook(true));
//
//        for (int i = 0; i < space[0].length; i++)
//            space[i][1].setPiece(new Pawn(true));
//
//        // black pieces
//        space[0][7].setPiece(new Rook(false));
//        space[1][7].setPiece(new Knight(false));
//        space[2][7].setPiece(new Bishop(false));
//        space[3][7].setPiece(new Queen(false));
//        space[4][7].setPiece(new King(false));
//        space[5][7].setPiece(new Bishop(false));
//        space[6][7].setPiece(new Knight(false));
//        space[7][7].setPiece(new Rook(false));
//
//        for (int i = 0; i < space[0].length; i++)
//            space[i][6].setPiece(new Pawn(false));
//    }
}


