package org.example.ui;

import org.example.db.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PermissionDebugFrame extends JFrame {
    private final int _userId;

    public PermissionDebugFrame(int userId) {
        _userId = userId;

        setTitle("Permission Debug Panel");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Обновить");
        refreshButton.addActionListener(e -> {
            outputArea.setText(debugInfo());
        });

        add(refreshButton, BorderLayout.SOUTH);

        outputArea.setText(debugInfo());
        setVisible(true);
    }

    private String debugInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Данные по пользователю ID: ").append(_userId).append("\n\n");

        try (Connection connection = DatabaseConnection.getConnection()) {

            // Получаем роли пользователя
            sb.append("Роли:\n");
            String roleQuery = """
                select r.id, r.name, r.level
                from user_roles ur
                join roles r on ur.role_id = r.id
                where ur.user_id = ?
            """;

            try (PreparedStatement roleStmt = connection.prepareStatement(roleQuery)) {
                roleStmt.setInt(1, _userId);
                ResultSet rs = roleStmt.executeQuery();

                while (rs.next()) {
                    sb.append(" - ").append(rs.getString("name"))
                            .append(" (уровень ").append(rs.getInt("level")).append(")\n");
                }
            }

            sb.append("\nПрава:\n");
            // Получаем все permissions пользователя
            String permissionQuery = """
                select distinct p.name
                from user_roles ur
                join role_permissions rp on ur.role_id = rp.role_id
                join permissions p on rp.permission_id = p.id
                where ur.user_id = ?
            """;

            try (PreparedStatement permStmt = connection.prepareStatement(permissionQuery)) {
                permStmt.setInt(1, _userId);
                ResultSet rs = permStmt.executeQuery();

                while (rs.next()) {
                    sb.append(" - ").append(rs.getString("name")).append("\n");
                }
            }

        } catch (SQLException e) {
            sb.append("Ошибка: ").append(e.getMessage());
        }

        return sb.toString();
    }
}
