package GameApplication.view.board;

import javafx.scene.layout.GridPane;

public class ChessBoard extends GridPane {
    private ChessField[] fields = new ChessField[64];

    ChessBoard(){
        for (int i = 0; i < 64 ; i++) {
            int x = getX(i);
            int y = getY(i);
            ChessField field = new ChessField(this, x, y);
            add(field, x, y);
            fields[i] = field;
        }
    }

    private int getX(int index ){
        return index % 8;
    }
    private int getY(int index){ return (index - getX(index)) / 8; }

    public ChessField getField(int x, int y){
        return x < 0 || x > 7 || y < 0 || y > 7 ? null: fields[y * 8 + y];
    }

}
