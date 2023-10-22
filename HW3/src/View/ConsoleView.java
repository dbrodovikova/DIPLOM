package src.View;

import java.util.Scanner;

public class ConsoleView implements View {

    // ToDo текущая настройка для моей VS code консоли (при других консолях может не
    // распознать русские буквы)
    private Scanner in = new Scanner(System.in, "IBM866");

    @Override
    public void printOutput(String message) {
        System.out.printf("%s", message);
    }

    @Override
    public String getInput(String message) {
        System.out.printf("%s", message);
        return in.nextLine();
    }

}