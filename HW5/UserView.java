// Класс UserView для взаимодействия с пользователем

import java.util.Scanner;

public class UserView {
    private UserRepository userRepository;

    public UserView(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser() {
        // Ввод данных о новом пользователе
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user name: ");
        String name = scanner.nextLine();
        System.out.println("Enter user email: ");
        String email = scanner.nextLine();
        System.out.println("Enter user password: ");
        String password = scanner.nextLine();

        // Создание объекта пользователя
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        // Вызов метода createUser из UserRepository
        userRepository.createUser(user);
    }

    public void deleteUser() {
        // Ввод id пользователя для удаления
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user id to delete: ");
        int id = scanner.nextInt();

        // Вызов метода deleteUser из UserRepository
        userRepository.deleteUser(id);
    }
}
