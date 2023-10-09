class UserView {
    private UserRepository userRepository;

    public UserView(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Метод для создания пользователя
    public void createUser(Long id, String name) {
        User user = new User(id, name);
        userRepository.getUsers().add(user);
    }

    // Метод для обновления пользователей после удаления
    public void updateUsersAfterDelete(Long id) {
        userRepository.delete(id);
    }
}