package hw6.homework.save.impl;

import hw6.homework.User;
import hw6.homework.save.Savable;

import java.util.ArrayList;
import java.util.List;

public class Persister implements Savable<User> {
    public static boolean statusOfAction;
    private List<User> userList;

    public Persister() {
        userList = new ArrayList<>();
    }
    public boolean save(User user) {
        userList.add(user);
        System.out.println("Save user: " + user.getName());
        statusOfAction = true;
        return statusOfAction;
    }
}
