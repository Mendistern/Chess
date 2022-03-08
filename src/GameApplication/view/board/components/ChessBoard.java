package GameApplication.view.board.components;

import GameApplication.model.Move;
import GameApplication.model.chess.Board;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import GameApplication.model.chess.piece.Piece;

public class ChessBoard extends GridPane {
    public Space[][] spaces = new Space[8][8];
    public Space activeSpace = null;
    public PieceComp[][] pieceComponentsOfBoard;
    private Piece[][] piecesFromBoard;

    public ChessBoard(boolean playerIsWhite) {
        super();
        pieceComponentsOfBoard = new PieceComp[8][8];
        piecesFromBoard = new Piece[8][8];

        /*
        for (int x = 0; x < spaces[0].length; x++) {
            for (int y = 0; y < spaces[1].length; y++) {
                boolean light = ((x + y) % 2 != 0);
                spaces[x][y] = new Space(light, x, y);

                if (playerIsWhite) {
                    this.add(spaces[x][y], x, 7 - y);
                } else {
                    this.add(spaces[x][y], 7 - x, y);
                }
                final int xVal = x;
                final int yVal = y;
                spaces[x][y].setOnAction(e -> onSpaceClick(xVal, yVal));


            }
        }

         */

        //kolom
        for (int y = 7; y >=0; y--) {
            //rij
            for (int x = 0; x <8; x++) {
                boolean light = ((x + y) % 2 != 0);
                spaces[x][y] = new Space(light, x, y);

                if (playerIsWhite) {
                    System.out.println(x + " " +y);
                    this.add(spaces[x][y], x, y);

                } else {
                    this.add(spaces[x][y], x, y);

                }
                final int xVal = x;
                final int yVal = y;
                spaces[x][y].setOnAction(e -> onSpaceClick(xVal, yVal));


            }
        }
       // this.defineStartPositions();
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

    public void onSpaceClickV2(Board board, int clickedColumn, int clickedRow){
        board.getMoveManager().addMove(clickedColumn, clickedRow);

        System.out.println(board.getMoveManager().getMoves());
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
            setActiveSpace(null);
        } else {
            //if there's a piece on the selected square when no active square
            if (spaces[x][y].getPiece() != null) {
                //make active square clicked square
                setActiveSpace(spaces[x][y]);
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
        PieceComp piece;

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
        PieceComp piece = oldSpace.getPiece();

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




    public void defineStartPositions(Piece[][] piecesFromBoard) {
      //  System.out.println("Pieces from model: "+piecesFromBoard[0][0].getPieceLocation());
        this.piecesFromBoard = piecesFromBoard;


        /*
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                this.spaces[j][i].setPiece(null);
                if (this.piecesFromBoard[j][i]!=null) {
                    pieceComponentsOfBoard[j][i] = PieceComp.fromPieceToPieceComp(this.piecesFromBoard[j][i]);
                    this.spaces[j][i].setPiece(pieceComponentsOfBoard[j][i]);
                    //System.out.println(i + " " + j);
                }
            }
        }

         */

        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                this.spaces[7-j][i].setPiece(null);
                if (this.piecesFromBoard[7-j][i]!=null) {
                    pieceComponentsOfBoard[7-j][i] = PieceComp.fromPieceToPieceComp(this.piecesFromBoard[7-j][i]);
                    this.spaces[7-j][i].setPiece(pieceComponentsOfBoard[7-j][i]);
                    //.out.println((i )+ " " + (7-j)+" "+pieceComponentsOfBoard[7-j][i].getName()+" "+pieceComponentsOfBoard[7-j][i].getColor());
                    //System.out.println(i + " " + j);
                }


            }
        }


        /*
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


         */
    }

}

