package GameApplication.view.board;

import GameApplication.model.Chess;
import GameApplication.view.ChessMenu;
import GameApplication.view.board.components.Space;
import GameApplication.view.board.components.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;

/**
 * Class that renders the View of our application
 *
 * @author Nicolas Bouquiaux
 * @version 1.0
 */

public class BoardView extends BorderPane {
    public Button[][] btn;
    private Space[][] space;
    public Space activeSpace = null;
    public ChessBoard board;

    //refactor gooo brrrtt
    private String pieceList;
    private TextArea textArea;
    private ChessMenu menu;
    private int size;


    private boolean playerIsWhite;

    private GridPane gamePane;

    public BoardView(int size) {
        this.size = size;
        initialiseNodes();
        layoutNodes();
    }

    public void setPieceList(String pieceList) {
        this.pieceList = pieceList;
    }

    private void initialiseNodes() {
        space = new Space[size][size];
        menu = new ChessMenu();
        textArea = new TextArea();
        pieceList = "";
    }

    private void layoutNodes() {
        super.setTop(menu);
        gamePane = new GridPane();


        for (int i = 0; i < space[0].length; i++) {
            for (int j = 0; j < space[1].length; j++) {
                boolean light = ((i + j) % 2 != 0);
                space[i][j] = new Space(light, i, j);
                if (true) {

                    gamePane.add(space[i][j], i, 7 - j);
                } else {
                    gamePane.add(space[i][j], 7 - i, j);
                }
                gamePane.setConstraints(space[i][j], i, j, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
                //space[i][j].setPrefSize(65,65);


            }
        }
        HBox hbBottom = new HBox(textArea);
        hbBottom.setAlignment(Pos.BOTTOM_CENTER);
        hbBottom.setPrefWidth(60);

        this.defineStartPositions();
        super.setCenter(gamePane);
        super.setBottom(hbBottom);
    }

    Space[][] getSpace() {
        return space;
    }

    private Label rowLabel(int i) {
        Label row = new Label(8 - i + "");
        row.setMinSize(20, 50);
        row.setAlignment(Pos.CENTER);
        return row;
    }

    private Label colLabel(int i) {
        Label col = new Label((char) (i + 65) + "");
        col.setMinSize(50, 20);
        col.setAlignment(Pos.CENTER);
        return col;
    }

    public int getX(int index) {
        return index % 8;
    }

    public int getY(int index) {
        return (index - getX(index)) / 8;
    }


    public Space getActiveSpace() {
        return activeSpace;
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


    private void defineStartPositions() {
        // white pieces
        this.space[0][0].setPiece(new Rook(true));
        this.space[1][0].setPiece(new Knight(true));
        this.space[2][0].setPiece(new Bishop(true));
        this.space[3][0].setPiece(new Queen(true));
        this.space[4][0].setPiece(new King(true));
        this.space[5][0].setPiece(new Bishop(true));
        this.space[6][0].setPiece(new Knight(true));
        this.space[7][0].setPiece(new Rook(true));

        for (int i = 0; i < space[0].length; i++)
            this.space[i][1].setPiece(new Pawn(true));

        // black pieces
        this.space[0][7].setPiece(new Rook(false));
        this.space[1][7].setPiece(new Knight(false));
        this.space[2][7].setPiece(new Bishop(false));
        this.space[3][7].setPiece(new Queen(false));
        this.space[4][7].setPiece(new King(false));
        this.space[5][7].setPiece(new Bishop(false));
        this.space[6][7].setPiece(new Knight(false));
        this.space[7][7].setPiece(new Rook(false));

        for (int i = 0; i < space[0].length; i++)
            this.space[i][6].setPiece(new Pawn(false));
    }

    public ChessMenu getMenu() {
        return menu;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    public String coordinatesToNotation(int x, int y) {
        int number = 0;
        char letter = 'z';
        String notation;

        switch (x) {
            case 0:
                number = 8;
                break;
            case 1:
                number = 7;
                break;
            case 2:
                number = 6;
                break;
            case 3:
                number = 5;
                break;
            case 4:
                number = 4;
                break;
            case 5:
                number = 3;
                break;
            case 6:
                number = 2;
                break;
            case 7:
                number = 1;
                break;
            default:
                break;
        }

        switch (y) {
            case 0:
                letter = 'a';
                break;
            case 1:
                letter = 'b';
                break;
            case 2:
                letter = 'c';
                break;
            case 3:
                letter = 'd';
                break;
            case 4:
                letter = 'e';
                break;
            case 5:
                letter = 'f';
                break;
            case 6:
                letter = 'g';
                break;
            case 7:
                letter = 'h';
                break;
            default:
                break;
        }

        notation = letter + Integer.toString(number);
        return notation;
    }


}


