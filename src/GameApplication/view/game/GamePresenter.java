package GameApplication.view.game;

import GameApplication.model.Chess;
import GameApplication.model.chess.FileManager;
import GameApplication.model.chess.IO.ChessLoader;
import GameApplication.view.ChessMenu;
import GameApplication.view.board.BoardView;
import GameApplication.view.board.components.ChessBoard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class GamePresenter {
    private GameView view;
    private Chess model;
    private ChessBoard board;
    private ChessMenu menu = new ChessMenu();

    public GamePresenter(Chess model, GameView view) {
        this.model = model;
        this.view = view;
        addEventListeners();
        this.board = new ChessBoard(true);

    }

    private void addEventListeners() {
        menu.getMiSave().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    GameView gameView = new GameView();
                    GamePresenter gamePresenter = new GamePresenter(model, view);
                    board.save(new File(board.getIO().toString()));
                    view.getScene().setRoot(gameView);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Problem saving file!");
                    alert.showAndWait();
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Problem saving file!");
                    alert.setContentText("The path does not exists: " + e.getMessage());
                    alert.showAndWait();
                }
            }
        });
    }


}


