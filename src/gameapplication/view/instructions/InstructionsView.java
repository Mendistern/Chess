/**
 * This class is used to display the instructions for the game
 */
package gameapplication.view.instructions;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * This class is used to display the instructions for the game
 */
public class InstructionsView extends BorderPane {
    // Creating the nodes that will be used in the view.
    private Button btnClose;
    private ImageView imageRules;
    private GridPane gridPane;

    public InstructionsView() {
        // This is the constructor of the class. It is initialising the nodes that will be used in the view.
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        // This is initialising the nodes that will be used in the view.
        this.imageRules = new ImageView("resources/instructions.png");
        this.btnClose = new Button("close");
    }

    private void layoutNodes() {

        // This is setting the image to be the size of the window.
        imageRules.setFitWidth(getMaxWidth());
        setCenter(imageRules);
        setAlignment(imageRules, Pos.CENTER);
        setAlignment(btnClose, Pos.BOTTOM_CENTER);
    }

    /**
     * Returns the button with the name "btnClose"
     *
     * @return The button object.
     */
    public Button getBtnClose() {
        return btnClose;
    }
}
