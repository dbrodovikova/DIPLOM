class LinkedList<T> {
    private Node<T> head; // головной узел
    private int size; // размер списка

    // Конструктор для создания пустого списка
    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    // Метод для добавления элемента в конец списка
    public void addLast(T value) {
        Node<T> newNode = new Node<>(value);

        // Если список пустой, то новый элемент становится головным узлом
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;

            // Ищем последний узел
            while (current.next != null) {
                current = current.next;
            }

            // Добавляем новый узел в конец списка
            current.next = newNode;
        }

        size++; // Увеличиваем размер списка
    }
    
    // Метод для получения размера списка
    public int size() {
        return size;
    }

    // Метод для получения элемента по индексу
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> current = head;

        // Идем по списку до нужного индекса
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data; // Возвращаем данные узла
    }
}