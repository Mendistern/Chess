package GameApplication.view.board.components;

import GameApplication.view.board.Move;
import javafx.scene.layout.GridPane;

/**
 * Presenter class for BoardView to handle Events
 *
 * @author Nicolas Bouquiaux
 * @version 1.0
 */


//lol don't even use this class anymore, but why bother refactoring code? so based
public class ChessBoard extends GridPane {
    public Space[][] spaces;
    public Space activeSpace = null;

    public ChessBoard(boolean playerIsWhite) {
        super();
        spaces = new Space[8][8];
        setActiveSpace(activeSpace);
    }

    public void setActiveSpace(Space s) {
        if (this.activeSpace != null)
            this.activeSpace.getStyleClass().removeAll("chess-space-active");

        this.activeSpace = s;

        //add style to new active location
        if (this.activeSpace != null)
            this.activeSpace.getStyleClass().add("chess-space-active");
    }

    protected boolean processMove(Move p) {
        if (moveIsValid(p)) {
            Space oldSpace = spaces[p.getOldX()][p.getOldY()];
            Space newSpace = spaces[p.getNewX()][p.getNewY()];

            newSpace.setPiece(oldSpace.releasePiece());
            return true;
        } else {
            return false;
        }
    }

    public boolean moveIsValid(Move p) {
        Space oldSpace;
        Space newSpace;
        Piece piece;

        if (p == null) {
            return false;
        }

        try {
            oldSpace = spaces[p.getOldX()][p.getOldY()];
        } catch (NullPointerException e) {
            return false;
        }

        try {
            newSpace = spaces[p.getNewX()][p.getNewY()];
        } catch (NullPointerException e) {
            return false;
        }

        piece = oldSpace.getPiece();


        return true;

    }

    protected boolean pawnValidityCheck(Move p) {
        //this should only be called in moveIsValid, so checks are done there
        Space oldSpace = spaces[p.getOldX()][p.getOldY()];
        Space newSpace = spaces[p.getNewX()][p.getNewY()];
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
                if (spaces[p.getOldX()][p.getOldY() + (c * colorMod)].isOccupied()) {
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

    public Space[][] getSpaces() {
        return spaces;
    }

    public Space getActiveSpace() {
        return this.activeSpace;
    }
}

