package GameApplication.view.player;

import GameApplication.model.Chess;
import GameApplication.view.board.BoardView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PlayerPresenter {
    private final Chess model;
    private final PlayerView view;

    public PlayerPresenter(Chess model, PlayerView view) {
        this.model = model;
        this.view = view;
        addEventHandlers();
    }

    private void addEventHandlers() {
        view.getBtnShowBoard().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BoardView boardView = new BoardView();
                Stage newStage = new Stage();
                newStage.initOwner(view.getScene().getWindow());
                newStage.initModality(Modality.APPLICATION_MODAL);
                Scene scene = new Scene(boardView);
                //BoardPresenter boardPresenter = new BoardPresenter(model, boardView);
                newStage.setScene(scene);
                newStage.getScene().getWindow().sizeToScene();
            }
        });

    }
}
