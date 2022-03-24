package GameApplication.model;

import GameApplication.model.chess.Board;
import GameApplication.model.chess.spot.Spot;

import java.io.File;
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
        List<String> spotsList = chess.getBoard().getMoveManager().getMovesList();
        try (FileWriter myWriter = new FileWriter(filePath)) {
            for (Iterator<String> iterator = spotsList.iterator(); iterator.hasNext(); ) {
                String next = iterator.next();

                myWriter.write(String.format("%s%n", next));
            }
        } catch (IOException e) {
            String message = String.format("Probleem met %s", filePath);
            throw new IOException(message, e);
        }

    }


    public void loadFile(String fileName) throws IOException {
        Path myFile = Paths.get(fileName);
        List<String> readLines = new ArrayList<String>();

        if (Files.exists(myFile)){
            try {
                Scanner fileScanner = new Scanner(myFile);
                while (fileScanner.hasNext()) {
                    String tekst = fileScanner.nextLine();
                    readLines.add(tekst);
                }


                chess.restarSavedGame(readLines);
            } catch (IOException ioe){
                throw new IOException("Problem Reading File: "+ioe.getMessage());
            }
        }else{
            throw new FileNotFoundException("File Not Found!");
        }

    }
}
