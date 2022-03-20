package GameApplication.view;

import javafx.scene.Scene;
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
        miSave = new Menu("Save Game");
        mnFile.getItems().add(miSettings);
        mnFile.getItems().add(miSave);
        mnFile.getItems().add(miLoad);
        mnHelp.getItems().add(miInstructions);
        super.getMenus().addAll(mnFile, mnHelp);

    }

    public void setMiInstructions(MenuItem miInstructions) {
        this.miInstructions = miInstructions;
    }

    public void setMiSettings(MenuItem miSettings) {
        this.miSettings = miSettings;
    }

    public void setMiLoad(MenuItem miLoad) {
        this.miLoad = miLoad;
    }

    public void setMiSave(MenuItem miSave) {
        this.miSave = miSave;
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

    public void setMiSave(Scene scene) {
    }
}
