package GameApplication.view.board;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {


    public Tile(boolean light, int x, int y) {
        setWidth(BoardView.TILE_SIZE);
        setHeight(BoardView.TILE_SIZE);

        relocate(x * BoardView.TILE_SIZE, y * BoardView.TILE_SIZE);

        setFill(light ? Color.valueOf("#FFFFFF") : Color.valueOf("#000000"));
    }

}
