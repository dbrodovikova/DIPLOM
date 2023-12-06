package ru.geekbrains.lesson5;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class BackupCreator {

    public static void main(String[] args) throws IOException {
        String sourceDirectory = "./";
        String backupDirectory = "./backup";

        createBackup(sourceDirectory, backupDirectory);
    }

    public static void createBackup(String sourceDirectory, String backupDirectory) throws IOException {
        // Создаем папку для резервных копий, если ее нет
        File backupDir = new File(backupDirectory);
        if (!backupDir.exists()) {
            backupDir.mkdir();
        }

        // Получаем список файлов в директории
        File sourceDir = new File(sourceDirectory);
        File[] filesToBackup = sourceDir.listFiles();

        // Копируем каждый файл в папку с резервными копиями
        for (File file : filesToBackup) {
            if (file.isFile()) {
                Files.copy(file.toPath(), new File(backupDir.getPath() + "/" + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}