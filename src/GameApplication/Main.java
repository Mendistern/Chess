package GameApplication;

import GameApplication.model.Chess;
import GameApplication.view.start.StartPresenter;
import GameApplication.view.start.StartView;
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
 * @author nicolas
 */

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        Chess model = new Chess();

//        BoardView view = new BoardView();
//        BoardPresenter presenter = new BoardPresenter(model, view);
        StartView view = new StartView();
        StartPresenter startPresenter = new StartPresenter(model, view);
        Scene scene = new Scene(view);

        scene.getStylesheets().add("resources/stylesheet.css");
        primaryStage.setScene(scene);


        primaryStage.setTitle("Ultimate Chess");
        primaryStage.show();


        //board.loadFromResource("init.json");

    }
}
