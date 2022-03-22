package GameApplication.view.instructions;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * View class that renders the menu for our application
 *
 * @author Nicolas Bouquiaux
 * @version 1.0
 */

/**
 * @author nicolas
 */
public class InstructionsView extends BorderPane {
    private Button btnClose;
    private ImageView imageRules;
    private GridPane gridPane;

    public InstructionsView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        this.imageRules = new ImageView("resources/instructions.png");
        this.btnClose = new Button("close");
    }

    private void layoutNodes() {
        imageRules.setFitWidth(getMaxWidth());
        setCenter(imageRules);
        setAlignment(imageRules, Pos.CENTER);
        setAlignment(btnClose, Pos.BOTTOM_CENTER);
    }

    public Button getBtnClose() {
        return btnClose;
    }
}
