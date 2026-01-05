package Entities;

public class Table {

    private int id;
    private String name;
    private TableStatus status;

    public Table(int id, String name) {
        this.id = id;
        this.name = name;
        this.status = TableStatus.EMPTY;
    }

    public int getId() {
        return id;
    }

    public TableStatus getStatus() {
        return status;
    }

    public void setStatus(TableStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return name;
    }
}
