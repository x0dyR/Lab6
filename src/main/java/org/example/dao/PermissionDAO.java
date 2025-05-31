package org.example.dao;

import org.example.db.DatabaseConnection;
import org.example.model.Permission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermissionDAO {
    public void addPermission(Permission permission) {
        String sql = "insert into permissions (name) values (?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, permission.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePermission(Permission permission) {
        String sql = "update permissions set name = ? where id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, permission.getName());
            statement.setInt(2, permission.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePermission(int permissionId) {
        String sql = "delete from permissions where id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, permissionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Permission> getAllPermissions() {
        List<Permission> permissions = new ArrayList<>();
        String sql = "select id, name from permissions";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Permission permission = new Permission(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                );
                permissions.add(permission);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return permissions;
    }
}
