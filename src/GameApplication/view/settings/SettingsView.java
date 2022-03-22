package GameApplication.view.settings;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class SettingsView extends BorderPane {
    private Button btnClose;
    private ImageView imageRules;
    private HBox buttonHBox;
    private GridPane gridPane;

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

    private Button btnSave = new Button("Save");

    public SettingsView() {
        super.setBottom(btnSave);
    }

    Button getButtonSave() {
        return btnSave;
    }
}
