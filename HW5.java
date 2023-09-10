public class HW5 {
    public static void main(String[] args) {
        phoneBook phoneBook = new phoneBook();
        phoneBook.add("123456789", "Юлия");
        phoneBook.add("987654321", "Макар");
        phoneBook.add("456789123", "Марк");
        phoneBook.add("321654987", "Степан");
        phoneBook.add("789123456", "София");
        phoneBook.add("654987321", "Василий");
        phoneBook.add("234567891", "Елизавета");
        phoneBook.add("876543219", "Егор");
        phoneBook.add("345678912", "Ян");
        phoneBook.add("432165498", "Андрей");
        phoneBook.add("876543219", "Алексей");
        phoneBook.add("234567891", "Артём");
        phoneBook.add("654987321", "Иван");
        phoneBook.add("435455423", "Андрей");
        phoneBook.add("789123446", "Михаил");
        phoneBook.add("321654987", "Даниил");
        phoneBook.add("321654987", "Мария");
        phoneBook.add("321654987", "Виталий");
        System.out.println(phoneBook.getPhoneNum("987654321"));
        System.out.println(phoneBook.getByName("Андрей"));
        System.out.println(phoneBook.getAll());
    }
}
