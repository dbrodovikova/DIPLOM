class User {
    private Long id;
    private String name;
    // Другие поля пользователя

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Геттеры и сеттеры для полей

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}