package GameApplication.view.start;

import GameApplication.model.Chess;
import GameApplication.view.board.BoardPresenter;
import GameApplication.view.board.BoardView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Presenter class for handling events from the startView
 *
 * @author Nicolas Bouquiaux
 * @version 1.0
 */

/**
 * @author nicolas
 */

public class StartPresenter {
    //TODO
    private Chess model;
    private StartView view;

    public StartPresenter(Chess model, StartView view) {
        this.model = model;
        this.view = view;
        addEventHandlers();
    }

    private void addEventHandlers() {
        view.getBtnStartGame().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BoardView boardView = new BoardView();
                BoardPresenter boardPresenter = new BoardPresenter(model, boardView);
                view.getScene().setRoot(boardView);
                boardView.getScene().getWindow().sizeToScene();
            }
        });

    }
}
