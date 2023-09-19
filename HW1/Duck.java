public class Duck extends Animal{
    public Duck (String name, String owner) {
        super(name,owner);
    }

    public Duck(String name){
        this(name,null);
    }
}
