package GameApplication.view.game;

import GameApplication.view.board.BoardView;
import GameApplication.view.board.components.ChessBoard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class GameView extends GridPane {
    private Button btnSave = new Button("Save");
    private Button btnClose;
    private ImageView imageRules;
    private HBox buttonHBox;
    private OptionButton optionButton;
    private GridPane options;
    private Button btnLoad;

    private TextField tfPath;
    private TextArea taContents;
    private Label optionLabel;
    private ChessBoard board;
    private BoardView boardView;

    public GameView() {
        initialiseNodes();
        layoutNodes();


    }

    public Label getOptionLabel() {
        return optionLabel;
    }

//    private void initialiseNodes() {
//
//        board = new ChessBoard(true);
//        btnSave = new Button("Save");
//
//        FileManager manager = new FileManager();
//        GridPane options = new GridPane();
//
//        options.add(new OptionButton("resources/save.png",  e -> {
//            File file = createFileChooser().showSaveDialog(btnClose.getContextMenu());
//                    try {
//                        board.save(file);
//                    } catch (IOException ex) {
//                        ex.printStackTrace();
//                    }
//                }, "Save"), 1,1,1,1
//                );
//
//
//
//    }

    private void initialiseNodes() {
        this.buttonHBox = new HBox(10);
        this.btnClose = new Button("close");
        board = new ChessBoard(true);
        btnSave = new Button("Save");

       // FileManager manager = new FileManager();
        options = new GridPane();

       /* options.add(new OptionButton("resources/save.png", e -> {
                    try {


                        Stage newStage = new Stage();
                        newStage.initOwner(boardView.getScene().getWindow());
                        newStage.initModality(Modality.APPLICATION_MODAL);

                        File file = createFileChooser().showSaveDialog(newStage);
                        board.save(file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }, "Save"), 1, 1, 1, 1
        );*/

    }

    private void layoutNodes() {
        this.buttonHBox.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10, 10, 10, 10));
        buttonHBox.getChildren().addAll(btnSave, options);
        this.add(options, 0, 1);

    }

    public Button getBtnSave() {
        return btnSave;
    }



}


