package GameApplication.view.start;

import GameApplication.model.Chess;
import GameApplication.view.player.PlayerPresenter;
import GameApplication.view.player.PlayerView;
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
    private final Chess model;
    private final StartView view;

    public StartPresenter(Chess model, StartView view) {
        this.model = model;
        this.view = view;
        addEventHandlers();
    }

    private void addEventHandlers() {
        view.getBtnStartGame().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PlayerView playerView = new PlayerView();
                PlayerPresenter playerPresenter = new PlayerPresenter(model, playerView);
                view.getScene().setRoot(playerView);
                playerView.getScene().getWindow().sizeToScene();
            }
        });

    }
}
