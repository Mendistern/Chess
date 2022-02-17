package GameApplication.view.board;

import GameApplication.model.Chess;
import GameConsole.Chess.Board;

public class BoardPresenter {
    private Chess model;
    private BoardView view;

    public BoardPresenter(Chess model, BoardView view){
        this.model = model;
        this.view = view;
    }


}
