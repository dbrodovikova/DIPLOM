// Класс User с полями id, name, email и password
public class User {
    private int id;
    private String name;
    private String email;
    private String password;

    // Конструкторы, геттеры и сеттеры для полей класса

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int getId() {
        return 0;
    }

    public void setName(String name2) {
    }

    public void setEmail(String email2) {
    }

    public void setPassword(String password2) {
    }
}