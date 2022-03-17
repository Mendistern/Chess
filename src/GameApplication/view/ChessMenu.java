package GameApplication.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * @author nicolas
 */
public class ChessMenu extends MenuBar {
    private MenuItem miInstructions;
    private MenuItem miSettings;
    private MenuItem miLoad;
    private MenuItem miSave;

    public ChessMenu() {
        Menu mnFile = new Menu("File");
        Menu mnHelp = new Menu("Help");
        miInstructions = new MenuItem("Instructies");
        miSettings = new MenuItem("Instellingen");
        miLoad = new MenuItem("Load Game");
        miSave = new MenuItem("Save Game");
        mnFile.getItems().add(miSettings);
        mnFile.getItems().add(miSave);
        mnFile.getItems().add(miLoad);
        mnHelp.getItems().add(miInstructions);
        super.getMenus().addAll(mnFile, mnHelp);

    }

    public MenuItem getMiLoad() {
        return miLoad;
    }
    public MenuItem getMiSave() {
        return miSave;
    }
    public MenuItem getMiInstructions() {
        return miInstructions;
    }

    public MenuItem getMiSettings() {
        return miSettings;
    }
}
