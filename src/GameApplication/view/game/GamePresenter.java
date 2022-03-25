package GameApplication.view.game;

import GameApplication.model.Chess;
import GameApplication.view.ChessMenu;
import GameApplication.view.board.components.ChessBoard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

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
                GameView gameView = new GameView();
                GamePresenter gamePresenter = new GamePresenter(model, view);
                // board.save(new File(board.getIO().toString()));
                view.getScene().setRoot(gameView);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Problem saving file!");
                alert.showAndWait();
            }
        });
        menu.getMiLoad().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
            }
        });
    }


}


