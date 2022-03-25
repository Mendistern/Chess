package gameapplication.view.board;

import gameapplication.model.Chess;
import gameapplication.model.chess.Board;
import gameapplication.model.chess.Player;
import gameapplication.model.chess.piece.Piece;
import gameapplication.model.chess.piece.PieceColor;
import gameapplication.model.chess.piece.pieces.King;
import gameapplication.model.chess.spot.Spot;
import gameapplication.view.board.components.ChessBoard;
import gameapplication.view.board.components.Space;
import gameapplication.view.instructions.InstructionsView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


/**
 * This class is responsible for updating the view based on the model
 */

public class BoardPresenter {
    // Creating a new dataframe that is a subset of the original dataframe.
    private final Chess model;
    private final BoardView view;
    private Piece[][] piecesFromModel;
    final int SIZE = 8;
    private ChessBoard board;
    private Board gameBoard;

    public BoardPresenter(Chess model, BoardView view) {
        this.model = model;
        this.view = view;
        addEventListeners();
        updateView();
    }

    /**
     * Converts a column number to a letter
     *
     * @param col The column number to convert to a letter.
     * @return The character that corresponds to the column number.
     */
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


    /**
     * This function is responsible for adding event listeners to the board
     */
    private void addEventListeners() {
        board = view.getBoard();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                final int finalX = x;
                final int finalY = y;
                // This is the code that is handling the event of a click on a space.
                view.getBoard().getSpaces()[finalX][finalY].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        // This code is handling the valid moves for the piece that was just moved.
                        handleValidMoves(model.getBoard().getPieceFromSpot(finalX, finalY));

                        // This code is setting the active space to the space that the player has moved to.
                        view.getBoard().setActiveSpace(view.getBoard().spaces[finalX][finalY]);

                        handleValidMoves(model.getBoard().getPieceFromSpot(finalX, finalY));

                        // Creating a new instance of the model and view. It is also creating a new instance of the
                        // controller.
                        view.getBoard().onSpaceClickV2(model.getBoard(), finalX, finalY);

                        view.getBoard().onSpaceClickV2(model.getBoard(), finalX, finalY);
                        // Updating the view.
                        updateView();
                    }


                });
                // The above code is creating a new instance of InstructionsView and setting it to a new stage.
                view.getChessMenu().getMiInstructions().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        // This is creating a new instance of InstructionsView and setting it to a new stage.
                        InstructionsView instructionsView = new InstructionsView();
                        Stage newStage = new Stage();
                        // This is setting the stage to be the owner of the scene.
                        newStage.initOwner(view.getScene().getWindow());
                        // Creating a new stage for the window.

                        newStage.initModality(Modality.APPLICATION_MODAL);
                        //
                        Scene scene = new Scene(instructionsView);
                        // This code is creating a new stage and setting the scene to the scene that was just created.
                        newStage.setScene(scene);
                        newStage.showAndWait();
                    }
                });

                view.getChessMenu().getMiSave().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        //
                        FileChooser fileChooser = new FileChooser();

                        // Creating a new file chooser dialog.
                        fileChooser.setTitle("Save to file");
                        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Textfiles", "*.txt"));
                        File selectedFile = fileChooser.showSaveDialog(view.getScene().getWindow());

                        // Checking if the file is writable.
                        if ((selectedFile != null) && (Files.isWritable(Paths.get(selectedFile.toURI())))) {
                            try {
                                // The above code is saving the file to the file system.
                                model.getFileManager().saveToFile(selectedFile.getPath());
                            } catch (IOException e) {
                                Alert errorWindow = new Alert(Alert.AlertType.ERROR);
                                // Showing a pop-up window with the error message.
                                errorWindow.setHeaderText("Problem with selected file");
                                errorWindow.setContentText("File is not writable: " + e.getMessage());
                                errorWindow.showAndWait();

                            }
                        }
                    }
                });

                // The above code is creating a new event handler for the load button.
                view.getChessMenu().getMiLoad().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        FileChooser fileChooser = new FileChooser();
                        // Creating a file chooser dialog box that will allow the user to select a file to load.
                        fileChooser.setTitle("Load Data File");
                                fileChooser.getExtensionFilters().addAll(
                                        new FileChooser.ExtensionFilter("Textfiles", "*.txt"));
                        File selectedFile = fileChooser.showOpenDialog(
                                // Creating a new window and setting it as the primary window.
                                view.getScene().getWindow());
                        // Checking if the file is readable.
                        if ((selectedFile != null) && (Files.isReadable(Paths.get(selectedFile.toURI())))) {
                            try {
                                // The above code is loading the file that was selected by the user.
                                System.out.println(selectedFile);
                                model.getFileManager().loadFile(String.valueOf(selectedFile.getPath()));
                                // Updating the view.
                                updateView();
                            } catch (IOException e) {
                                Alert errorWindow = new Alert(Alert.AlertType.ERROR);
                                errorWindow.setHeaderText("Problem with selected file");
                                errorWindow.setContentText("File is not readable: " + e.getMessage());
                                errorWindow.showAndWait();

                            }
                        }

                    }
                });


                // Creating a new event handler for the "restart" menu item.
                view.getChessMenu().getMiRestart().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Restarting the game.
                        model.restartGame();
                        // Updating the view.
                        updateView();
                    }
                });

                // Creating a new EventHandler for the restartGameButton.
                view.getRestartGameButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        model.restartGame();
                        updateView();
                    }
                });


                // Removing the active class from the active space.
                Space s = view.getBoard().getActiveSpace();
                if (view.getBoard().getActiveSpace() != null) {
                    view.getBoard().getActiveSpace().getStyleClass().removeAll("chess-space-active");
                }
                // Creating a new space and assigning it to the variable s.
                s = view.getActiveSpace();
                // Adding a CSS class to the active space.
                if (view.getBoard().getActiveSpace() != null) {
                    view.getBoard().getActiveSpace().getStyleClass().add("chess-space-active");
                }


            }
        }

    }

    /**
     * This function is called when the user clicks on a valid space. It checks if the user clicked on an empty space, and
     * if so, it adds the valid spaces to the list of valid spaces
     *
     * @param piece the piece that was clicked on
     */
    public void handleValidMoves(Piece piece) {
        // Checking to see if the space is valid.
        List<Space> validSpaces = view.getValidMovesSpaces();
        for (Space space : validSpaces) {
            // This code is removing the "chess-space-valid" style from the space that was just selected.
            view.getBoard().getSpaces()[space.getX()][space.getY()].getStyleClass().remove("chess-space-valid");
        }
        // Checking to see if the space is a valid attack space.
        List<Space> validAttackSpaces = view.getValidAttackSpaces();
        // Checking if the space is empty and if it is, it adds it to the list of valid attack spaces.
        for (Space space : validAttackSpaces) {
            view.getBoard().getSpaces()[space.getX()][space.getY()].getStyleClass().remove("chess-space-attackable");
        }
        // Clearing the valid moves spaces.
        view.clearValidMovesSpaces();
        // Clearing the attack spaces from the previous turn.
        view.clearAttackSpaces();
        //check if not clicked on empty cell
        if (piece == null) return;
        // Checking if the piece is a black piece or a white piece.
        PieceColor color = piece.getPieceColor();
        //check if clicked on own piece
        // The above code is checking if the last turn color is not equal to the color of the player.
        if (model.getBoard().getLastTurnColor() != color) return;
        Spot[][] validSpots = model.getBoard().getPieceFromSpot(piece.getColumn(), piece.getRow()).validMoves(model.getBoard());

        // The above code is iterating through the validSpots array and setting the validMovesSpaces array to true for each
        // valid spot.
        for (Spot[] validSpot : validSpots) {
            for (Spot spot : validSpot) {
                if (spot != null) {
                    view.setValidMovesSpaces(new Space(true, spot.getColumn(), spot.getRow()));
                }
            }
        }

        // The above code is checking if the spot that the piece is trying to move to is a valid spot to move to.
        Piece validAttackSpots = model.getBoard().getPieceFromSpot(piece.getColumn(), piece.getRow());

        // Checking if the spot is empty. If it is empty, it is adding it to the list of valid attack spots.
        for (Spot[] validSpot : validAttackSpots.getValidAttackSpots()) {
            for (Spot spot : validSpot) {
                if (spot != null) {
                    view.setValidAttackSpaces(new Space(true, spot.getColumn(), spot.getRow()));
                }
            }
        }

    }


    /**
     * This function updates the view based on the model
     */
    public void updateView() {
        //On restart, remove all space classes from board.
        // Checking if the model is restarted. If it is, it will set the model to not restarted.
        if (model.isRestarted()) {
            // This code is removing the "chess-space-attackable" and "chess-space-valid" classes from all spaces on the
            // board.
            for (Space[] spaceX : view.getBoard().getSpaces()) {
                for (Space space : spaceX) {
                    space.getStyleClass().remove("chess-space-attackable");
                    space.getStyleClass().remove("chess-space-valid");
                }
            }
            //set restarted to false
            model.setRestarted(false);

            // Hiding the game over screen.
            view.getHBoxGameOver().setVisible(false);
        }

        // Checking if the game is over. If it is, it will return true.
        if (model.getBoard().isGameOver()){
            // Showing the game over screen.
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
            // This code is adding a CSS class to the space that the king is on.
            view.getBoard().getSpaces()[king.getColumn()][king.getRow()].getStyleClass().add("chess-space-attackable");
        }


    }

    /**
     * This function returns the pieces from the model
     *
     * @return The piecesFromModel array.
     */
    public Piece[][] getPiecesFromModel() {
        return piecesFromModel;
    }

    /**
     * This function sets the piecesFromModel field to the given array
     *
     * @param piecesFromModel The pieces from the model.
     */
    public void setPiecesFromModel(Piece[][] piecesFromModel) {
        this.piecesFromModel = piecesFromModel;
    }
    public Board getGameBoard() {
        return gameBoard;
    }
}


