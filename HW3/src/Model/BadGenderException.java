package src.Model;

public class BadGenderException extends Exception {
    String inputString;

    public BadGenderException(String inputString) {
        this.inputString = inputString;
    }

    @Override
    public String getMessage() {
        return "Неправильно указан пол (используйте только латинские буквы f или m), а не '" + inputString + "'\n";
    }
}

