package hw6.homework.report.impl;

import hw6.homework.User;
import hw6.homework.report.Reportable;
import hw6.homework.save.Savable;
import hw6.homework.save.impl.Persister;


public class Report implements Reportable {

    private Savable<User> saveAction;

    public Report() {
        saveAction = new Persister();
    }

    @Override
    public String report() {
        return Persister.statusOfAction ? "Save was successful" : "Can not add";
    }
}
