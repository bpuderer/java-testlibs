package models;

public class Product {
    // serialized object form of the request body
    // name exact as will appear in body
    private int id;
    private String name;

    public Product() {}

    public Product(int id, String name) {
        setId(id);
        setName(name);
    }

    public Product(String name) {
        setName(name);
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
}
