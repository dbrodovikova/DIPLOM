public class Bird extends Animal{
    public Bird (String name, String owner) {
        super(name,owner);
    }

    public Bird(String name){
        this(name,null);
    }

    @Override
    public void swim() {}

}
