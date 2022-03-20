package GameApplication.view.settings;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Presenter for listing to events from our SettingsView
 *
 * @author Nicolas Bouquiaux
 * @version 1.0
 */

public class SettingsPresenter {
    //TODO
    private Button btnClose;
    private ImageView imageRules;
    private HBox buttonHBox;
    private GridPane gridPane;

    public SettingsPresenter() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        this.imageRules = new ImageView("resources/instructions.png");
        this.buttonHBox = new HBox(10);
        this.btnClose = new Button("close");
    }

    private void layoutNodes() {

    }

    public Button getBtnClose() {
        return btnClose;
    }
}
