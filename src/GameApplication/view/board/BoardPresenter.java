package GameApplication.view.board;

import GameApplication.model.Chess;
import GameApplication.model.FileWrite;
import GameApplication.model.Position;
import GameApplication.model.chess.Board;
import GameApplication.model.chess.Player;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
import GameApplication.model.chess.piece.pieces.King;
import GameApplication.model.chess.spot.Spot;
import GameApplication.view.board.components.ChessBoard;
import GameApplication.view.board.components.Space;
import GameApplication.view.instructions.InstructionsView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
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


public class BoardPresenter {
    private final Chess model;
    private final BoardView view;
    private Piece[][] piecesFromModel;
    int size = 8;
    private ChessBoard board;
    //private final transient Board moveEvaluator;
    //private GridPane options;
    //  private FileWrite fileWrite;


    public Board getGameBoard() {
        return gameBoard;
    }

    private Board gameBoard;

    public void restartGame() {

    }

    public BoardPresenter(Chess model, BoardView view) {
        this.model = model;
        this.view = view;
        //options = new GridPane();
        //fileWrite = new FileWrite();
        // FileManager fileManager;
        //this.moveEvaluator = model.getBoard();
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
                System.out.println("final " + finalY + " " + finalY);


                view.getBoard().getSpaces()[finalX][finalY].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {



                        handleValidMoves(model.getBoard().getPieceFromSpot(finalX, finalY));

                        view.getBoard().setActiveSpace(view.getBoard().spaces[finalX][finalY]);
                        /*if (model.getBoard().getCheckedState()){
                            System.out.println("check state");
                            return;
                        }*/

                        handleValidMoves(model.getBoard().getPieceFromSpot(finalX, finalY));

                        view.getBoard().onSpaceClickV2(model.getBoard(), finalX, finalY);

                        view.getBoard().onSpaceClickV2(model.getBoard(), finalX, finalY);
                        //Spot pieceLocation = model.getBoard().getPieceFromSpot(finalX, finalY).getPieceLocation().getPiece().getPieceLocation();
                        view.getBoard().getSpaces()[finalX][finalY].setOnMouseClicked((MouseEvent e) -> {
                            StringBuilder str = new StringBuilder();

                            str.append("Position Gridpane X: ").append(e.getX()).append("\n");
                            str.append("Position Gridpane Y: ").append(e.getY()).append("\n");

                            int x = (int) e.getX();
                            int y = (int) e.getY();

                            int rx = ((int) e.getX() % 8);
                            int ry = ((int) e.getY() % 8);

                            int lin = (x - rx) / 8;
                            int col = (y - ry) / 8;

                            str.append("Line X: ").append(lin).append("\n");
                            str.append("Col Y: ").append(convCol(col)).append("\n");
                            str.append("Position XY : ").append(new Position(lin, convCol(col))).append("\n");
                            str.append("\n-----------------------------------------------------------------------------\n\n");

                            view.getGameFlow().appendText(str.toString());
                        });
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
                    public void handle(ActionEvent actionEvent) {
                        FileChooser fileChooser = new FileChooser();

                        fileChooser.setTitle("Save to file");
                        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Textfiles", "*.txt"));
                        File selectedFile = fileChooser.showSaveDialog(view.getScene().getWindow());

                        if ((selectedFile != null) ^ (Files.isWritable(Paths.get(selectedFile.toURI())))) {
                            try {
                                model.getFileManager().saveToFile(selectedFile.getPath());
                            } catch (IOException e) {
                                Alert errorWindow = new Alert(Alert.AlertType.ERROR);
                                errorWindow.setHeaderText("Problem with selected file");
                                errorWindow.setContentText("File is not writable: " + e.getMessage());
                                errorWindow.showAndWait();

                            }
                        } else {
                            Alert errorWindow = new Alert(Alert.AlertType.ERROR);
                            errorWindow.setHeaderText("Problem with selected file");
                            errorWindow.setContentText("File is not writable");
                            errorWindow.showAndWait();

                        }
                    }
                });

                view.getChessMenu().getMiLoad().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Load Data File");
                                fileChooser.getExtensionFilters().addAll(
                                        new FileChooser.ExtensionFilter("Textfiles", "*.txt"));
                        File selectedFile = fileChooser.showOpenDialog(
                                view.getScene().getWindow());
                        if ((selectedFile != null) &&
                                (Files.isReadable(Paths.get(selectedFile.toURI())))) {
                            try {

                                System.out.println(selectedFile);

                                model.getFileManager().loadFile(String.valueOf(selectedFile.getPath()));
                                updateView();
                            } catch (IOException e) {
                                Alert errorWindow = new Alert(Alert.AlertType.ERROR);
                                errorWindow.setHeaderText("Problem with selected file");
                                errorWindow.setContentText("File is not readable: " + e.getMessage());
                                errorWindow.showAndWait();

                            }
                        } else{
                            Alert errorWindow = new Alert(Alert.AlertType.ERROR);
                            errorWindow.setHeaderText("Problem with selected file");
                            errorWindow.setContentText("File is not readable.");
                            errorWindow.showAndWait();
                        }

                    }
                });


                view.getChessMenu().getMiRestart().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        model.restartGame();
                        updateView();
                    }
                });

                view.getRestartGameButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        model.restartGame();
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
        //On restart, remove all space classes from board.
        if (model.isRestarted()) {
            for (Space[] spaceX : view.getBoard().getSpaces()) {
                for (Space space : spaceX) {
                    space.getStyleClass().remove("chess-space-attackable");
                    space.getStyleClass().remove("chess-space-valid");
                }
            }
            //set restarted to false
            model.setRestarted(false);

            view.getHBoxGameOver().setVisible(false);
        }

        if (model.getBoard().isGameOver()){
            view.getHBoxGameOver().setVisible(true);
        }

        piecesFromModel = model.getPiecesOnBoard();
        view.getBoard().defineStartPositions(piecesFromModel);

        //Add names to labels
        for (Player player:model.getBoard().getPlayers()){
            if (player.getColor()==PieceColor.WHITE){
                view.setPlayerName1(player.getName());
            }else{
                view.setPlayerName2(player.getName());
            }
        }



        King king = model.getBoard().getKing(model.getBoard().getCheckedColor());

        //remove attack color from kings position
        view.getBoard().getSpaces()[king.getColumn()][king.getRow()].getStyleClass().remove("chess-space-attackable");

        //if king moved, after check, remove attack color from his last position
        view.getBoard().getSpaces()[king.getLastKingPosition().get(0).getColumn()][king.getLastKingPosition().get(0).getRow()].getStyleClass().remove("chess-space-attackable");


        List<Space> validSpaces = view.getValidMovesSpaces();
        for (Space space : validSpaces) {
            view.getBoard().getSpaces()[space.getX()][space.getY()].getStyleClass().add("chess-space-valid");
        }
        List<Space> validAttackSpaces = view.getValidAttackSpaces();
        for (Space space : validAttackSpaces) {
            view.getBoard().getSpaces()[space.getX()][space.getY()].getStyleClass().add("chess-space-attackable");
        }


        if (model.getBoard().getCheckedState()) {
            view.getBoard().getSpaces()[king.getColumn()][king.getRow()].getStyleClass().add("chess-space-attackable");
        }


    }

    public void setPiecesFromModel(Piece[][] piecesFromModel) {
        this.piecesFromModel = piecesFromModel;
    }
}


