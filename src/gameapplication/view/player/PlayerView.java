package gameapplication.view.player;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


/**
 * @author nicolas
 */
/**
 * The PlayerView class is a BorderPane that contains two HBoxes, one for each player. The HBoxes contain a Label and a
 * TextField. The TextFields are used to enter the names of the players. The Button is used to generate the board
 */
public class PlayerView extends BorderPane {
    // These are the nodes that are used in the PlayerView class.
    private Label lblPlayerOne;
    private Label lblPlayerTwo;
    private TextField tfPlayerOne;
    private TextField tfPlayerTwo;
    private Button btnShowBoard;

    public PlayerView() {
        // The initNodes method is used to initialize the nodes that are used in the PlayerView class. The layoutNodes
        // method is used to layout the nodes in the PlayerView class.
        initNodes();
        layoutNodes();
    }

    private void initNodes() {
        //initilization of the labels that are displayed next to the inputfields
        lblPlayerOne = new Label("Naam speler 1:");
        lblPlayerTwo = new Label("Naam speler 2:");
        //initilization of the textFields where the user can enter their names in
        tfPlayerOne = new TextField();
        tfPlayerTwo = new TextField();
        //initilization of the button that shows the board
        btnShowBoard = new Button("Genereer schaakbord");

    }

    //getters to be called upon in the PlayerPresenter class

    private void layoutNodes() {
        //create a vbox for placing both HBoxes on separate rows
        //layout of the player one's elements
        HBox playerOneBox = new HBox(lblPlayerOne, tfPlayerOne);
        playerOneBox.setAlignment(Pos.CENTER);
        playerOneBox.setSpacing(10);

        //layout of the second player's elements
        HBox playerTwoBox = new HBox(lblPlayerTwo, tfPlayerTwo);
        // This is setting the alignment of the HBoxes to center and the spacing between the HBoxes to 10.
        playerTwoBox.setAlignment(Pos.CENTER);
        playerTwoBox.setSpacing(10);

        // This is creating a new VBox. This is used to place the HBoxes on separate rows.
        VBox vboxPlayers = new VBox();
        // This is adding the HBoxes to the VBox.
        vboxPlayers.getChildren().addAll(playerOneBox, playerTwoBox);
        // This is adding a space between the two HBoxes.
        vboxPlayers.setSpacing(10);

        // This is setting the center of the border pane to the vboxPlayers, setting the padding to 10 and setting the
        // bottom of the border pane to the btnShowBoard.
        setCenter(vboxPlayers);
        setPadding(new Insets(10, 10, 10, 10));
        super.setBottom(btnShowBoard);


        // This is setting the font of the button to 20 and the padding to 10.
        btnShowBoard.setFont(new Font(20));
        btnShowBoard.setPadding(new Insets(10));
        BorderPane.setMargin(btnShowBoard,new Insets(10));

        // This is setting the alignment of the vboxPlayers and the btnShowBoard to center.
        BorderPane.setAlignment(vboxPlayers,Pos.CENTER);
        BorderPane.setAlignment(btnShowBoard, Pos.CENTER);

    }

    // This is returning the nodes that are used in the PlayerView class.
    public Label getLblPlayerOne() {
        return lblPlayerOne;
    }

    public Label getLblPlayerTwo() {
        return lblPlayerTwo;
    }

    public TextField getTfPlayerOne() {
        return tfPlayerOne;
    }

    public TextField getTfPlayerTwo() {
        return tfPlayerTwo;
    }

    public Button getBtnShowBoard() {
        return btnShowBoard;
    }


}
