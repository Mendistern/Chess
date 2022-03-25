package gameapplication.view.board.components;

import gameapplication.model.chess.Board;
import gameapplication.model.chess.piece.Piece;
import gameapplication.view.board.Move;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;


/**
 * The ChessBoard class is a GridPane that holds all the spaces on the board. It also holds the pieces on the board
 */
public class ChessBoard extends GridPane {
    private static ChessBoard instance;
    public Space[][] spaces = new Space[8][8];
    public Space activeSpace = null;
    public PieceComp[][] pieceComponentsOfBoard;
    private Piece[][] piecesFromBoard;
    public int size;

    private GridPane root;

    public ChessBoard(boolean playerIsWhite) {
        instance = this;
        size = 8;

        root = new GridPane();
        pieceComponentsOfBoard = new PieceComp[8][8];
        piecesFromBoard = new Piece[8][8];

        //kolom
        int colNum = 1; // vereist want loop start vanaf 0

        //houdt de nummers gescheiden voor problemen te voorkomen

        // stackpane zorgt ervoor dat we deze op de huidige pane kunnen plaatsen, anders krijg je duplication errors
        // This is creating the labels for the column numbers.
        // Looping through the array.
        for (int y = 7; y >= 0; y--) {
            //rij

            // This is creating the labels for the column numbers.
            StackPane square = new StackPane();
            int rows = ( y + 1);
            Label label = new Label(new String(String.valueOf((7-y)+1)));
            square.getChildren().add(label);
            this.add(square, size,y) ;

            for (int x = 0; x < 8; x++) {

                // This is checking if the current space is light or dark.
                boolean light = ((x + y) % 2 != 0);
                // Creating a new space object and setting it's light and x and y values.
                spaces[x][y] = new Space(light, x, y);


                // This is checking if the player is white or black. If the player is white, then the board is flipped.
                if (!playerIsWhite) {
                    System.out.println(x + " " + y);
                    super.add(spaces[x][y], x, 7 - y);
                } else {
                    super.add(spaces[x][y], 7 - x, y);
                }


                final int xVal = x;
                final int yVal = y;
                // This is registering the event handler for the space.
                spaces[x][y].setOnAction(e -> onSpaceClick(xVal, yVal));

            }
        }
        // This is creating the labels for the column numbers.
        for (int i = 1; i <= size; i++) {
            StackPane square = new StackPane();
            char[] letter = new char[]{(char) ('A' + (i - 1))};
            Label label = new Label(new String(letter));
            square.getChildren().add(label);
            this.add(square, i - 1, size);
        }

        // This is setting the alignment of the GridPane to center.
        super.setAlignment(Pos.CENTER);
        this.isResizable();
        GridPane.setFillHeight(root, super.isResizable());


    }

    // This is returning the pieceComponentsOfBoard and piecesFromBoard.
    public PieceComp[][] getPieceComponentsOfBoard() {
        return pieceComponentsOfBoard;
    }
    public Piece[][] getPiecesFromBoard() {
        return piecesFromBoard;
    }
    public double getCell_width() {
        return cell_width;
    }
    public double getCell_height() {
        return cell_height;
    }

    private double cell_width;
    private double cell_height;

    /**
     * This function sets the active space to the space passed in
     *
     * @param s The Space object that was clicked.
     */
    public void setActiveSpace(Space s) {
        //removes syle from old active location
        if (this.activeSpace != null)
            this.activeSpace.getStyleClass().removeAll("chess-space-active");

        this.activeSpace = s;

        //add style to new active location
        if (this.activeSpace != null)
            this.activeSpace.getStyleClass().add("chess-space-active");
    }

    /**
     * The function is called when the user clicks on a column.
     *
     * The function adds the move to the move manager.
     *
     * The function prints the move manager's moves.
     *
     * @param board The board object that is being used in the game.
     * @param clickedColumn The column that was clicked.
     * @param clickedRow The row that was clicked.
     */
    public void onSpaceClickV2(Board board, int clickedColumn, int clickedRow) {

        // This is adding the move to the move manager.
        board.getMoveManager().addMove(clickedColumn, clickedRow);
        // Printing the moves that are in the move manager.
        System.out.println(board.getMoveManager().getMoves());
    }

