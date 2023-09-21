class Nurse {
    // Метод, который будет вызываться из класса Doctor для взаимодействия
    public void performTask() {
        
    }
}

// Создаем интерфейс Runnable с методом получения скорости бега
interface Runnable {
    double getRunningSpeed();
}

// Создаем интерфейс Flyable с методом получения скорости полета
interface Flyable {
    double getFlyingSpeed();
}

// Создаем интерфейс Swimable с методом получения скорости плавания
interface Swimable {
    double getSwimmingSpeed();
}