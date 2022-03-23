package GameApplication.view.board.components;

import GameApplication.model.ChessIO;
import GameApplication.model.chess.Board;
import GameApplication.model.chess.FileManager;
import GameApplication.model.chess.piece.Piece;
import GameApplication.view.board.Move;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Translate;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


public class ChessBoard extends GridPane {
    private static ChessBoard instance;
    public Space[][] spaces = new Space[8][8];
    public Space activeSpace = null;
    public PieceComp[][] pieceComponentsOfBoard;
    private Piece[][] piecesFromBoard;
    public int size;
    private Translate pos;
    private Map<String, ChessIO> io = new LinkedHashMap<>();

    private GridPane root;

    private StackPane square;

    public ChessBoard(boolean playerIsWhite) {
        instance = this;

//        final int xVal;
//        final int yVal;
        size = 8;

        root = new GridPane();
        pieceComponentsOfBoard = new PieceComp[8][8];
        piecesFromBoard = new Piece[8][8];



        //kolom
        int colNum = 1; // vereist want loop start vanaf 0

        //houdt de nummers gescheiden voor problemen te voorkomen

        // stackpane zorgt ervoor dat we deze op de huidige pane kunnen plaatsen, anders krijg je duplication errors
        for (int y = 7; y >= 0; y--) {
            //rij
            for (int x = 0; x < 8; x++) {

                boolean light = ((x + y) % 2 != 0);
                spaces[x][y] = new Space(light, x, y);

                if (!playerIsWhite) {
                    System.out.println(x + " " + y);
                    super.add(spaces[x][y], x, 7 - y);
                } else {
                    super.add(spaces[x][y], 7 - x, y);
                }


                final int xVal = x;
                final int yVal = y;
                spaces[x][y].setOnAction(e -> onSpaceClick(xVal, yVal));


                ///this.add(newColLabel(x), 0, x + 1, 1, 1);
                //this.add(newColLabel(7-x), 0, 9,1,1);
            }


//            Label label = newRowLabel(colNum);
//            root.setAlignment(Pos.BOTTOM_LEFT);
//            root.getChildren().add(label);
//
//            this.add(newRowLabel(y), 9,9 - y, 1, 1);

        }
        for (int i = 1; i <= size; i++) {
            StackPane square = new StackPane();
            char[] letter = new char[]{(char) ('A' + (i - 1))};
            Label label = new Label(new String(letter));
            square.getChildren().add(label);
            this.add(square, i - 1, size);
        }
        for (int i = 1; i <= size; i++) {
            StackPane square = new StackPane();
            int rows = (i + 1);
            Label label = new Label(new String(String.valueOf(rows - 1)));
            square.getChildren().add(label);
            this.add(square, size, i - 1);
        }
//        for (int i = 0; i <= size; i++) {
//            this.getColumnConstraints().add(new ColumnConstraints(getMinWidth(), Space.USE_COMPUTED_SIZE, Space.USE_PREF_SIZE, Priority.ALWAYS, HPos.CENTER, true));
//            this.getRowConstraints().add(new RowConstraints(getMinHeight(), Space.USE_COMPUTED_SIZE, Space.USE_PREF_SIZE, Priority.ALWAYS, VPos.CENTER, true));
//
//
//        }

        super.setAlignment(Pos.CENTER);
        this.isResizable();
        GridPane.setFillHeight(root, super.isResizable());


    }
    public Map<String, ChessIO> getIo() {
        return io;
    }
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

    public void setActiveSpace(Space s) {
        //removes syle from old active location
        if (this.activeSpace != null)
            this.activeSpace.getStyleClass().removeAll("chess-space-active");

        this.activeSpace = s;

        //add style to new active location
        if (this.activeSpace != null)
            this.activeSpace.getStyleClass().add("chess-space-active");
    }

    public void onSpaceClickV2(Board board, int clickedColumn, int clickedRow) {

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

    public boolean moveIsValid(Move p) {
        Space oldSpace;
        Space newSpace;
        PieceComp piece;

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

    private Label newRowLabel(int i) {
        Label l = new Label(8 - i + "");

        l.setAlignment(Pos.CENTER);
        return l;
    }

    private Label newColLabel(int i) {
        Label l = new Label((char) (i + 65) + "");

        l.isHover();
        l.setAlignment(Pos.CENTER);
        return l;
    }

    public Map<String, ChessIO> getIO() {
        return io;
    }


//    public void load(File file) {
//        load(getFileExtension(file.getName()), FileManager.loadDataFromFile(file));
//    }

    public void setIO(ChessIO io) {
        this.io.put(io.getFileExtension(), io);
    }

    private String getFileExtension(String name) {
        return name.substring(name.lastIndexOf('.') + 1);
    }

    public void save(File file) throws IOException {
        byte[] s = io.get(getFileExtension(file.getName())).save(this);
        FileManager.saveDataToFile(s, file);
    }


//    public void loadFromResource(String resource) {
//        load(getFileExtension(resource), FileManager.loadDataFromResource(resource));
//    }


}


