// Интерфейс UserRepository с методами для создания и удаления пользователей

import java.util.ArrayList;
import java.util.List;

public interface UserRepository {
    void createUser(User user);
    void deleteUser(int id);
}

// Реализуем UserRepository в классе UserRepositoryImpl
public class UserRepositoryImpl implements UserRepository {
    private List<User> users = new ArrayList<>();

    @Override
    public void createUser(User user) {
        users.add(user);
        System.out.println("User created: " + user);
    }

    @Override
    public void deleteUser(int id) {
        User userToRemove = null;
        for (User user : users) {
            if (user.getId() == id) {
                userToRemove = user;
                break;
            }
        }
        if (userToRemove != null) {
            users.remove(userToRemove);
            System.out.println("User deleted: " + userToRemove);
        } else {
            System.out.println("User not found with id: " + id);
        }
    }
}