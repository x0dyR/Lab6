package org.example.dao;

import org.example.db.DatabaseConnection;
import org.example.model.Permission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolePermissionDAO {

    public void assignPermissionToRole(int roleId, int permissionId) {
        String sql = "insert into role_permissions (role_id, permission_id) values (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roleId);
            statement.setInt(2, permissionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removePermissionFromRole(int roleId, int permissionId) {
        String sql = "delete from role_permissions where role_id = ? and permission_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roleId);
            statement.setInt(2, permissionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Вот этот метод тебе сейчас нужен
    public List<Permission> getPermissionsByRole(int roleId) {
        List<Permission> permissions = new ArrayList<>();
        String sql = """
            select p.id, p.name
            from role_permissions rp
            join permissions p on rp.permission_id = p.id
            where rp.role_id = ?
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, roleId);
            ResultSet resultSet = statement.executeQuery();

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
