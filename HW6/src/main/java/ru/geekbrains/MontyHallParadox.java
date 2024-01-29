package ru.geekbrains;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Урок 6. Управление проектом: сборщики проектов
 * В качестве задачи предлагаю вам реализовать код для демонстрации парадокса Монти Холла
 * (Парадокс Монти Холла — Википедия) и наглядно убедиться в верности парадокса
 * (запустить игру в цикле на 1000 и вывести итоговый счет).
 * Необходимо:
 * Создать свой Java Maven или Gradle проект;
 * Самостоятельно реализовать прикладную задачу;
 * Сохранить результат в HashMap<шаг теста, результат>;
 * Вывести на экран статистику по победам и поражениям.
 */

public class MontyHallParadox {
    static Random random;
    static Map<Integer, Boolean> results1;       // Статистика для игрока, не меняющего свой выбор.
    static Map<Integer, Boolean> results2;       // Статистика для игрока, изменяющего свой выбор.
    static int doorsNumber;                      // Количество дверей.
    static int attempts;                         // Количество попыток.

    public static void main(String[] args) {
        random = new Random();
        results1 = new HashMap<>();
        results2 = new HashMap<>();
        doorsNumber = 3;
        attempts = 1000;

        for (int i = 0; i < attempts; i++) {     // Розыгрыш (1000 попыток).
            trial(i);
        }

        int win = 0;                             // Статистика для первого игрока, не меняющего свой выбор.
        for (Map.Entry<Integer, Boolean> entry: results1.entrySet()){
            if (entry.getValue()){
                win++;
            }
        }
        System.out.println("\nПарадокс Монти Холла");
        System.out.println("\nСтатистика выигрышей для игрока, не меняющего свой выбор:");
        System.out.println("Количество побед: " + win + " раз из " + attempts + " попыток.");

        win = 0;                                  // Статистика для второго игрока, изменяющего свой выбор.
        for (Map.Entry<Integer, Boolean> entry: results2.entrySet()){
            if (entry.getValue()){
                win++;
            }
        }
        System.out.println("\nСтатистика выигрышей для игрока, изменяющего свой выбор:");
        System.out.println("Количество побед: " + win + " раз из " + attempts + " попыток.");
    }

    private static void trial(int numRound) {
        int success = random.nextInt(doorsNumber);
        int firstChoice = random.nextInt(doorsNumber);
        int freeOpenDoor = -1;
        int secondChoice = -1;

        for (int i = 0; i < doorsNumber; i++) {
            if (i != success && i != firstChoice){
                freeOpenDoor = i;
            }
        }

        for (int i = 0; i < doorsNumber; i++) {            // Игрок не изменяет свой выбор.
            if (i != freeOpenDoor && i != firstChoice){
                secondChoice = firstChoice;
            }
        }
        results1.put(numRound, success == secondChoice);   // Статистика для первого игрока.

        for (int i = 0; i < doorsNumber; i++) {            // Игрок не изменяет свой выбор.
            if (i != freeOpenDoor && i != firstChoice){
                secondChoice = i;
            }
        }
        results2.put(numRound, success == secondChoice);   // Статистика для второго игрока.
    }
}