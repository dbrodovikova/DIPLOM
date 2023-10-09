package hw7.model.dao.impl;

import hw7.model.dao.Operation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileOperation implements Operation<String> {

    private final String pathToDB;

    public FileOperation(String fileName) {
        this.pathToDB = fileName;
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> findAll() {
        List<String> lines = new ArrayList<>();
        File file = new File(pathToDB);
        try (FileReader fr = new FileReader(file);
             BufferedReader reader = new BufferedReader(fr)) {
            String line = reader.readLine();
            if (line != null) {
                lines.add(line);
            }
            while (line != null) {
                line = reader.readLine();
                if (line != null) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    @Override
    public void saveAll(List<String> data) {
        try (FileWriter writer = new FileWriter(pathToDB, false)) {
            for (String line : data) {
                writer.write(line);
                writer.append('\n');
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