    /**
     * If the user clicks on a space that has a piece on it, and the active space is null, then set the active space to the
     * space that was clicked on
     *
     * @param x the x coordinate of the space that was clicked
     * @param y the y coordinate of the space that was clicked
     */
    public void onSpaceClick(int x, int y) {

        Space clickedSpace = spaces[x][y];
        // if piece is selected && user didn't click on allied piece
        if (activeSpace != null &&
                activeSpace.getPiece() != null &&
                clickedSpace.getPieceColor() != activeSpace.getPieceColor()) {
            Move p;
            p = new Move(activeSpace.getX(), activeSpace.getY(), x, y);
            // update gameboard
            this.processMove(p);

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

    /**
     * If the move is valid, then register the old and new coordinates and reposition the piece to the new coordinates
     *
     * @param p the move to be processed
     * @return A boolean value.
     */
    protected boolean processMove(Move p) {
        //when a move is valid, register current (old) coordinates and new coordinates & reposition piece to new coordinates
        if (moveIsValid(p)) {
            Space oldSpace = spaces[p.getOldX()][p.getOldY()];
            Space newSpace = spaces[p.getNewX()][p.getNewY()];

            newSpace.setPiece(oldSpace.releasePiece());
            return true;
        } else {
            return false;
        }
    }

    /**
     * This function checks if the move is valid
     *
     * @param p The move that the player is attempting to make.
     * @return A boolean value.
     */
    public boolean moveIsValid(Move p) {
        Space oldSpace;
        Space newSpace;
        PieceComp piece;

        // This is checking if the move is null. If it is, then it returns false.
        if (p == null) {
            return false;
        }

        // check if previous spot is present or else throw exception
        try {
            oldSpace = spaces[p.getOldX()][p.getOldY()];
        } catch (NullPointerException e) {
            return false;
        }


        // Checks if new spot is a valid choice, else throw exception
        try {
            newSpace = spaces[p.getNewX()][p.getNewY()];
        } catch (NullPointerException e) {
            return false;
        }

        piece = oldSpace.getPiece();


        return true;

    }

    /**
     * If the piece is a pawn, and the move is a straight move, and there's no piece in the way, then the move is valid
     *
     * @param p the move being checked
     * @return A boolean value.
     */
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


    /**
     * This function takes in a 2D array of pieces from the board and sets the pieces on the board to the pieces from the
     * board
     *
     * @param piecesFromBoard The pieces that are currently on the board.
     */
    public void defineStartPositions(Piece[][] piecesFromBoard) {
        this.piecesFromBoard = piecesFromBoard;

        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                this.spaces[7 - j][i].setPiece(null);
                if (this.piecesFromBoard[7 - j][i] != null) {
                    pieceComponentsOfBoard[7 - j][i] = PieceComp.fromPieceToPieceComp(this.piecesFromBoard[7 - j][i]);
                    this.spaces[7 - j][i].setPiece(pieceComponentsOfBoard[7 - j][i]);
                }


            }
        }
    }

    /**
     * Create a new label with the given text
     *
     * @param i The row number of the label.
     * @return A Label
     */
    private Label newRowLabel(int i) {
        Label l = new Label(8 - i + "");

        l.setAlignment(Pos.CENTER);
        return l;
    }

    /**
     * Create a new label with the given index and set it's alignment to center
     *
     * @param i the index of the column
     * @return A label that is centered and has the letter of the column.
     */
    private Label newColLabel(int i) {
        // This is creating a label with the letter of the column.
        Label l = new Label((char) (i + 65) + "");

        l.isHover();
        l.setAlignment(Pos.CENTER);
        return l;
    }



    /**
     * Given a file name, return the extension of the file
     *
     * @param name The name of the file.
     * @return The substring of the name after the last period.
     */
    private String getFileExtension(String name) {
        return name.substring(name.lastIndexOf('.') + 1);
    }




}


