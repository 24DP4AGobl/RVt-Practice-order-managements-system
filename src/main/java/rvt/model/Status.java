package rvt.model;

public class Status {
    int id;
    String name;

    public Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //setter methods//
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    //getter methods//
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
