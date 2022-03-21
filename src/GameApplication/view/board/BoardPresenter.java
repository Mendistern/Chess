package GameApplication.view.board;

import GameApplication.model.Chess;
import GameApplication.model.FileWrite;
import GameApplication.model.chess.Board;
import GameApplication.model.chess.FileManager;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
import GameApplication.model.chess.spot.Spot;
import GameApplication.view.board.components.ChessBoard;
import GameApplication.view.board.components.Space;
import GameApplication.view.instructions.InstructionsView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;


public class BoardPresenter {
    private Chess model;
    private BoardView view;
    private Piece[][] piecesFromModel;
    int size = 8;
    private ChessBoard board;
    private final transient Board moveEvaluator;
    private GridPane options;
    private FileWrite fileWrite;
    private final transient Logger log = Logger.getLogger("game");
    private Board gameBoard;
    private Scanner scanner;


    public BoardPresenter(Chess model, BoardView view) {
        this.model = model;
        this.view = view;
        options = new GridPane();
        fileWrite = new FileWrite();
        FileManager fileManager;
        this.moveEvaluator = model.getBoard();
        addEventListeners();
        updateView();
    }

    public static char convCol(int col) {
        switch (col) {
            case 1:
                return 'A';
            case 2:
                return 'B';
            case 3:
                return 'C';
            case 4:
                return 'D';
            case 5:
                return 'E';
            case 6:
                return 'F';
            case 7:
                return 'G';
            case 8:
                return 'H';
            default:
                return ' ';
        }
    }

    private void addEventListeners() {
        board = view.getBoard();


        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                final int finalX = x;
                final int finalY = y;
//                spot = new Spot(x, y);

                view.getBoard().getSpaces()[finalX][finalY].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        StringBuilder str = new StringBuilder();
                        handleValidMoves(model.getBoard().getPieceFromSpot(finalX, finalY));

                        view.getBoard().setActiveSpace(view.getBoard().spaces[finalX][finalY]);
                        view.getBoard().onSpaceClickV2(model.getBoard(), finalX, finalY);
//                      Spot pos = model.getBoard().getPieceFromSpot(finalX,finalY).getPieceLocation();
                        Spot pieceLocation = model.getBoard().getPieceFromSpot(finalX, finalY).getPieceLocation();


                        str.append("turn ").append(model.getBoard().getPieceFromSpot(finalX, finalY).getPieceLocation().toString());
                        view.getGameFlow().appendText(str.toString());


                        updateView();

                    }

                });
                view.getChessMenu().getMiInstructions().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        InstructionsView instructionsView = new InstructionsView();
                        Stage newStage = new Stage();
                        newStage.initOwner(view.getScene().getWindow());
                        newStage.initModality(Modality.APPLICATION_MODAL);
                        Scene scene = new Scene(instructionsView);
                        newStage.setScene(scene);
                        newStage.showAndWait();
                    }
                });
                view.getChessMenu().getMiSave().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Save Data File");
                        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Textfiles", "*.txt"), new FileChooser.ExtensionFilter("All Files", "*.*"));
                        File selectedFile = fileChooser.showSaveDialog(view.getScene().getWindow());
                        if ((selectedFile != null) ^ (Files.isWritable(Paths.get(selectedFile.toURI())))) {
                            try (Formatter output = new Formatter(selectedFile)) {
//                                PieceComp piece = PieceComp.fromPieceToPieceComp(model.getBoard().getPieceFromSpot(view.getActiveSpace().getX(),view.getActiveSpace().getY()));

                                output.format("%s%s", model.getBoard().getPieceFromSpot(finalX, finalX).toString(), view.getGameFlow().getText());
                                ObjectOutputStream objWriter = new ObjectOutputStream(new FileOutputStream(selectedFile));

                                Spot pieceLocation = model.getBoard().getPieceFromSpot(finalX, finalY).getPieceLocation();
                                objWriter.writeInt(model.getBoard().getArrayIndexForColor(pieceLocation.getPiece().getPieceColor()));
                                objWriter.writeObject(board.getIO());
                                String ext = fileChooser.getSelectedExtensionFilter().getExtensions().toString();
                                FileWrite fileWrite = new FileWrite();
                                fileWrite.saveToFile(Paths.get(selectedFile.toURI()), String.valueOf(output.format("%n", pieceLocation.hashCode())));


                                Alert succesAlert = new Alert(Alert.AlertType.CONFIRMATION);
                                succesAlert.setHeaderText("File succesfully written to " + Paths.get(selectedFile.toURI()).toString());
                                succesAlert.show();
                            } catch (IOException e) {
                                Alert errorWindow = new Alert(Alert.AlertType.ERROR);
                                errorWindow.setHeaderText("Problem with selected file");
                                errorWindow.setContentText("File is not writable");
                                errorWindow.showAndWait();
                            }
                        }
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


    //    public void writeToConsole(Piece piece){
//        List<Space> clickedSpace = view.getClickedSpace();
//
//
//
//        for (Space space : clickedSpace){
//            space.setUserData( new Spot( view.getBoard().getSpaces().length - 7, convCol(space.getX() + 1)));
//            StringBuilder str = new StringBuilder();
//            str.append( "Position XY : "  ).append(space.getUserData().toString()).append("\n");
//            int x = (int) e.getX();
//            int y = (int) e.getY();
//
//            int rx = ((int) e.getX() % 8);
//            int ry = ((int) e.getY() % 8);
//
//            int lin = (x - rx) / 8;
//            int col = (y - ry) / 8;
//
//            str.append("Line X: ").append(lin).append("\n");
//            str.append("Col Y: ").append(convCol(col)).append("\n");
//            str.append("Position XY : ").append(new Position(lin, convCol(col))).append("\n");
//            str.append("\n-----------------------------------------------------------------------------\n\n");
//            view.getGameFlow().appendText( str.toString() );
//
//        }
//
//
//    }
    public void handleValidMoves(Piece piece) {
        List<Space> validSpaces = view.getValidMovesSpaces();
        for (Space space : validSpaces) {
            view.getBoard().getSpaces()[space.getX()][space.getY()].getStyleClass().remove("chess-space-valid");
        }
        List<Space> validAttackSpaces = view.getValidAttackSpaces();
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
                    view.setValidMovesSpaces(new Space(true, spot.getColumn(), spot.getRow()));
                }
            }
        }

        Piece validAttackSpots = model.getBoard().getPieceFromSpot(piece.getColumn(), piece.getRow());
        for (Spot[] validSpot : validAttackSpots.getValidAttackSpots()) {
            for (Spot spot : validSpot) {
                if (spot != null) {
                    view.setValidAttackSpaces(new Space(true, spot.getColumn(), spot.getRow()));
                }
            }
        }

    }


    public Piece[][] getPiecesFromModel() {
        return piecesFromModel;
    }

    public void updateView() {
        piecesFromModel = model.getPiecesOnBoard();
        view.getBoard().defineStartPositions(piecesFromModel);


        List<Space> validSpaces = view.getValidMovesSpaces();
        for (Space space : validSpaces) {
            view.getBoard().getSpaces()[space.getX()][space.getY()].getStyleClass().add("chess-space-valid");
        }
        List<Space> validAttackSpaces = view.getValidAttackSpaces();
        for (Space space : validAttackSpaces) {
            view.getBoard().getSpaces()[space.getX()][space.getY()].getStyleClass().add("chess-space-attackable");
        }

    }

    public void setPiecesFromModel(Piece[][] piecesFromModel) {
        this.piecesFromModel = piecesFromModel;
    }
}


