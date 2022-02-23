package GameApplication.view.board;

import GameApplication.model.Chess;
import GameApplication.view.board.components.Space;
import GameApplication.view.board.components.ChessBoard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class BoardPresenter {
    private Chess model;
    private BoardView view;
    private ChessBoard board;

    public BoardPresenter(Chess model, BoardView view) {
        this.model = model;
        this.view = view;
        board = new ChessBoard();
        //updateView(true);
        addEventListeners();
    }

    private void updateView(boolean playerIsWhite) {

        for (int x = 0; x < board.getSpaces()[0].length; x++) {
            for (int y = 0; y < board.getSpaces()[1].length; y++) {
                boolean light = ((x + y) % 2 != 0);
                board.getSpaces()[x][y] = new Space(light, x, y);

                if (playerIsWhite) {
                    board.add(board.getSpaces()[x][y], x, 7 - y);
                } else {
                    board.add(board.getSpaces()[x][y], 7 - x, y);
                }
                final int xVal = x;
                final int yVal = y;


            }
        }
    }

    private void addEventListeners() {
        for (int x = 0; x < view.getSpace()[0].length; x++) {
            for (int y = 0; y < view.getSpace()[1].length; y++) {
                final int finalX = x;
                final int finalY = y;
                view.getSpace()[finalX][finalY].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                    }

                });

//                view.getSpace(). (e -> board.onSpaceClick(finalX, finalY));
            }
        }
    }
}

      
