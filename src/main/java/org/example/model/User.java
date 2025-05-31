package org.example.model;

public class User {
    private int _id;
    private String _username;
    private String _password;

    public User() {}

    public User(int id, String username, String password) {
        _id = id;
        _username = username;
        _password = password;
    }

    public User(String username, String password) {
        _username = username;
        _password = password;
    }

    public int getId() {
        return _id;
    }

    public void set_id(int id) {
        _id = id;
    }

    public String getUsername() {
        return _username;
    }

    public void set_username(String username) {
        _username = username;
    }

    public String getPassword() {
        return _password;
    }

    public void set_password(String password) {
        _password = password;
    }

    @Override
    public String toString() {
        return _username;
    }
}

