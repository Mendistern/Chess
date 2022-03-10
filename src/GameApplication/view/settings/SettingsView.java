package GameApplication.view.settings;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class SettingsView extends BorderPane {
    private Button btnSave = new Button("Save");

    public SettingsView() {
        super.setBottom(btnSave);
    }

    Button getButtonSave() {
        return btnSave;
    }
}
