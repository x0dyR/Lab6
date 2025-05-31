package org.example.ui;

import org.example.db.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RBACOverviewFrame extends JFrame {
    private final int _userId;

    public RBACOverviewFrame(int userId) {
        _userId = userId;

        setTitle("RBAC Полный обзор");
        setSize(700, 600);
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

        try (Connection connection = DatabaseConnection.getConnection()) {

            sb.append("Пользователи и их роли:\n");
            String userRoleQuery = """
                select u.id, u.username, r.name as role_name, r.level
                from users u
                left join user_roles ur on u.id = ur.user_id
                left join roles r on ur.role_id = r.id
                order by u.id;
            """;

            try (PreparedStatement stmt = connection.prepareStatement(userRoleQuery);
                 ResultSet rs = stmt.executeQuery()) {

                int lastUserId = -1;
                while (rs.next()) {
                    int userId = rs.getInt("id");
                    if (userId != lastUserId) {
                        sb.append("\nUser: ").append(rs.getString("username")).append(" (id: ").append(userId).append(")\n");
                        lastUserId = userId;
                    }
                    String roleName = rs.getString("role_name");
                    if (roleName != null) {
                        sb.append("  - Role: ").append(roleName).append(" (Level: ").append(rs.getInt("level")).append(")\n");
                    }
                }
            }

            sb.append("\nРоли и их permissions:\n");
            String rolePermQuery = """
                select r.name as role_name, p.name as permission_name
                from roles r
                left join role_permissions rp on r.id = rp.role_id
                left join permissions p on rp.permission_id = p.id
                order by r.name;
            """;

            try (PreparedStatement stmt = connection.prepareStatement(rolePermQuery);
                 ResultSet rs = stmt.executeQuery()) {

                String lastRole = "";
                while (rs.next()) {
                    String roleName = rs.getString("role_name");
                    if (!roleName.equals(lastRole)) {
                        sb.append("\nRole: ").append(roleName).append("\n");
                        lastRole = roleName;
                    }
                    String perm = rs.getString("permission_name");
                    if (perm != null) {
                        sb.append("  - Permission: ").append(perm).append("\n");
                    }
                }
            }

        } catch (SQLException e) {
            sb.append("Ошибка: ").append(e.getMessage());
        }

        return sb.toString();
    }
}
