package demo;

public class Fruit {

    private String id;
    private String name;
    private String species;

    public Fruit() {
    }

    public Fruit(String id, String name, String species) {
        this.id = id;
        this.name = name;
        this.species = species;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "id='" + id + "\', " +
                "name='" + name + "\', " +
                "species='" + species + "\'" +
                '}';
    }
}
