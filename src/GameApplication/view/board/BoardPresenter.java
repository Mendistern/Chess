package GameApplication.view.board;

import GameApplication.model.Chess;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
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

//    private void updateView(boolean playerIsWhite) {
//
//        for (int x = 0; x < board.getSpaces()[0].length; x++) {
//            for (int y = 0; y < board.getSpaces()[1].length; y++) {
//                boolean light = ((x + y) % 2 != 0);
//                board.getSpaces()[x][y] = new Space(light, x, y);
//
//                if (playerIsWhite) {
//                    board.add(board.getSpaces()[x][y], x, 7 - y);
//                } else {
//                    board.add(board.getSpaces()[x][y], 7 - x, y);
//                }
//                final int xVal = x;
//                final int yVal = y;
//
//
//            }
//        }
//    }

    private void addEventListeners() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                final int finalX = x;
                final int finalY = y;
                view.getBoard().getSpaces()[finalX][finalY].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
//                        System.out.println("Clicked");
//                        System.out.println((finalY)+ " "+ (finalX) );


                        handleValidMoves(model.getBoard().getPieceFromSpot(finalX, finalY));

                        view.getBoard().setActiveSpace(view.getBoard().spaces[finalX][finalY]);

                        view.getBoard().onSpaceClickV2(model.getBoard(),finalX, finalY);
                       // view.getBoard().onSpaceClick(finalX, finalY);
                       // model.getBoard().nextTurn();
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

              // view.getSpace(). (e -> board.onSpaceClick(finalX, finalY));
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
                    //System.out.println(spot);
                }
            }
        }

        Piece validAttackSpots = model.getBoard().getPieceFromSpot(piece.getColumn(), piece.getRow());
        for (Spot[] validSpot :validAttackSpots.getValidAttackSpots()) {
            for (Spot spot : validSpot) {
                if (spot != null) {

                    view.setValidAttackSpaces(new Space(true,spot.getColumn(),spot.getRow()));
                    //System.out.println(spot);
                }
            }
        }

    }


    public void updateView() {
        piecesFromModel = model.getPiecesOnBoard();
        view.getBoard().defineStartPositions(piecesFromModel);


       List<Space> validSpaces =  view.getValidMovesSpaces();
       for (Space space : validSpaces) {
           view.getBoard().getSpaces()[space.getX()][space.getY()].getStyleClass().add("chess-space-valid");
       }
       List<Space> validAttackSpaces =  view.getValidAttackSpaces();
       for (Space space : validAttackSpaces) {
           view.getBoard().getSpaces()[space.getX()][space.getY()].getStyleClass().add("chess-space-attackable");
       }

    }

    public Piece[][] getPiecesFromModel() {
        return piecesFromModel;
    }
}

      
