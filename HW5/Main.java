public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        System.out.println("Исходные пользователи: " + userRepository);

        UserView userView = new UserView(userRepository);
        userView.updateUsersAfterDelete(2L);
        System.out.println("Пользователи после удаления: " + userRepository);

        userView.createUser(4L, "User 4");
        System.out.println("Пользователи после создания: " + userRepository);
    }
}