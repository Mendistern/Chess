package GameApplication.view.board;


import GameApplication.model.Space;
import GameApplication.view.board.components.*;
import javafx.geometry.Pos;
import javafx.scene.layout.*;


public class BoardView extends BorderPane {
    public Space[][] space;
    public Space activeSpace = null;
    //private ChessBoard board;
    private boolean playerIsWhite;
    private GridPane gamePane;

    public BoardView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        gamePane = new GridPane();

        //board = new ChessBoard(playerIsWhite);
        space = new Space[8][8];

        for (int x = 0; x < space[0].length; x++) {
            for (int y = 0; y < space[1].length; y++) {
                boolean light = ((x + y) % 2 != 0);
                space[x][y] = new Space(light, x, y);

                if (playerIsWhite) {
                    gamePane.add(space[x][y], x, 7 - y);
                } else {
                    gamePane.add(space[x][y], 7 - x, y);
                }
                final int xVal = x;
                final int yVal = y;
                //space[x][y].setOnAction(e -> onSpaceClick(xVal, yVal));
            }
        }
        defineStartPositions();

        super.setCenter(gamePane);
    }

    private void layoutNodes() {
        gamePane.setAlignment(Pos.CENTER);
    }

    private void defineStartPositions() {
        // white pieces
        space[0][0].setPiece(new Rook(true));
        space[1][0].setPiece(new Knight(true));
        space[2][0].setPiece(new Bishop(true));
        space[3][0].setPiece(new Queen(true));
        space[4][0].setPiece(new King(true));
        space[5][0].setPiece(new Bishop(true));
        space[6][0].setPiece(new Knight(true));
        space[7][0].setPiece(new Rook(true));

        for (int i = 0; i < space[0].length; i++)
            space[i][1].setPiece(new Pawn(true));

        // black pieces
        space[0][7].setPiece(new Rook(false));
        space[1][7].setPiece(new Knight(false));
        space[2][7].setPiece(new Bishop(false));
        space[3][7].setPiece(new Queen(false));
        space[4][7].setPiece(new King(false));
        space[5][7].setPiece(new Bishop(false));
        space[6][7].setPiece(new Knight(false));
        space[7][7].setPiece(new Rook(false));

        for (int i = 0; i < space[0].length; i++)
            space[i][6].setPiece(new Pawn(false));
    }
}


