package domain;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private int age;
    private ArrayList<Consomation> consomationList = new ArrayList<>();
    public User( String name, int age){

        this.id = id;
        this.name = name;
        this.age = age;
        this.consomationList = new ArrayList<>();

    }

    public User() {

    }

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



    public String toString() {
        return "ID: " + id + ", Nom: " + name + ", Age: " + age;
    }
    public void AddCarbon(Consomation consomation){
        consomationList.add(consomation);
    }
    public List<Consomation> getConsommations() {
        return consomationList;
    }

}

