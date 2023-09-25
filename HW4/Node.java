// Создаем класс Node, который будет представлять узел списка
class Node<T> {
    T data; // данные узла
    Node<T> next; // ссылка на следующий узел

    // Конструктор для создания нового узла
    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}