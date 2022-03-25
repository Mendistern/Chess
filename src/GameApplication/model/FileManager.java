package GameApplication.model;


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

public class FileManager {
    private Chess chess;

    public FileManager(Chess chess) {
        this.chess = chess;
    }


    public void saveToFile(String filePath) throws IOException {
        //get all made move
        List<String> spotsList = chess.getBoard().getMoveManager().getMovesList();
        //try write to file
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
        //get file
        Path myFile = Paths.get(fileName);
        //initialise moves list
        List<String> readLines = new ArrayList<>();


        if (Files.exists(myFile)){
            try {
                Scanner fileScanner = new Scanner(myFile);
                while (fileScanner.hasNext()) {
                    String tekst = fileScanner.nextLine();
                    //add each line to the list
                    readLines.add(tekst);
                }

                //restart game with entered move list
                chess.restarSavedGame(readLines);
            } catch (IOException ioe){
                throw new IOException("Problem Reading File: "+ioe.getMessage());
            }
        }else{
            throw new FileNotFoundException("File Not Found!");
        }

    }
}
