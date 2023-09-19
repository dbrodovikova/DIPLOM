
public class Animal {

    private String name;
    private Integer age;
    private String vaccination;
    private String color;
    private String species;
    private String owner;
    private Integer legsCount;

    public Animal (String name,Integer age,String vaccination,
                    String color,String species,String owner,
                    Integer legsCount){
        this.name=name;
        this.age=age;
        this.vaccination=vaccination;
        this.color=color;
        this.species=species;
        this.owner=owner;
        this.legsCount=legsCount;
    }

    public Animal (String name,String owner){
        this(name,null,null,null,null,owner,null);
    }

    @Override
    public String toString() {
        return String.format("- %s by name: %s, owner: %s :", this.getType(), this.name, this.owner); 
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }
     
    public Animal(String name) {
        this(name, null);
    }

    public Animal() {
        this(null);
    }

    public void toGo() {
        System.out.println(String.format("%s %s умеет бегать!", this.getType(), this.name));
    }

    public void fly() {
        System.out.println(String.format("%s %s умеет летать!", this.getType(), this.name));
    }

    public void swim() {
        System.out.println(String.format("%s %s умеет плавать!", this.getType(), this.name));
    }
}