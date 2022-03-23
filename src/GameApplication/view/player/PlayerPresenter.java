package GameApplication.view.player;

import GameApplication.model.Chess;
import GameApplication.view.board.BoardPresenter;
import GameApplication.view.board.BoardView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

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

                model.getBoard().addPlayer(view.getTfPlayerOne().getText(),0);
                model.getBoard().addPlayer(view.getTfPlayerTwo().getText(),1);
                model.getBoard().generatePlayers();

                //view.getTfPlayerOne()


                BoardView boardView = new BoardView();
                BoardPresenter BoardPresenter = new BoardPresenter(model, boardView);
                view.getScene().setRoot(boardView);
                boardView.getScene().getWindow().sizeToScene();


            }
        });

    }

    public Chess getModel() {
        return model;
    }
}
