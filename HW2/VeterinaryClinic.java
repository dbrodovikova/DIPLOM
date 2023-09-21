import java.util.ArrayList;
import java.util.List;

class VeterinaryClinic {
    // Метод для получения всех бегающих
    public List<Runnable> getAllRunners() {
        List<Runnable> runners = new ArrayList<>();        
        return runners;
    }

    // Метод для получения всех плавающих
    public List<Swimable> getAllSwimmers() {
        List<Swimable> swimmers = new ArrayList<>();        
        return swimmers;
    }

    // Метод для получения всех говорящих
    public List<Talkable> getAllTalkers() {
        List<Talkable> talkers = new ArrayList<>();        
        return talkers;
    }

    // Метод для получения всех летающих
    public List<Flyable> getAllFlyers() {
        List<Flyable> flyers = new ArrayList<>();       
        return flyers;
    }
}