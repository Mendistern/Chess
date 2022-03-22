package GameApplication.view.start;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

/**
 * View that welcome's the user
 *
 * @author Nicolas Bouquiaux
 * @version 1.0
 */

/**
 * @author nicolas
 */
public class StartView extends BorderPane {
    private Label lblPlayerNAme;
    private Button btnStartGame;
    private Button btnSettings;
    private Image imgBackground;

    public StartView() {
        initNodes();
        layoutNodes();

    }

    private void initNodes() {
        btnStartGame = new Button("Start Schaakspel");
    }

    private void layoutNodes() {
        btnStartGame.setFont(new Font(20));
        setPrefSize(300, 300);
        setCenter(btnStartGame);
    }

    public Button getBtnStartGame() {
        return btnStartGame;
    }


}
