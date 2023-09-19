
import java.util.ArrayList;
import java.util.List;

public class Main{

    public static void main(String[] args){                
        List<Animal> animals = new ArrayList<Animal>();
            animals.add(new Bird("Гоша","Кеша"));
            animals.add(new Duck("Скрудж","Макдак"));
            animals.add(new Fish("Пиксель","Аква"));
            animals.add(new Cat("Персик","Мелисса"));     
            animals.add(new Turtle("Рафаэль","Леонардо"));         

        for (Animal a: animals) a.toGo();
            System.out.println();
    
        for (Animal a: animals) a.fly();               
            System.out.println();
        
        for (Animal a: animals) a.swim();
            System.out.println();
    }
}
