package GameApplication.view.board;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class BoardView extends GridPane {
    private ImageView lblSquare;
    private BorderPane pane;


    private ChessBoard board;

    public BoardView() {
        initNodes();
        layoutNodes();
    }

    private void layoutNodes() {
        //this.add(lblSquare, 1, 0, 0, 0);
    }

    private void initNodes() {
        lblSquare = new ImageView("/Black_Bishop.png");
        this.board = board;
         pane = new BorderPane();
        GridPane table = new GridPane();
        for (int i = 0; i < 8; i++) {
            table.add(newRowLabel(i), 0, i + 1, 1, 1);
            table.add(newRowLabel(i), 9, i + 1, 1, 1);
            table.add(newColLabel(i), i + 1, 0, 1, 1);
            table.add(newColLabel(i), i + 1, 9, 1, 1);


        }
        table.add(board = new ChessBoard(), 1,1,8,8);
        table.setAlignment(Pos.CENTER);
        pane.setCenter(table);


    }

    private Label newRowLabel(int index) {
        Label labelRow = new Label(8 - index + "");
        labelRow.setMinSize(20, 50);
        labelRow.setAlignment(Pos.CENTER);
        return labelRow;
    }

    private Label newColLabel(int index) {
        Label labelCol = new Label((char) (index + 65) + "");
        labelCol.setMinSize(50, 20);
        labelCol.setAlignment(Pos.CENTER);
        return labelCol;
    }
    public BorderPane getPane(){return pane;}


}
