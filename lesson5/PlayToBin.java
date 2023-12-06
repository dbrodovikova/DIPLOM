package ru.geekbrains.lesson5;

import java.io.FileOutputStream;
import java.io.IOException;

public class PlayToBin {
    public static void main(String[] args) {
        int[] values = {1, 0, 2, 3, 1, 0, 0, 2, 3};
        byte[] bytes = new byte[3];

        // Собираем биты в байты
        for (int i = 0; i < values.length; i++) {
            int shift = i % 4 * 2;
            bytes[i / 4] |= (values[i] << shift);
        }

        // Записываем байты в файл
        try (FileOutputStream fos = new FileOutputStream("file.bin")) {
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

