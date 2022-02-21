package GameApplication.view.board;


import GameApplication.model.ChessBoard;
import GameApplication.model.Space;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;


public class BoardView extends BorderPane {
    private ChessBoard board;
    private boolean playerIsWhite;

    public BoardView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        board = new ChessBoard(playerIsWhite);
        this.setCenter(board);


    }

    private void layoutNodes() {

    }
}


