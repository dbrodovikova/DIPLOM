package patterns.decorator;

public class BasicWashingProgram implements WashingProgram {

  public void fillByWater() {
    System.out.println("Заполяем водой");
  }

  public void takePowder() {
    System.out.println("Смываем порошок в барабан");
  }

  public void rotateDrum(int speed) {
    System.out.println("Прокручиваем барабан со скоростью " + speed + " оборотов");
  }

  public void drainOff() {
    System.out.println("Сливаем воду");
  }

  @Override
  public void executeProgram() {
    fillByWater();
    takePowder();
    for (int i = 0; i < 40; i++) {
      rotateDrum(30);
      rotateDrum(-30);
    }
    drainOff();
    rotateDrum(1500);
    drainOff();
    rotateDrum(1500);
  }
}
