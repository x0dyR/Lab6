package org.example.dao;

import org.example.model.Role;
import org.example.db.DatabaseConnection;
import org.example.model.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDAO {
    public void assignRoleToUser(int userId, int roleId) {
        String sql = "insert into user_roles (user_id, role_id) values (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, roleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeRoleFromUser(int userId, int roleId) {
        String sql = "delete from user_roles where user_id = ? and role_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, roleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Role> getRolesByUser(int userId) {
        List<Role> roles = new ArrayList<>();
        String sql = """
        select r.id, r.name, r.level
        from user_roles ur
        join roles r on ur.role_id = r.id
        where ur.user_id = ?
    """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Role role = new Role(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("level")
                );
                roles.add(role);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return roles;
    }

    public List<UserRole> getAllUserRoles() {
        List<UserRole> userRoles = new ArrayList<>();
        String sql = "select user_id, role_id from user_roles";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                UserRole userRole = new UserRole(
                        resultSet.getInt("user_id"),
                        resultSet.getInt("role_id")
                );
                userRoles.add(userRole);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userRoles;
    }
}
