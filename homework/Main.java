package hw6.homework;

import hw6.homework.report.Reportable;
import hw6.homework.report.impl.Report;
import hw6.homework.save.Savable;
import hw6.homework.save.impl.Persister;

public class Main {
    public static void main(String[] args) {
        User user = new User("Bob");
        Savable<User> userSaver = new Persister();
        Reportable reportActions = new Report();
        userSaver.save(user);
        System.out.println(reportActions.report());

    }
}