/**
 * The PlayerPresenter class is responsible for the interaction between the PlayerView and the Chess model
 */
package gameapplication.view.player;

import gameapplication.model.Chess;
import gameapplication.view.board.BoardPresenter;
import gameapplication.view.board.BoardView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class PlayerPresenter {
    // A way to make sure that the model and view are not changed.
    // Making sure that the model is not changed.
    private final Chess model;
    private final PlayerView view;

    public PlayerPresenter(Chess model, PlayerView view) {
        // This is a constructor.
        this.model = model;
        this.view = view;
        // Adding an event handler to the button.
        addEventHandlers();
    }

    private void addEventHandlers() {
        // This is an event handler. It is a way to add an event to a button.
        view.getBtnShowBoard().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                // Adding the players to the board.
                model.getBoard().addPlayer(view.getTfPlayerOne().getText(),0);

                // Adding the second player to the board.
                model.getBoard().addPlayer(view.getTfPlayerTwo().getText(),1);
                // Adding the players to the board.
                model.getBoard().generatePlayers();


                // Creating a new instance of the BoardView class.
                BoardView boardView = new BoardView();
                // This is creating a new instance of the BoardPresenter class.
                BoardPresenter BoardPresenter = new BoardPresenter(model, boardView);
                // This is setting the root of the scene to the boardView.
                view.getScene().setRoot(boardView);
                // This is making sure that the window is the same size as the scene.
                boardView.getScene().getWindow().sizeToScene();


            }
        });

    }

    /**
     * Returns the model of the chess game
     *
     * @return The model object.
     */
    public Chess getModel() {
        return model;
    }
}
