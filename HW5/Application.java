// Класс Application, где будет основное выполнение программы
public class Application {
    public static void main(String[] args) {
        // Объект класса UserRepositoryImpl
        UserRepository userRepository = new UserRepositoryImpl();

        // Объект класса UserView и передаем ему userRepository
        UserView userView = new UserView(userRepository);

        // Объект userView для вызова методов createUser и deleteUser
        userView.createUser();
        userView.deleteUser();
    }
}