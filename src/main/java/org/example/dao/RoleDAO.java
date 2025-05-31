package org.example.dao;

import org.example.db.DatabaseConnection;
import org.example.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {

    public void addRole(Role role) {
        String sql = "insert into roles (name, level) values (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, role.getName());
            statement.setInt(2, role.getLevel());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateRole(Role role) {
        String sql = "update roles set name = ?, level = ? where id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, role.getName());
            statement.setInt(2, role.getLevel());
            statement.setInt(3, role.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteRole(int roleId) {
        String sql = "delete from roles where id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1,roleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        String sql = "select id, name, level from roles"; // ОБЯЗАТЕЛЬНО добавляем level

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while(resultSet.next()) {
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

    public int getMaxUserRoleLevel(int userId) {
        String sql = """
        select max(r.level) as max_level
        from user_roles ur
        join roles r on ur.role_id = r.id
        where ur.user_id = ?
    """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("max_level");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

}