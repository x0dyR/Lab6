package org.example.model;

public class Permission {
    private int _id;
    private String _name;

    public Permission() {}

    public Permission(int id, String name) {
        _id = id;
        _name = name;
    }

    public Permission(String name) {
        _name = name;
    }

    public int getId() {
        return _id;
    }

    public void set_id(int id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void set_name(String name) {
        _name = name;
    }

    @Override
    public String toString() {
        return _name;
    }
}
