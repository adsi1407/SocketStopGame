package co.com.ceiba.socketapplication.game;

public class Round {

    private String name;
    private String lastName;
    private String city;
    private String animal;

    public Round(String name, String lastName, String city, String animal) {
        this.name = name;
        this.lastName = lastName;
        this.city = city;
        this.animal = animal;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public String getAnimal() {
        return animal;
    }
}
