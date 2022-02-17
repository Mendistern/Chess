package GameApplication.view.board;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoardView extends GridPane {
    private ImageView lblSquare;
    private BorderPane pane;
    private GridPane table;
    private Image IMG_BLACK_PAWN = new Image("/Black_Pawn.png");
    private Image IMG_BLACK_ROOK = new Image("/Black_Rook.png");
    private Image IMG_BLACK_KING = new Image("/Black_King.png");
    private Image IMG_BLACK_KNIGHT = new Image("/Black_Knight.png");
    private Image IMG_BLACK_QUEEN = new Image("/Black_Queen.png");
    private Image IMG_BLACK_BISHOP = new Image("/Black_Bishop.png");
    private Image[] CHESS_PIECES = {IMG_BLACK_ROOK, IMG_BLACK_KNIGHT, IMG_BLACK_BISHOP, IMG_BLACK_QUEEN, IMG_BLACK_KING, IMG_BLACK_PAWN};
    private List<Image> CHESS_PIECES_LIST = new ArrayList<Image>();
    private ImageView viewRook;
    private ImageView viewKnight;
    private ImageView viewBishop;
    private ChessBoard board;
    public List<Image> getCHESS_PIECES_LIST(){ return CHESS_PIECES_LIST; }
    public Image getIMG_BLACK_PAWN() {return IMG_BLACK_PAWN; }

    public BoardView() {
        initNodes();
        layoutNodes();
    }

    private void layoutNodes() {
        table.setAlignment(Pos.CENTER);
        pane.setCenter(table);

    }

    private void initNodes() {
        this.board = board;
        pane = new BorderPane();
        table = new GridPane();
        Collections.addAll(CHESS_PIECES_LIST, CHESS_PIECES);
        viewRook = new ImageView(CHESS_PIECES_LIST.get(0));
        viewKnight = new ImageView(CHESS_PIECES_LIST.get(1));
        viewBishop = new ImageView(CHESS_PIECES_LIST.get(2));
        table.add(board = new ChessBoard(), 1, 1, 8, 8);
        drawBoard();
        fillBoardColor();
        //setupBoard();
        //table.add(this.chessView, 1, 1, 1, 1);
        //table.add(this.viewPawn, 1, 2, 1,1);



    }

    private void drawBoard() {
        for (int i = 0; i < 8; i++) {
            table.add(newRowLabel(i), 0, i + 1, 1, 1);
            table.add(newRowLabel(i), 9, i + 1, 1, 1);
            table.add(newColLabel(i), i + 1, 0, 1, 1);
            table.add(newColLabel(i), i + 1, 9, 1, 1);
            table.setGridLinesVisible(true);

        }

    }
    private void fillBoardColor() {
        int size = 9;
        Background dark = new Background(new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY));
        Background light = new Background(new BackgroundFill(Color.ANTIQUEWHITE, CornerRadii.EMPTY, Insets.EMPTY));
        for (int row = 1; row < size; row++) {
            for (int col = 1; col < size; col++) {
                StackPane square = new StackPane();
                square.setBackground((row + col) % 2 == 0 ? dark : light);
                table.add(square, col, row);
                square.prefWidthProperty().bind(table.widthProperty().divide(size));
                square.prefHeightProperty().bind(table.heightProperty().divide(size));
            }
        }
    }

    private void setupBoard(){

            //table.add(this.viewRook, 2 , 1, 1, 1 );

        for (int i = 0; i < 2 ; i++) {

            StackPane rook = new StackPane(this.viewRook);
            table.add(rook, 0, 1,1,1);

        }


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

    public BorderPane getPane() {
        return pane;
    }
    public Image[] getCHESS_PIECES(){ return CHESS_PIECES; }
}
