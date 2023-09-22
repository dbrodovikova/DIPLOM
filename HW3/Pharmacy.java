import java.util.Objects;

// Класс, представляющий лекарство
class Pharmacy implements Comparable<Pharmacy> {
    private String name; // название лекарства
    private int power; // мощность лекарства

    // Конструктор лекарства
    public Pharmacy(String name, int power) {
        this.name = name;
        this.power = power;
    }

    // Метод сравнения лекарств по мощности и названию компонентов
    @Override
    public int compareTo(Pharmacy other) {
        // Сначала сравниваем мощность
        if (this.power != other.power) {
            return Integer.compare(this.power, other.power);
        }

        // Если мощность равна, сравниваем названия компонентов
        return this.name.compareTo(other.name);
    }
    // Переопределение методов equals и hashCode для уникальности экземпляров
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pharmacy pharmacy = (Pharmacy) o;
        return power == pharmacy.power && Objects.equals(name, pharmacy.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, power);
    }
}