package org.example.model;

public class RolePermission {
    private int _roleId;
    private int _permissionId;

    public RolePermission() {}

    public RolePermission(int roleId, int permissionId) {
        _roleId = roleId;
        _permissionId = permissionId;
    }

    public int getRoleId() {
        return _roleId;
    }

    public void set_roleId(int roleId) {
        _roleId = roleId;
    }

    public int getPermissionId() {
        return _permissionId;
    }

    public void set_permissionId(int permissionId) {
        _permissionId = permissionId;
    }
}
