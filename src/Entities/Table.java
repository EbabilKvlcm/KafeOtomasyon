package Entities;

public class Table {
    private int id;
    private String name;

    public Table(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }

    @Override
    public String toString() {
        return name;
    }
}
