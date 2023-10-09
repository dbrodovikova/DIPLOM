package hw7.util.connecttodb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DBConnector {

    public static final String DBPATH = "notes.csv";

    public void connect() {
        Path pathToDB = Path.of(DBPATH);
        if(Files.exists(pathToDB)) {
            System.out.println("DB already exists.");
        } else {
            try {
                Files.createFile(pathToDB);
                System.out.println("File .csv was created.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
