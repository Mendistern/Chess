package GameApplication.view.game;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class OptionButton extends Label {

    private Label optionLabel;

    private ImageView icon;

    public OptionButton(String imagePath, EventHandler<? super MouseEvent> mouseClickedEvent, String tooltip) {
        setAlignment(Pos.CENTER);
       // icon = new ImageView(FileManager.loadImage(imagePath, 70, 70));

        setMinHeight(35);
        setMaxHeight(35);
        setMinWidth(40);
        setMaxWidth(40);
        resizeIcon(30);
        setOnMouseClicked(mouseClickedEvent);
        setOnMouseEntered(this::onMouseEntered);
        setOnMouseExited(this::onMouseExited);
        setGraphic(icon);
        setTooltip(new Tooltip(tooltip));

    }

    private void onMouseEntered(MouseEvent e) {
        resizeIcon(35);
        e.consume();
    }

    private void onMouseExited(MouseEvent e) {
        resizeIcon(30);
        e.consume();
    }

    private void resizeIcon(int width) {
        icon.setPreserveRatio(true);
        icon.setFitWidth(width);
    }

}
