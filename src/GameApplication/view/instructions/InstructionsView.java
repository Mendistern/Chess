package GameApplication.view.instructions;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * View class that renders the menu for our application
 *
 * @author Nicolas Bouquiaux
 * @version 1.0
 */

public class InstructionsView extends GridPane {
    private Button btnClose;
    private ImageView imageRules;
    private HBox buttonHBox;
    private GridPane gridPane;

    public InstructionsView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        this.imageRules = new ImageView("resources/instructions.png");
        this.buttonHBox = new HBox(10);
        this.btnClose = new Button("close");
    }

    private void layoutNodes() {
        this.buttonHBox.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10, 10, 10, 10));
        buttonHBox.getChildren().addAll(this.btnClose);
        this.add(buttonHBox, 0, 2);
        this.add(imageRules, 0, 1);

    }

    public Button getBtnClose() {
        return btnClose;
    }
}
