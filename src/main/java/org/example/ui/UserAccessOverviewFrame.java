package org.example.ui;

import org.example.dao.RoleDAO;
import org.example.dao.UserDAO;
import org.example.dao.UserRoleDAO;
import org.example.dao.RolePermissionDAO;
import org.example.dao.PermissionDAO;
import org.example.model.Permission;
import org.example.model.Role;
import org.example.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserAccessOverviewFrame extends JFrame {

    private final UserDAO userDAO = new UserDAO();
    private final UserRoleDAO userRoleDAO = new UserRoleDAO();
    private final RoleDAO roleDAO = new RoleDAO();
    private final RolePermissionDAO rolePermissionDAO = new RolePermissionDAO();
    private final PermissionDAO permissionDAO = new PermissionDAO();

    private final JComboBox<User> userComboBox = new JComboBox<>();
    private final JTextArea resultArea = new JTextArea();

    public UserAccessOverviewFrame() {
        setTitle("Обзор доступа пользователя");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        loadUsers();

        userComboBox.addActionListener(e -> loadAccessData());

        add(userComboBox, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
        resultArea.setEditable(false);

        setVisible(true);
    }

    private void loadUsers() {
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            userComboBox.addItem(user);
        }
    }

    private void loadAccessData() {
        resultArea.setText("");

        User user = (User) userComboBox.getSelectedItem();
        if (user == null) return;

        resultArea.append("Пользователь: " + user.getUsername() + "\n");
        resultArea.append("Назначенные роли:\n");

        List<Role> roles = userRoleDAO.getRolesByUser(user.getId());
        for (Role role : roles) {
            resultArea.append(" - " + role.getName() + " (уровень " + role.getLevel() + ")\n");
        }

        resultArea.append("\nПолученные права:\n");

        for (Role role : roles) {
            List<Permission> permissions = rolePermissionDAO.getPermissionsByRole(role.getId());
            for (Permission permission : permissions) {
                resultArea.append(" - " + permission.getName() + "\n");
            }
        }
    }
}
