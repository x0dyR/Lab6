package org.example;

import org.example.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccessControlService {
    public boolean hasPermissionFor(int userId, String permissionName) {
        String sql = """
        select count(*) as cnt
        from user_roles ur
        join role_permissions rp on ur.role_id = rp.role_id
        join permissions p on rp.permission_id = p.id
        where ur.user_id = ? and lower(p.name) = lower(?)
    """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setString(2, permissionName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("cnt") > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
