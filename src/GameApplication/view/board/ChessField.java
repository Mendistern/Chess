package GameApplication.view.board;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class ChessField extends Label {
    private int x,y;
    private ChessBoard board;

    ChessField(ChessBoard board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
        setMinSize(50, 50);
        setMaxSize(50, 50);
        setAlignment(Pos.CENTER);
    }
}
