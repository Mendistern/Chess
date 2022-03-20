package GameApplication.model.chess.IO;

import GameApplication.model.ChessIO;
import GameApplication.model.MoveManager;
import GameApplication.model.chess.Board;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
import GameApplication.model.chess.spot.Spot;
import GameApplication.view.board.components.ChessBoard;
import GameApplication.view.board.components.PieceComp;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.scene.paint.Color;

import javax.json.bind.JsonbBuilder;
import java.io.*;

import java.util.Date;

public class ChessLoader implements ChessIO {
    private ChessBoard board;

    @Override
    public void load(byte[] data, ChessBoard board) {

    }

    @Override
    public byte[] save(ChessBoard board) {

        this.board = board;
        Board model = new Board();


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("current_turn", model.getCurrentTurn());
        jsonObject.addProperty("50_move_rule_turns", model.get50MoveRuleTurns());
        JsonArray black = new JsonArray();
        JsonArray white = new JsonArray();

        for (Spot figure : model.getMoveManager().getSpots()) {
            JsonObject jsonFigure = new JsonObject();
            jsonFigure.addProperty("type", figure.getPiece().getPieceType().toString());
            jsonFigure.addProperty("pos", figure.getPiece().getPieceLocation().toString());
            figure.getPiece().getPieceType();
            jsonFigure.addProperty("first_turn", model.getCurrentTurn());
            if (figure.getPiece().getPieceColor() == PieceColor.BLACK) {
                black.add(jsonFigure);
            } else {
                white.add(jsonFigure);
            }
        }
        jsonObject.add("black", black);
        jsonObject.add("white", white);
        return JsonbBuilder.create().toJson(jsonObject).getBytes();


    }


    @Override
    public String getFileTypeDescription() {
        return null;
    }

    @Override
    public String getFileExtension() {
        return null;
    }
}
