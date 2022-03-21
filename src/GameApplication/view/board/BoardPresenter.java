package GameApplication.view.board;

import GameApplication.model.Chess;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
import GameApplication.model.chess.piece.pieces.King;
import GameApplication.model.chess.piece.pieces.Pawn;
import GameApplication.model.chess.spot.Spot;
import GameApplication.view.board.components.Space;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.List;

public class BoardPresenter {
    private Chess model;
    private BoardView view;
    private Piece[][] piecesFromModel;
    public BoardPresenter(Chess model, BoardView view) {
        this.model = model;
        this.view = view;
        addEventListeners();
        updateView();
    }

    private void addEventListeners() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                final int finalX = x;
                final int finalY = y;
                System.out.println("final "+finalY+" "+finalY);
                view.getBoard().getSpaces()[finalX][finalY].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        view.getBoard().setActiveSpace(view.getBoard().spaces[finalX][finalY]);
                        /*if (model.getBoard().getCheckedState()){
                            System.out.println("check state");
                            return;
                        }*/

                        handleValidMoves(model.getBoard().getPieceFromSpot(finalX, finalY));

                        view.getBoard().onSpaceClickV2(model.getBoard(),finalX, finalY);

                        updateView();
                    }

                });
                Space s = view.getBoard().getActiveSpace();
                if (view.getBoard().getActiveSpace() != null) {
                    view.getBoard().getActiveSpace().getStyleClass().removeAll("chess-space-active");


                }
                s = view.getActiveSpace();
                if (view.getBoard().getActiveSpace() != null) {
                    view.getBoard().getActiveSpace().getStyleClass().add("chess-space-active");
                }
            }
        }
    }

    public void handleValidMoves(Piece piece) {
        List<Space> validSpaces =  view.getValidMovesSpaces();
        for (Space space : validSpaces) {
            view.getBoard().getSpaces()[space.getX()][space.getY()].getStyleClass().remove("chess-space-valid");
        }
        List<Space> validAttackSpaces =  view.getValidAttackSpaces();
        for (Space space : validAttackSpaces) {
            view.getBoard().getSpaces()[space.getX()][space.getY()].getStyleClass().remove("chess-space-attackable");
        }
        view.clearValidMovesSpaces();
        view.clearAttackSpaces();
        //check if not clicked on empty cell
        if (piece == null) return;
        PieceColor color = piece.getPieceColor();
        //check if clicked on own piece
        if (model.getBoard().getLastTurnColor() != color) return;
        Spot[][] validSpots = model.getBoard().getPieceFromSpot(piece.getColumn(), piece.getRow()).validMoves(model.getBoard());

        for (Spot[] validSpot : validSpots) {
            for (Spot spot : validSpot) {
                if (spot != null) {
                        view.setValidMovesSpaces(new Space(true,spot.getColumn(),spot.getRow()));
                }
            }
        }

        Piece validAttackSpots = model.getBoard().getPieceFromSpot(piece.getColumn(), piece.getRow());
        for (Spot[] validSpot :validAttackSpots.getValidAttackSpots()) {
            for (Spot spot : validSpot) {
                if (spot != null) {
                    view.setValidAttackSpaces(new Space(true,spot.getColumn(),spot.getRow()));
                }
            }
        }

    }



    public void updateView() {

        piecesFromModel = model.getPiecesOnBoard();
        view.getBoard().defineStartPositions(piecesFromModel);



        King king   = model.getBoard().getKing(model.getBoard().getCheckedColor());

        //remove attack color from kings position
        view.getBoard().getSpaces()[king.getColumn()][king.getRow()].getStyleClass().remove("chess-space-attackable");

        //if king moved, after check, remove attack color from his last position
        view.getBoard().getSpaces()[king.getLastKingPosition().get(0).getColumn()][king.getLastKingPosition().get(0).getRow()].getStyleClass().remove("chess-space-attackable");



       List<Space> validSpaces =  view.getValidMovesSpaces();
       for (Space space : validSpaces) {
           view.getBoard().getSpaces()[space.getX()][space.getY()].getStyleClass().add("chess-space-valid");
       }
       List<Space> validAttackSpaces =  view.getValidAttackSpaces();
       for (Space space : validAttackSpaces) {
           view.getBoard().getSpaces()[space.getX()][space.getY()].getStyleClass().add("chess-space-attackable");
       }



        if (model.getBoard().getCheckedState()){
            view.getBoard().getSpaces()[king.getColumn()][king.getRow()].getStyleClass().add("chess-space-attackable");
        }







    }

    public Piece[][] getPiecesFromModel() {
        return piecesFromModel;
    }
}

      
