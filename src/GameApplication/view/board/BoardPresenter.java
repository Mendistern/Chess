package GameApplication.view.board;

import GameApplication.model.Chess;
import GameApplication.model.MoveList;
import GameApplication.view.board.components.Piece;
import GameApplication.view.board.components.Space;
import GameApplication.view.board.components.ChessBoard;
import GameApplication.view.instructions.InstructionsView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Presenter class for BoardView to handle Events
 *
 * @author Nicolas Bouquiaux
 * @version 1.0
 */

public class BoardPresenter {
    private Chess model;
    private BoardView view;
    public ChessBoard board;
    private Space[][] spaces = new Space[8][8];
    private Space activeSpace = null;
    private Move p;

    public BoardPresenter(Chess model, BoardView view) {
        this.model = model;
        this.view = view;
        addEventListeners();
    }

    public Move getP() {
        return p;
    }

    private void addEventListeners() {
        //board = new ChessBoard(true);


        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int finalX = x;
                int finalY = y;

                view.getSpace()[x][y].setOnAction(e -> onSpaceClick(finalX, finalY));


            }

        }

        view.getMenu().getMiInstructions().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                InstructionsView instructionsView = new InstructionsView();
                Stage newStage = new Stage();
                newStage.initOwner(view.getScene().getWindow());
                newStage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(instructionsView);
                newStage.setScene(scene);
                newStage.showAndWait();


            }
        });
    }

    private void setActiveSpace(Space s) {
        if (this.activeSpace != null)
            this.activeSpace.getStyleClass().removeAll("chess-space-active");
        {

            this.activeSpace = s;
        }


        //add style to new active location
        if (this.activeSpace != null) {

            this.activeSpace.getStyleClass().add("chess-space-active");
        }

    }

    private void onSpaceClick(int x, int y) {
        Space clickedSpace = view.getSpace()[x][y];

        // if piece is selected && user didn't click on allied piece
        if (activeSpace != null &&
                activeSpace.getPiece() != null &&
                clickedSpace.getPieceColor() != activeSpace.getPieceColor()) {

            p = new Move(activeSpace.getX(), activeSpace.getY(), x, y);


            // update gameboard
            this.processMove(p);

            //decouples space from space on board
            setActiveSpace(null);
        } else {
            //if there's a piece on the selected square when no active square but totally irrelevant
            if (view.getSpace()[x][y].getPiece() != null) {
                //makes ui look irrelevant
                setActiveSpace(view.getSpace()[x][y]);
            }
        }
    }

    boolean processMove(Move p) {
        if (moveIsValid(p)) {
            Space oldSpace = view.getSpace()[p.getOldX()][p.getOldY()];
            Space newSpace = view.getSpace()[p.getNewX()][p.getNewY()];

            newSpace.setPiece(oldSpace.releasePiece());
            return true;
        } else {
            return false;
        }
    }

    boolean moveIsValid(Move p) {
        Space oldSpace;
        Space newSpace;
        Piece piece;
        MoveList[] moves;

        if (p == null) {
            return false;
        }

        try {
            oldSpace = view.getSpace()[p.getOldX()][p.getOldY()];
        } catch (NullPointerException e) {
            return false;
        }

        //check whether in range
        try {
            newSpace = view.getSpace()[p.getNewX()][p.getNewY()];
        } catch (NullPointerException e) {
            return false;
        }

        if (!oldSpace.isOccupied()) {
            return false;
        }


        piece = oldSpace.getPiece();
        moves = piece.getPieceMoves();

        boolean matchesPieceMoves = false;

        //for Pieces that move more than 1 base move (Bishop, Rook, Queen)
        int multiMoveCount;
        int stretchedMoveX;
        int stretchedMoveY;


        for (MoveList m : moves) {//iterates through multiple times if has multiple possible moves
            multiMoveCount = 1;
            if (piece.usesSingleMove() == false) {
                multiMoveCount = 8;
            }

            boolean hasCollided = false;

            for (int c = 1; c <= multiMoveCount; c++) {
                //if the prior run hit a piece of opponent's color, done with this move
                if (hasCollided) {
                    break;
                }

                //stretches a base move out to see if it matches the move made
                stretchedMoveX = m.getX() * c;
                stretchedMoveY = m.getY() * c;

                Space tempSpace;

                //If OOB, go to next move of the piece -- ensures space exists later
                try {
                    tempSpace = view.getSpace()[p.getOldX() + stretchedMoveX]
                            [p.getOldY() + stretchedMoveY];
                } catch (Exception e) {
                    break;
                }

                //handles piece collision and capturing
                if (tempSpace.isOccupied()) {
                    hasCollided = true;
                    boolean piecesSameColor = tempSpace.getPiece().getColor() == oldSpace.getPiece().getColor();
                    //stops checking this move if pieces are the same color
                    if (piecesSameColor) {
                        break;
                    }
                }

                //if stretched move matches made move
                if (p.getGapX() == stretchedMoveX && p.getGapY() == stretchedMoveY) {
                    matchesPieceMoves = true;

                    if (pawnValidityCheck(p) == false) {
                        return false;
                    }

                    piece.setHasMoved(true);
                    break;
                }
            }
        }
        if (!matchesPieceMoves) {
            return false;
        }
        return true;

    }

    protected boolean pawnValidityCheck(Move p) {
        //this should only be called in moveIsValid, so checks are done there
        Space oldSpace = view.getSpace()[p.getOldX()][p.getOldY()];
        Space newSpace = view.getSpace()[p.getNewX()][p.getNewY()];
        Piece piece = oldSpace.getPiece();

        //If it's not a pawn, it passes
        if (!piece.getName().equals("pawn")) {
            return true;
        }

        //if this is a "straight" move
        if (p.getGapX() == 0) {
            //black is negative 1, white is positive 1, for direction later
            int colorMod = p.getGapY() / Math.abs(p.getGapY());

            //if there's a piece in the way for a straight move, don't allow move
            for (int c = 1; c <= Math.abs(p.getGapY()); c++) {
                if (view.getSpace()[p.getOldX()][p.getOldY() + (c * colorMod)].isOccupied()) {
                    return false;
                }
            }
        } else //if it's a diagonal move
        {
            //if the target square doesn't have an opposing piece, don't allow move
            if ((!newSpace.isOccupied()) ||
                    piece.getColor() == newSpace.getPiece().getColor()) {
                return false;
            }
        }

        return true;
    }


}





      
