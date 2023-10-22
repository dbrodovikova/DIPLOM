package src.Presenter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import src.Model.ParsingDataStringException;
import src.Model.DataChecker;
import src.View.View;

public class Presenter< V extends View> {

    private DataChecker model;
    private V view;

    public Presenter(V v) {
        view = v;
    }

    public void start() {
        boolean wokring = true;
        do {
            String input = view.getInput(
                    "Введите данные через пробел (Фамилию Имя Отчество ДатуРождения НомерТелефона Пол), или Exit для прекращения программы:");
            if (input.equals("Exit")) {
                wokring = false;
                break;
            } else {
                // множественные пробелы заменяем на одинарные (не считаем это страшной ошибкой)
                String[] splitedInput = input.replaceAll("\\s+", " ").split(" ");

                int inputDataCount = checkInputDataCount(splitedInput.length);
                if (inputDataCount == -1) {
                    view.printOutput("Слишком мало данных на вводе (должно быть " + DataChecker.dataCount
                            + " разделённых пробелом ' ': Фамилия Имя Отчество НомерТелефона ДатаРождения Пол)\n");
                } else if (inputDataCount == 0) {
                    view.printOutput("Слишком много данных на вводе (должно быть " + DataChecker.dataCount
                            + " разделённых пробелом ' ': Фамилия Имя Отчество НомерТелефона ДатаРождения Пол)\n");
                } else {
                    try {
                        model = new DataChecker();
                        model.CheckData(splitedInput);
                        writePersonData(model);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParsingDataStringException e) {
                        view.printOutput(e.getMessage());
                    }
                }
            }
        } while (wokring);
    }

    // по заданию проверяем кол-во введённых данных через возврат кода ошибки при
    // несоответствии
    private int checkInputDataCount(int inputDataCount) {
        if (inputDataCount < DataChecker.dataCount) {
            return -1;
        } else if (inputDataCount > DataChecker.dataCount) {
            return 0;
        } else {
            return inputDataCount;
        }
    }
    
    //создаём или подключаемся к фаилу по фамилии и дописываем туда новые данные
    private void writePersonData(DataChecker data) throws IOException {
        File filepath = new File(data.getLastName());
        try (FileWriter fw = new FileWriter(filepath, true)) {
            fw.append(data.toString() + "\n");
        } catch (IOException e) {
            throw e;
        }
    }
}
