package org.example.ui;

import org.example.*;
import org.example.dao.PermissionDAO;
import org.example.dao.RoleDAO;
import org.example.dao.RolePermissionDAO;
import org.example.model.Permission;
import org.example.model.Role;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AssignPermissionFrame extends JFrame {
    private final RoleDAO roleDAO = new RoleDAO();
    private final PermissionDAO permissionDAO = new PermissionDAO();
    private final RolePermissionDAO rolePermissionDAO = new RolePermissionDAO();
    private final int _userId;
    private final AccessControlService _accessControlService = new AccessControlService();

    public AssignPermissionFrame(int userId) {
        _userId = userId;

        setTitle("Назначение прав ролям");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        JComboBox<Role> roleComboBox = new JComboBox<>();
        JComboBox<Permission> permissionComboBox = new JComboBox<>();

        // Загружаем роли
        List<Role> roles = roleDAO.getAllRoles();
        for (Role role : roles) {
            roleComboBox.addItem(role);
        }

        // Загружаем permissions
        List<Permission> permissions = permissionDAO.getAllPermissions();
        for (Permission permission : permissions) {
            permissionComboBox.addItem(permission);
        }

        JButton assignButton = new JButton("Назначить Permission");

        assignButton.addActionListener(e -> {
            if (!_accessControlService.hasPermissionFor(_userId, "edit_permissions")) {
                JOptionPane.showMessageDialog(this, "У вас нет прав для назначения permissions");
                return;
            }

            Role selectedRole = (Role) roleComboBox.getSelectedItem();
            Permission selectedPermission = (Permission) permissionComboBox.getSelectedItem();

            rolePermissionDAO.assignPermissionToRole(selectedRole.getId(), selectedPermission.getId());
            JOptionPane.showMessageDialog(this, "Permission назначен успешно");
        });

        add(new JLabel("Выберите роль:"));
        add(roleComboBox);
        add(new JLabel("Выберите permission:"));
        add(permissionComboBox);
        add(assignButton);

        setVisible(true);
    }
}
