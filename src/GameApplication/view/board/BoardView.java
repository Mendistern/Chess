package GameApplication.view.board;


import GameApplication.view.ChessMenu;
import GameApplication.view.board.components.ChessBoard;
import GameApplication.view.board.components.PieceComp;
import GameApplication.view.board.components.Space;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;


public class BoardView extends BorderPane {
    public Button[][] buttons = new Button[8][8];
    private Space[][] space;
    private List<Space> validMovesSpaces;
    private List<Space> validAttackSpaces;
    private List<Space> clickedSpace;

    public Space activeSpace = null;
    public ChessBoard board;

    public ChessMenu getChessMenu() {
        return chessMenu;
    }

    private ChessMenu chessMenu;
    private Button btnSettings;
    private Button btnInstructions;
    private Button btnSave;


    private PieceComp[][] piecesFromModel;
    private boolean playerIsWhite;

    public Group getBoardGroup() {
        return boardGroup;
    }

    public Bounds getGameBounds() {
        return gameBounds;
    }

    private Group boardGroup;
    private Bounds gameBounds;
    private TextArea gameFlow;
    private GridPane textHolder;

    public BoardView() {
        initialiseNodes();
        layoutNodes();
        validMovesSpaces = new ArrayList<>();
        validAttackSpaces = new ArrayList<>();
        clickedSpace = new ArrayList<>();

    }

    public TextArea getGameFlow() {
        return gameFlow;
    }

    public Space[][] getSpace() {
        return space;
    }

    public GridPane getTextHolder() {
        return textHolder;
    }

    private void initialiseNodes() {
        board = new ChessBoard(playerIsWhite);
        space = new Space[8][8];
        textHolder = new GridPane();


    }


    private void layoutNodes() {
        chessMenu = new ChessMenu();
        gameFlow = new TextArea();
        super.setTop(chessMenu);
        this.setCenter(board);

        //textArea layout
        GridPane.setColumnSpan(gameFlow, 10);
        GridPane.setRowSpan(gameFlow, 2);
        textHolder.setVgap(1);
        textHolder.add(gameFlow, 0, 25);
        GridPane.setHalignment(gameFlow, HPos.CENTER);
        super.setBottom(textHolder);


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

    public List<Space> getValidAttackSpaces() {
        return validAttackSpaces;
    }

    public List<Space> getClickedSpace() {
        return clickedSpace;
    }

    public void setValidAttackSpaces(Space validAttackSpace) {
        validAttackSpaces.add(validAttackSpace);
    }

    public void clearValidMovesSpaces() {
        this.validMovesSpaces.clear();
    }

    public void clearAttackSpaces() {
        this.validAttackSpaces.clear();
    }


    public ChessBoard getBoard() {
        return board;
    }

    public boolean isPlayerIsWhite() {
        return playerIsWhite;
    }

//    public GridPane getGamePane() {
//        return gamePane;
//    }

}


