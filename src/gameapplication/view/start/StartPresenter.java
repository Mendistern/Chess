package gameapplication.view.start;

import gameapplication.model.Chess;
import gameapplication.view.player.PlayerPresenter;
import gameapplication.view.player.PlayerView;
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
        updateView();
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

    public void updateView(){
        view.getStyleClass().add("start-background");
    }
}
