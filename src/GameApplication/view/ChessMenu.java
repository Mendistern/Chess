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
    private MenuItem miLoad;
    private MenuItem miSave;
    private MenuItem miRestart;

    public ChessMenu() {
        Menu mnFile = new Menu("File");
        Menu mnHelp = new Menu("Help");
        miInstructions = new MenuItem("Instructies");
        miLoad = new MenuItem("Load Game");
        miSave = new Menu("Save Game");
        miRestart = new MenuItem("Restart Game");

        mnFile.getItems().add(miSave);
        mnFile.getItems().add(miLoad);
        mnFile.getItems().add(miRestart);
        mnHelp.getItems().add(miInstructions);
        super.getMenus().addAll(mnFile, mnHelp);

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

    public MenuItem getMiRestart() {
        return miRestart;
    }
}
