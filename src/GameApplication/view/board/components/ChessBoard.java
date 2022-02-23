package GameApplication.view.board.components;

import GameApplication.model.Move;
import GameApplication.model.Space;
import GameApplication.view.board.components.*;
import javafx.scene.layout.GridPane;

public class ChessBoard extends GridPane {
    public Space[][] spaces = new Space[8][8];
    public Space activeSpace = null;

    public ChessBoard(boolean playerIsWhite) {
        super();

//        for (int x = 0; x < spaces[0].length; x++) {
//            for (int y = 0; y < spaces[1].length; y++) {
//                boolean light = ((x + y) % 2 != 0);
//                spaces[x][y] = new Space(light, x, y);
//
//                if (playerIsWhite) {
//                    this.add(spaces[x][y], x, 7 - y);
//                } else {
//                    this.add(spaces[x][y], 7 - x, y);
//                }
//                final int xVal = x;
//                final int yVal = y;
//                spaces[x][y].setOnAction(e -> onSpaceClick(xVal, yVal));
//
//
//            }
//        }
        defineStartPositions();
    }

    public void setActiveSpace(Space s) {
        //removes syle from old active location
        if (this.activeSpace != null)
            this.activeSpace.getStyleClass().removeAll("chess-space-active");

        this.activeSpace = s;

        //add style to new active location
        if (this.activeSpace != null)
            this.activeSpace.getStyleClass().add("chess-space-active");
    }

    public void onSpaceClick(int x, int y) {
        Space clickedSpace = spaces[x][y];
        // if piece is selected && user didn't click on allied piece
        if (activeSpace != null &&
                activeSpace.getPiece() != null &&
                clickedSpace.getPieceColor() != activeSpace.getPieceColor()) {
            Move p;
            p = new Move(activeSpace.getX(), activeSpace.getY(), x, y);


            // update gameboard
            if (this.processMove(p)) {
                // send move to other player

            }

            //decouples space from space on board
            this.setActiveSpace(null);
        } else {
            //if there's a piece on the selected square when no active square
            if (spaces[x][y].getPiece() != null) {
                //make active square clicked square
                this.setActiveSpace(spaces[x][y]);
            }
        }
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

    public Space getSpace(int x, int y) {
        return spaces[x][y];
    }

    public Space getActiveSpace() {
        return this.activeSpace;
    }


    private void defineStartPositions() {
        // white pieces
        this.spaces[0][0].setPiece(new Rook(true));
        this.spaces[1][0].setPiece(new Knight(true));
        this.spaces[2][0].setPiece(new Bishop(true));
        this.spaces[3][0].setPiece(new Queen(true));
        this.spaces[4][0].setPiece(new King(true));
        this.spaces[5][0].setPiece(new Bishop(true));
        this.spaces[6][0].setPiece(new Knight(true));
        this.spaces[7][0].setPiece(new Rook(true));

        for (int i = 0; i < this.spaces[0].length; i++)
            this.spaces[i][1].setPiece(new Pawn(true));

        // black pieces
        this.spaces[0][7].setPiece(new Rook(false));
        this.spaces[1][7].setPiece(new Knight(false));
        this.spaces[2][7].setPiece(new Bishop(false));
        this.spaces[3][7].setPiece(new Queen(false));
        this.spaces[4][7].setPiece(new King(false));
        this.spaces[5][7].setPiece(new Bishop(false));
        this.spaces[6][7].setPiece(new Knight(false));
        this.spaces[7][7].setPiece(new Rook(false));

        for (int i = 0; i < this.spaces[0].length; i++)
            this.spaces[i][6].setPiece(new Pawn(false));
    }
}

