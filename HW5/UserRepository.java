import java.util.ArrayList;
import java.util.List;

class UserRepository {
    private List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
        // Инициализация списка пользователей
        users.add(new User(1L, "User 1"));
        users.add(new User(2L, "User 2"));
        users.add(new User(3L, "User 3"));
    }

    // Метод для удаления пользователя
    public boolean delete(Long id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                users.remove(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "UserRepository{" +
                "users=" + users +
                '}';
    }
}