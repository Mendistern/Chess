package gameapplication;

import gameapplication.model.Chess;
import gameapplication.view.start.StartPresenter;
import gameapplication.view.start.StartView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class that stages our mainView & model to scenes
 *
 * @author Nicolas Bouquiaux
 * @version 1.0
 */

/**
 * @author nicolas Bouquiaux
 */

/**
 * The main class is the entry point for the program. It creates a new chess game and a new scene. It then creates a new
 * start view and a new start presenter. It then sets the scene to the start view
 */
public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) throws Exception {


        Chess model = new Chess();

        //BoardView boardview = new BoardView();
        //BoardPresenter presenter = new BoardPresenter(model, boardview);
        StartView view = new StartView();
        StartPresenter startPresenter = new StartPresenter(model, view);
        Scene scene = new Scene(view);

        scene.getStylesheets().add("resources/stylesheet.css");
        primaryStage.setScene(scene);


        primaryStage.setTitle("Ultimate Chess");
        primaryStage.show();



    }
}
