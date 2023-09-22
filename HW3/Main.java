import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // Создаем несколько экземпляров Pharmacy
        Pharmacy pharmacy1 = new Pharmacy("Лекарство 1", 100);
        Pharmacy pharmacy2 = new Pharmacy("Лекарство 2", 200);
        Pharmacy pharmacy3 = new Pharmacy("Лекарство 1", 100);
        Pharmacy pharmacy4 = new Pharmacy("Лекарство 3", 100);

        // Добавляем лекарства в сет
        Set<Pharmacy> result = new HashSet<>();
        result.add(pharmacy1);
        result.add(pharmacy2);
        result.add(pharmacy3);
        result.add(pharmacy4);

        // Выводим количество уникальных экземпляров Pharmacy
        System.out.println(result.size()); // Вывод: 3
    }
}