package domain;

import repositorie.ConsomationRepository;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;  // Auto-incremented in the database, so you don't set this manually
    private String name;
    private int age;
    private ArrayList<Consomation> consomationList = new ArrayList<>();

    public User(String name, int age) {
        this.name = name;
        this.age = age;
        this.consomationList = new ArrayList<>();
    }

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.consomationList = new ArrayList<>();
    }

    public User() {}

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

    public List<Consomation> getConsommations() {
        return consomationList;
    }

    @Override
    public String toString() {
        return "ID: " + getId() + ", Nom: " + name + ", Age: " + age;
    }
}
