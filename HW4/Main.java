public class Main {
    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<>();

        linkedList.addLast("Привет");
        linkedList.addLast("Мир");

        System.out.println(linkedList.size()); // Выведет: 2
        System.out.println(linkedList.get(0)); // Выведет: Привет
        System.out.println(linkedList.get(1)); // Выведет: Мир
    }
}