package org.example.model;

public class Equipment {
    private int id;
    private String name;

    public Equipment() {}

    public Equipment(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Equipment(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
