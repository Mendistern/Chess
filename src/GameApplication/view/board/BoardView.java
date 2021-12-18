package GameApplication.view.board;


import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;


public class BoardView {

    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    private GridPane gridPane;
    private Tile[][] tiles;
    private Tile origin;
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    public BoardView() {
        initialiseNodes();
        //layoutNodes();
    }

    private void initialiseNodes() {
        tiles = new Tile[8][8];
        createContent();
        addPieces();
    }

    private void addPieces() {
        gridPane = new GridPane();
        gridPane.getChildren().clear();


        //add the coordinates around the board
        for (int i = 1; i <= 8; i++) {
            Text coord1 = new Text((char) (64 + i) + "");
            GridPane.setHalignment(coord1, HPos.CENTER);
            gridPane.add(coord1, i, 0);

            Text coord2 = new Text((char) (64 + i) + "");
            GridPane.setHalignment(coord2, HPos.CENTER);
            gridPane.add(coord2, i, 9);

            Text coord3 = new Text(9 - i + "");
            GridPane.setHalignment(coord3, HPos.CENTER);
            gridPane.add(coord3, 0, i);

            Text coord4 = new Text(9 - i + "");
            GridPane.setHalignment(coord4, HPos.CENTER);
            gridPane.add(coord4, 9, i);
        }

    }


    public Pane getView() {
        return gridPane;
    }


    private StackPane createContent() {
        StackPane root = new StackPane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                tiles[x][y] = tile;
                tileGroup.getChildren().add(tile);

            }
        }


        return root;
    }

    public Parent getCreateContent() {
        return createContent();
    }


}
