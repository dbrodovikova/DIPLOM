public class Turtle extends Animal{
    public Turtle (String name, String owner) {
        super(name,owner);
    }

    public Turtle(String name){
        this(name,null);
    }
    @Override
    public void fly() {}
}
