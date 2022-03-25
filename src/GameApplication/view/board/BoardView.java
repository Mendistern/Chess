package GameApplication.view.board;

import GameApplication.view.ChessMenu;
import GameApplication.view.board.components.ChessBoard;
import GameApplication.view.board.components.PieceComp;
import GameApplication.view.board.components.Space;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BoardView extends BorderPane {
    public Button[][] buttons = new Button[8][8];
    private Space[][] space;
    private final List<Space> validMovesSpaces;
    private final List<Space> validAttackSpaces;
    private final List<Space> clickedSpace;
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
    private TextField tfPath;
    private Label playerName1;
    private Label playerName2;

    private HBox HBoxGameOver;
    private Button restartGame;


    public BoardView() {
        initialiseNodes();
        layoutNodes();
        validMovesSpaces = new ArrayList<>();
        validAttackSpaces = new ArrayList<>();
        clickedSpace = new ArrayList<>();


    }

    public Button getBtnSave() {
        return btnSave;
    }

    public TextField getTfPath() {
        return tfPath;
    }



    public Space[][] getSpace() {
        return space;
    }
    private Path myFile;

    private void initialiseNodes() {
        board = new ChessBoard(playerIsWhite);
        space = new Space[8][8];

        playerName1 = new Label("Player1");
        playerName2 = new Label("Player2");
        restartGame = new Button("Restart game");

        HBoxGameOver = new HBox();


    }


    private void layoutNodes() {
        chessMenu = new ChessMenu();
        super.setTop(this.getChessMenu());
        Label gameOver = new Label("Game Over. 😘");
        gameOver.setFont(new Font(22));


        HBoxGameOver.getChildren().addAll(gameOver, restartGame);
        HBoxGameOver.setVisible(false);
        HBoxGameOver.setAlignment(Pos.CENTER);
        HBoxGameOver.setSpacing(20);


        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        BorderPane.setMargin(vbox, new Insets(10));
        playerName1.setFont(new Font(18));
        playerName2.setFont(new Font(18));


        vbox.getChildren().addAll(HBoxGameOver, playerName2, board, playerName1);
        super.setCenter(vbox);
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


    public void setPlayerName1(String name) {
        this.playerName1.setText(name);
    }


    public void setPlayerName2(String name) {
        this.playerName2.setText(name);
    }

    public ChessBoard getBoard() {
        return board;
    }

    public boolean isPlayerIsWhite() {
        return playerIsWhite;
    }

    public HBox getHBoxGameOver() {
        return HBoxGameOver;
    }

    public Button getRestartGameButton() {
        return restartGame;
    }
}


