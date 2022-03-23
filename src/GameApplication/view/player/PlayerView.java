package GameApplication.view.player;

import javafx.geometry.Insets;
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
public class PlayerView extends BorderPane {
    private Label lblPlayerOne;
    private Label lblPlayerTwo;
    private TextField tfPlayerOne;
    private TextField tfPlayerTwo;
    private Button btnShowBoard;

    public PlayerView() {
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
        //create a vbox for placing both HBoxes on seperate rows
        //layout of the player one's elements
        HBox playerOneBox = new HBox(lblPlayerOne, tfPlayerOne);
        playerOneBox.setSpacing(10);
        //layout of the second player's elements
        HBox playerTwoBox = new HBox(lblPlayerTwo, tfPlayerTwo);
        playerTwoBox.setSpacing(10);

        VBox vboxPlayers = new VBox();
        vboxPlayers.getChildren().addAll(playerOneBox, playerTwoBox);
        vboxPlayers.setSpacing(10);

        setCenter(vboxPlayers);
        setPadding(new Insets(10, 10, 10, 10));
        super.setBottom(btnShowBoard);


        btnShowBoard.setFont(new Font(20));
    }

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
