package org.example.model;

public class UserRole {
    private int _userId;
    private int _roleId;

    public UserRole() {}

    public UserRole(int userId, int roleId) {
        _userId = userId;
        _roleId = roleId;
    }

    public int getUserId() {
        return _userId;
    }

    public void set_userId(int userId) {
        _userId = userId;
    }

    public int getRoleId() {
        return _roleId;
    }

    public void set_roleId(int roleId) {
        _roleId = roleId;
    }
}
