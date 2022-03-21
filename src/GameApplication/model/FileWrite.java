package GameApplication.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author nicolas
 */
public class FileWrite {


    final static int MAX_LINES = 10000; // chess shouldn't take longer than 50 turns but just in case
    public String[] lines = new String[MAX_LINES];
    public String content = ""; // empty string, fill up when game progresses
    public int numberLines = 0; // empty file always starts with zero lines to it
    String Path;
    private int line_print;

    public FileWrite() {

    }

//    public void writeState(Path Path, boolean object) throws IOException {
//        try {
//
//            Piece piece = null;
//            assert false;
//            Spot spot = null;
//            PieceComp.fromPieceToPieceComp(spot.getPiece());
//            String val = piece.getPieceLocation().toString();
//            FileOutputStream fos = new FileOutputStream();
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(piece);
//            oos.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void saveToFile(Path Path, String contents) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(contents))) {
            writer.write(contents);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        }

    }

    private int level_of(String s) {
        int i = 0;
        int level = 0;
        while (i < s.length()) {
            if (s.charAt(i) == '\t') {
                level++;
                i++;
            } else {
                return level;
            }
        }
        return level;
    }

    private Hashtable toRecursiveHash(Hashtable hash, int level) {

        while (line_print < numberLines) {

            String key = lines[line_print];

            if (level_of(key) == level) {

                key = key.substring(level);

                line_print++;

                String value = lines[line_print];

                if (level_of(value) == level) {
                    value = value.substring(level);

                    hash.put(key, value);
                    line_print++;
                } else {
                    hash.put(key, toRecursiveHash(new Hashtable(), level + 1));
                }

            } else {

                return hash;

            }

        }

        return hash;

    }

    public Hashtable to_Hash() {
        Hashtable hash = new Hashtable();

        read_lines();

        line_print = 0;
        return toRecursiveHash(hash, 0);
    }

    public void from_Hash(Hashtable hash) {
        line_print = 0;
        calc_content();

        write_content();

    }

    public String calc_content() {
        content = "";
        for (int i = 0; i < numberLines; i++) {
            content += lines[i] + "\n";
        }
        return content;
    }

    public String set(String key, String value) {
        for (int i = 0; i < numberLines; i++) {
            String line = lines[i];

            Pattern get_key = Pattern.compile("^" + key + "=(.*)");
            Matcher get_key_matcher = get_key.matcher(line);

            if (get_key_matcher.find()) {
                if (value == null) {
                    return (get_key_matcher.group(1));
                }
                lines[i] = key + "=" + value;
                return value;

            }
        }

        if (value == null) {
            return null;
        }
        if (numberLines < MAX_LINES) {
            lines[numberLines++] = key + "=" + value;
        }
        return null;
    }

    public Boolean write_content() {
        Path path;

        try {
            path = Paths.get(Path);
        } catch (InvalidPathException ex) {
            System.out.println("Invalid path" + Path);

            return false;
        }

        try {
            Files.write(path, content.getBytes());
            return true;
        } catch (IOException ex) {
            System.out.println("IO error writing file " + path);
            return false;
        }
    }

    public String[] read_lines() {
        numberLines = 0;
        int counter = 0;

        File file = new File(Path);

        if (file.exists()) {
            FileReader fileReader = null;

            try {
                fileReader = new FileReader(Path);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String currentLine = null;

            do {
                try {
                    currentLine = bufferedReader.readLine();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                if (currentLine != null) {
                    if (counter < MAX_LINES) {
                        lines[counter++] = currentLine;
                        content += currentLine + "\n";
                    }
                }
            } while (currentLine != null);

            if (counter == 0) {
                return null;
            }

            numberLines = counter;

            return Arrays.copyOfRange(lines, 0, counter);

        }
        return null;
    }

    public boolean add_lines(String line) {
        read_lines();

        for (int i = 0; i < numberLines; i++) {
            if (lines[i].equals(line)) {
                return true;
            }
        }

        if (numberLines >= MAX_LINES) {
            return false;
        }

        lines[numberLines++] = line;

        calc_content();

        return write_content();
    }

    public String setField(String key, String value) {
        read_lines();
        String result = set(key, value);

        if (result != null) {
            calc_content();
            write_content();
            return value;
        }
        return null;
    }

    public String getField(String key) {
        read_lines();
        String result = set(key, null);
        if (result != null) {
            return result;
        }
        return null;
    }


}
