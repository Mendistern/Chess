package GameApplication.view.board;

import GameApplication.model.Chess;
import GameApplication.model.ChessBoard;

public class BoardPresenter {
    private Chess model;
    private BoardView view;

    public BoardPresenter(Chess model, BoardView view) {
        this.model = model;
        this.view = view;
        addEventListeners();
    }

    private void addEventListeners() {
    }


}
