package hw7.view.impl;

import hw7.controller.impl.Controller;
import hw7.model.Note;
import hw7.util.commands.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class ConsoleView implements View {
    private Controller controller;

    public ConsoleView(Controller controller) {
        this.controller = controller;
    }

    public void runProgram() {
        System.out.print("Welcome to application for notes \n" +
                "Here commands which you can enter \n" +
                "CREATE, READ, READALL, UPDATE, DELETE : \n");
        while (true) {
            System.out.print("Input command: ");
            String action = actionToNotes();
            Button button = Button.valueOf(action);
            if (button.equals(Button.EXIT)) {
                System.out.println("Quit from app.");
                return;
            }
            switch (button) {
                case CREATE -> {
                    System.out.print("Please input the text: ");
                    String text = actionToNotes();
                    Note note = new Note(text, LocalDate.now());
                    controller.create(note);
                }
                case READ -> {
                    System.out.print("Input id of notes which you want to read: ");
                    Long id = Long.parseLong(actionToNotes());
                    System.out.println(controller.read(id));
                }
                case READALL -> {
                    for(Note note : controller.readAll())
                        System.out.println(note);
                }
                case UPDATE -> {
                    System.out.print("Please input id to update: ");
                    Long id = Long.parseLong(actionToNotes());
                    System.out.print("Input new text to update: ");
                    String newText = actionToNotes();
                    Note note = new Note(newText, LocalDate.now());
                    controller.update(id, note);
                }
                case DELETE -> {
                    System.out.print("Input id to delete: ");
                    Long id = Long.parseLong(actionToNotes());
                    boolean result = controller.delete(id);
                    System.out.println(result);
                }
            }
        }
    }

    public String actionToNotes() {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String readLine;
        try {
            readLine = bf.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return readLine;
    }
}
