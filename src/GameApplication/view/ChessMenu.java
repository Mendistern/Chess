package GameApplication.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class ChessMenu extends MenuBar {
    private MenuItem miInstructions;
    private MenuItem miSettings;

    public ChessMenu() {
        Menu mnFile = new Menu("File");
        Menu mnInstructions = new Menu("Instructions");
        Menu mnSettings = new Menu("Settings");
        miInstructions = new MenuItem("Instructions");
        miSettings = new MenuItem("Settings");
        mnInstructions.getItems().add(miInstructions);
        mnSettings.getItems().add(miSettings);
        super.getMenus().addAll(mnInstructions);

    }

    public MenuItem getMiInstructions() {
        return miInstructions;
    }

    public MenuItem getMiSettings() {
        return miSettings;
    }
}
