public class Fish extends Animal {
    public Fish (String name, String owner) {
        super(name,owner);
    }

    public Fish(String name){
        this(name,null);
    }

    @Override
    public void toGo() {}
    

}
