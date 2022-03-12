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
    private List<Space> validAttackSpaces;

    public Space activeSpace = null;
    public ChessBoard board;

    private PieceComp[][] piecesFromModel;


    private boolean playerIsWhite;
    private GridPane gamePane;

    public BoardView() {
        initialiseNodes();
        layoutNodes();
        validMovesSpaces = new ArrayList<>();
        validAttackSpaces = new ArrayList<>();
    }


    private void initialiseNodes() {

        board = new ChessBoard(playerIsWhite);
        space = new Space[8][8];
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

    public List<Space> getValidAttackSpaces() {
        return validAttackSpaces;
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

    public GridPane getGamePane() {
        return gamePane;
    }

}


