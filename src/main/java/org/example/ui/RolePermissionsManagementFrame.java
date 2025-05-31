package org.example.ui;

import org.example.dao.PermissionDAO;
import org.example.dao.RoleDAO;
import org.example.dao.RolePermissionDAO;
import org.example.model.Permission;
import org.example.model.Role;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RolePermissionsManagementFrame extends JFrame {
    private final RoleDAO roleDAO = new RoleDAO();
    private final PermissionDAO permissionDAO = new PermissionDAO();
    private final RolePermissionDAO rolePermissionDAO = new RolePermissionDAO();

    private final JComboBox<Role> roleComboBox = new JComboBox<>();
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Permission ID", "Permission Name"}, 0);
    private final JTable table = new JTable(tableModel);

    public RolePermissionsManagementFrame() {
        setTitle("Управление правами ролей");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        loadRoles();

        roleComboBox.addActionListener(e -> refreshPermissionsTable());
        add(roleComboBox, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton addButton = new JButton("Добавить Permission");
        JButton removeButton = new JButton("Удалить Permission");

        addButton.addActionListener(e -> addPermission());
        removeButton.addActionListener(e -> removePermission());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadRoles() {
        List<Role> roles = roleDAO.getAllRoles();
        for (Role role : roles) {
            roleComboBox.addItem(role);
        }
    }

    private void refreshPermissionsTable() {
        tableModel.setRowCount(0);
        Role role = (Role) roleComboBox.getSelectedItem();
        if (role != null) {
            List<Permission> permissions = rolePermissionDAO.getPermissionsByRole(role.getId());
            for (Permission permission : permissions) {
                tableModel.addRow(new Object[]{permission.getId(), permission.getName()});
            }
        }
    }

    private void addPermission() {
        Role role = (Role) roleComboBox.getSelectedItem();
        if (role == null) return;

        List<Permission> allPermissions = permissionDAO.getAllPermissions();
        Permission selected = (Permission) JOptionPane.showInputDialog(
                this,
                "Выберите permission:",
                "Назначить permission",
                JOptionPane.PLAIN_MESSAGE,
                null,
                allPermissions.toArray(),
                null
        );

        if (selected != null) {
            rolePermissionDAO.assignPermissionToRole(role.getId(), selected.getId());
            refreshPermissionsTable();
        }
    }

    private void removePermission() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) return;

        Role role = (Role) roleComboBox.getSelectedItem();
        int permissionId = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Удалить permission у роли?", "Подтверждение", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            rolePermissionDAO.removePermissionFromRole(role.getId(), permissionId);
            refreshPermissionsTable();
        }
    }
}
