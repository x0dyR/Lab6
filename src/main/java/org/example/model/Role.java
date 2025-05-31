package org.example.model;

public class Role {
    private int _id;
    private String _name;
    private int _level;

    public Role() {}

    public Role(int id, String name, int level) {
        _id = id;
        _name = name;
        _level = level;
    }

    public Role(String name, int level) {
        _name = name;
        _level = level;
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

    public int getLevel() {
        return _level;
    }

    public void set_level(int level) {
        _level = level;
    }

    @Override
    public String toString() {
        return _name + " (level " + _level + ")";
    }
}
