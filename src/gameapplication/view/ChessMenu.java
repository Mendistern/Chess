package gameapplication.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * @author nicolas
 */
public class ChessMenu extends MenuBar {
    // Creating the menu items.
    private MenuItem miInstructions;
    private MenuItem miLoad;
    private MenuItem miSave;
    private MenuItem miRestart;

    public ChessMenu() {
        // This is creating the menu items.
        Menu mnFile = new Menu("File");
        Menu mnHelp = new Menu("Help");
        miInstructions = new MenuItem("Instructies");
        miLoad = new MenuItem("Load Game");
        miSave = new Menu("Save Game");
        miRestart = new MenuItem("Restart Game");

        // Adding the miSave MenuItem to the mnFile Menu.
        mnFile.getItems().add(miSave);
        mnFile.getItems().add(miLoad);
        mnFile.getItems().add(miRestart);
        mnHelp.getItems().add(miInstructions);
        // Adding the mnFile and mnHelp Menu to the MenuBar.
        super.getMenus().addAll(mnFile, mnHelp);

    }

    /**
     * It sets the value of the miLoad MenuItem to the value of the miLoad MenuItem passed in.
     *
     * @param MenuItem miLoad The menu item that was clicked.
     */
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
