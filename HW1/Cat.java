public class Cat extends Animal{
    public Cat (String name, String owner) {
        super(name,owner);
    }

    public Cat(String name){
        this(name,null);
    }

@Override
    public void fly() {}

    public void swim () {}
}
