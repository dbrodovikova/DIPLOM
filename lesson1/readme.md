## Урок 1. Компиляция и интерпретация кода

Создать [проект из трёх классов](/src/main/java/ru/geekbrains/lesson1) (основной с точкой входа и два класса в другом пакете),
которые вместе должны составлять одну программу, позволяющую
производить четыре основных математических действия и осуществлять форматированный
вывод результатов пользователю (ИЛИ ЛЮБОЕ ДРУГОЕ ПРИЛОЖЕНИЕ НА ВАШ ВЫБОР, которое просто демонстрирует работу некоторого механизма).

- Необходимо установить Docker Desktop.
- Создать [Dockerfile](/src/main/dockerfile), позволяющий откопировать исходный код вашего приложения в образ для демонстрации работы вашего приложения при создании соответствующего контейнера.
```dockerfile
FROM bellsoft/liberica-openjdk-alpine:latest
COPY ./java ./src
RUN mkdir ./out
RUN javac -sourcepath ./src -d out src/ru/geekbrains/lesson1/sample/Main.java
CMD java -classpath ./out ru.geekbrains.lesson1.sample.Main
```

## Урок 2. Функции, манипулирующие данными
[link for lesson 2 programm](src/main/java/ru/geekbrains/lesson2/Program.java)
1. Полностью разобраться с кодом программы разработанной на семинаре, переписать программу. Это минимальная задача для сдачи домашней работы. [✓ done]()

Усложняем задачу:
2. ** Переработать метод проверки победы, логика проверки победы должна работать для поля 5х5 и
количества фишек 4. Очень желательно не делать это просто набором условий для каждой из
возможных ситуаций! Используйте вспомогательные методы, используйте циклы! 
 [✓ done]()

3. **** Доработать искусственный интеллект, чтобы он мог блокировать ходы игрока.


## Урок 3. Классы и объекты
[link for directory](src/main/java/ru/geekbrains/lesson3/)

- Построить три класса ([базовый](src/main/java/ru/geekbrains/lesson3/Worker.java) и 2 потомка), описывающих некоторых [работников с почасовой оплатой](src/main/java/ru/geekbrains/lesson3/HourlyWorker.java) (один из потомков) и [фиксированной оплатой](src/main/java/ru/geekbrains/lesson3/FixedWorker.java) (второй потомок).
  - Описать в базовом классе абстрактный метод для расчёта среднемесячной заработной платы.
  ```java
   public abstract double calculateAverageSalary();
  ```
  Для «повременщиков» формула для расчета такова: «среднемесячная заработная плата = 20.8 * 8 * почасовая ставка», для работников с фиксированной оплатой «среднемесячная заработная плата = фиксированная месячная оплата».
  ```java
    @Override
    public double calculateAverageSalary() {
        return 20.8 * 8 * hourlyRate;
    }
  
    @Override
    public double calculateAverageSalary() {
        return fixedMonthlyPayment;
    }
  ```
  - Создать на базе абстрактного класса массив сотрудников и заполнить его.
  - ** Реализовать интерфейсы для возможности сортировки массива (обратите ваше внимание на интерфейсы Comparator, Comparable)
  - ** Создать класс, содержащий массив сотрудников, и реализовать возможность вывода данных с использованием foreach.
  ```java
  class WorkersArray {
      private Worker[] workers;
  
      public WorkersArray(Worker[] workers) {
          this.workers = workers;
      }
  
      // Метод для сортировки массива работников по имени
      public void sortByName() {
          Arrays.sort(workers, Comparator.comparing(Worker::getName));
      }
  
      // Метод для сортировки массива работников по заработной плате
      public void sortByAverageSalary() {
          Arrays.sort(workers, Comparator.comparing(Worker::calculateAverageSalary));
      }
  
      // Метод для вывода информации о работниках
      public void printWorkersInfo() {
          for (Worker worker : workers) {
              System.out.println("Name: " + worker.getName() + ", Average Salary: " + worker.calculateAverageSalary());
          }
      }
  }
  ```

# Урок 4. Обработка исключений

[link for directory](src/main/java/ru/geekbrains/lesson4/)
- Напишите метод, на вход которого подаётся двумерный строковый массив размером 4х4. При
подаче массива другого размера необходимо бросить исключение MyArraySizeException.
- Далее метод должен пройтись по всем элементам массива, преобразовать в int и
просуммировать. Если в каком-то элементе массива преобразование не удалось (например, в
ячейке лежит символ или текст вместо числа), должно быть брошено исключение
MyArrayDataException с детализацией, в какой именно ячейке лежат неверные данные.
- В методе main() вызвать полученный метод, обработать возможные исключения
MyArraySizeException и MyArrayDataException и вывести результат расчета.

```java
class MyArraySizeException extends Exception {
}

class MyArrayDataException extends Exception {
    private int row;
    private int column;

    public MyArrayDataException(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
```

```java
public static int processArray(String[][] array) throws MyArraySizeException, MyArrayDataException {
    if (array.length != 4 || array[0].length != 4) {
        throw new MyArraySizeException();
    }

    int sum = 0;
    for (int i = 0; i < array.length; i++) {
        for (int j = 0; j < array[i].length; j++) {
            try {
                sum += Integer.parseInt(array[i][j]);
            } catch (NumberFormatException e) {
                throw new MyArrayDataException(i, j);
            }
        }
    }

    return sum;
}
```

# Урок 5. Внешний мир
[folder with code](src/main/java/ru/geekbrains/lesson5)
1. Написать функцию, создающую резервную копию всех файлов в директории(без поддиректорий) во вновь созданную папку ./backup
[backup code](src/main/java/ru/geekbrains/lesson5/BackupCreator.java)
2. Доработайте класс Tree и метод print который мы разработали на семинаре. Ваш метод должен распечатать полноценное дерево директорий и файлов относительно корневой директории.
[print tree code](src/main/java/ru/geekbrains/lesson5/Tree.java)
3. ***. Предположить, что числа в исходном массиве из 9 элементов имеют диапазон[0, 3], и представляют собой, например, состояния ячеек поля для игры в крестикинолики, где 0 – это пустое поле, 1 – это поле с крестиком, 2 – это поле с ноликом,
3 – резервное значение. Такое предположение позволит хранить в одном числе типа int всё поле 3х3. Записать в файл 9 значений так, чтобы они заняли три байта
[play to bin file](src/main/java/ru/geekbrains/lesson5/PlayToBin.java)