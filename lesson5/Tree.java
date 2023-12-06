package ru.geekbrains.lesson5;

import java.io.File;

public class Tree {
    /**
     * recursive print tree of files and folders
     * @param file file to print
     * @param indent level of subfolder
     * @param isLast this file is last?
     */
    public static void print(File file, String indent, boolean isLast) {
        System.out.print(indent);

        if (isLast) {
            System.out.print("└─");
            indent += "  ";
        } else {
            System.out.print("├─");
            indent += "│ ";
        }

        if (file.isDirectory()) {
            System.out.println(file.getName() + "/");
        } else {
            System.out.println("F " + file.getName());
            return;
        }

        File[] files = file.listFiles();
        if (files == null) {
            return;
        }

        int subDirTotal = 0;
        int fileTotal = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                subDirTotal++;
            } else {
                fileTotal++;
            }
        }

        int subDirCounter = 0;
        int fileCounter = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                print(files[i], indent, subDirCounter == subDirTotal - 1 && fileCounter == fileTotal);
                subDirCounter++;
            } else {
                print(files[i], indent, fileCounter == fileTotal - 1 && subDirCounter == subDirTotal);
                fileCounter++;
            }
        }
    }
}