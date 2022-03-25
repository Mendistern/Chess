package gameapplication.model;


import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * @author nicolas
 */

/**
 * The FileManager class is used to save and load games
 */
public class FileManager {
    // The `private Chess chess;` is used to make sure that the chess variable is only used in this class.
    private Chess chess;

    public FileManager(Chess chess) {
        this.chess = chess;
    }


    public void saveToFile(String filePath) throws IOException {
        //get all made moves
        List<String> spotsList = chess.getBoard().getMoveManager().getMovesList();
        //try write to file
        // This is a try-catch block. The try block is used to write to the file. The catch block is used to catch any
        // errors that might occur.
        try (FileWriter myWriter = new FileWriter(filePath)) {
            //loop through all moves
            for (Iterator<String> iterator = spotsList.iterator(); iterator.hasNext(); ) {
                String next = iterator.next();
                //add the move and new line
                myWriter.write(String.format("%s%n", next));
            }
        } catch (IOException e) {
            String message = String.format("Probleem met %s", filePath);
            throw new IOException(message, e);
        }

    }

    //load file and set the moves
    public void loadFile(String fileName) throws IOException {
    /**
     * Reads a file and creates a list of moves from the file
     *
     * @param fileName the name of the file to be loaded.
     */
        //get file
        Path myFile = Paths.get(fileName);
        //initialise moves list
        List<String> readLines = new ArrayList<>();


        // This is a conditional statement. It checks if the file exists. If it does, it will continue with the code. If it
        // does not, it will throw an exception.
        if (Files.exists(myFile)){
            try {
                // This is reading the file line by line and adding each line to the list.
                Scanner fileScanner = new Scanner(myFile);
                while (fileScanner.hasNext()) {
                    String tekst = fileScanner.nextLine();
                    //add each line to the list
                    readLines.add(tekst);
                }

                //restart game with entered move list
                chess.restartSavedGame(readLines);
            } catch (IOException ioe){
                // This is a way to throw an exception. It is throwing an exception with a message.
                throw new IOException("Problem Reading File: "+ioe.getMessage());
            }
        }else{
            // This is a way to throw an exception. It is throwing an exception with a message.
            throw new FileNotFoundException("File Not Found!");
        }

    }
}
