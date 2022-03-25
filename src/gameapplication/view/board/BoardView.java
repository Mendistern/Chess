/**
 * This class is the view of the board. It is responsible for displaying the board and the pieces
 */


package gameapplication.view.board;

import gameapplication.view.ChessMenu;
import gameapplication.view.board.components.ChessBoard;
import gameapplication.view.board.components.PieceComp;
import gameapplication.view.board.components.Space;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class BoardView extends BorderPane {
    // This is initialising the board and the buttons from the Space class.
    public Button[][] buttons = new Button[8][8];
    private Space[][] space;
    private final List<Space> validMovesSpaces;
    private final List<Space> validAttackSpaces;
    private final List<Space> clickedSpace;
    public Space activeSpace = null;
    public ChessBoard board;
    private ChessMenu chessMenu;
    private Button btnSettings;
    private Button btnInstructions;
    private Button btnSave;


    // This is initialising the pieces from the model.
    private PieceComp[][] piecesFromModel;
    // This is initialising the playerIsWhite boolean to true.
    private boolean playerIsWhite;

    /**
     * Returns the chess menu
     *
     * @return The chess menu.
     */
    public ChessMenu getChessMenu() {
        return chessMenu;
    }

    /**
     * Returns the group that contains the board
     *
     * @return The boardGroup object.
     */
    public Group getBoardGroup() {
        return boardGroup;
    }

    // This is returning the bounds of the game.
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

    /**
     * This function returns the button object that is named "btnSave"
     *
     * @return A button.
     */
    public Button getBtnSave() {
        return btnSave;
    }

    /**
     * Returns the text field that contains the path to the file
     *
     * @return A TextField object.
     */
    public TextField getTfPath() {
        return tfPath;
    }



    /**
     * Returns the 2D array of spaces
     *
     * @return The method returns the space array.
     */
    public Space[][] getSpace() {
        return space;
    }

    /**
     * This function initialises the board and the space
     */
    private void initialiseNodes() {
        // This is initialising the board and the space.
        board = new ChessBoard(playerIsWhite);
        // This is initialising the button array.
        space = new Space[8][8];

        // This is initialising the player names and the restart game button.
        playerName1 = new Label("Player1");
        playerName2 = new Label("Player2");
        restartGame = new Button("Restart game");

        HBoxGameOver = new HBox();


    }


    /**
     * This function is initialising the chess menu and setting the top of the border pane to the chess menu
     */
    private void layoutNodes() {
        // This is initialising the chess menu and setting the top of the border pane to the chess menu.
        chessMenu = new ChessMenu();
        super.setTop(this.getChessMenu());
        Label gameOver = new Label("Game Over. 😘");
        gameOver.setFont(new Font(22));


        // This is adding the game over label and the restart game button to the HBoxGameOver object.
        HBoxGameOver.getChildren().addAll(gameOver, restartGame);
        // This is making the HBoxGameOver object invisible.
        HBoxGameOver.setVisible(false);
        // This is setting the alignment of the HBoxGameOver object to center and the spacing to 20.
        HBoxGameOver.setAlignment(Pos.CENTER);
        HBoxGameOver.setSpacing(20);


        // This is setting the alignment of the VBox to center and the spacing to 10.
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        // This is setting the margin of the VBox object to 10.
        BorderPane.setMargin(vbox, new Insets(10));
        playerName1.setFont(new Font(18));
        playerName2.setFont(new Font(18));


        // This is adding the game over label and the restart game button to the HBoxGameOver object.
        //         This is making the HBoxGameOver object invisible.
        //         This is setting the alignment of the HBoxGameOver object to center and the spacing to 20.
        //         This is setting the alignment of the VBox to center and the spacing to 10.
        //         This is setting the margin of the VBox object to 10.
        //         This is setting the top of the border pane to the chess menu.
        //         This is setting the center of the border pane to the VBox object.
        vbox.getChildren().addAll(HBoxGameOver, playerName2, board, playerName1);
        super.setCenter(vbox);
    }


    // The getActiveSpace() method returns the active space.
    //         The getValidMovesSpaces() method returns the valid moves spaces.
    //         The setValidMovesSpaces() method adds the valid moves spaces to the validMovesSpaces list.
    //         The getValidAttackSpaces() method returns the valid attack spaces.
    //         The setValidAttackSpaces() method adds the valid attack spaces to the validAttackSpaces list.
    //         The getClickedSpace() method returns the        The getHBoxGameOver() method returns the HBoxGameOver
    // object.
    //         The getRestartGameButton() method returns the restart game button.
    //         The isPlayerIsWhite() method returns the playerIsWhite boolean.
    //         The getBoard() method returns the board.
    //         The getActiveSpace() method returns the active space.
    //         The getValidMovesSpaces() method returns the valid moves spaces.
    //         The getValidAttackSpaces() method returns the valid attack spaces.
    //         The getClickedSpace() method returns the clicked space.
    //         The getHBoxGameOver() method returns the HBoxGameOver object

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


