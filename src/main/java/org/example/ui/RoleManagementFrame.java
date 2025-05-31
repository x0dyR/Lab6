package org.example.ui;

import org.example.AccessControlService;
import org.example.model.Role;
import org.example.dao.RoleDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RoleManagementFrame extends JFrame {
    private final RoleDAO roleDAO = new RoleDAO();
    private final int _userId;
    private final AccessControlService _accessControlService = new AccessControlService();
    private final DefaultTableModel tableModel;
    private final JTable table;

    public RoleManagementFrame(int userId) {
        _userId = userId;

        setTitle("Role Management");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Level"}, 0);
        table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Добавить");
        JButton editButton = new JButton("Редактировать");
        JButton deleteButton = new JButton("Удалить");
        JButton refreshButton = new JButton("Обновить");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            if (checkPermission("create_roles")) addRole();
        });
        editButton.addActionListener(e -> {
            if (checkPermission("edit_roles")) editRole();
        });
        deleteButton.addActionListener(e -> {
            if (checkPermission("delete_roles")) deleteRole();
        });
        refreshButton.addActionListener(e -> refreshTable());

        setVisible(true);
    }

    private boolean checkPermission(String permission) {
        if (_accessControlService.hasPermissionFor(_userId, permission)) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "Нет доступа к этому действию");
            return false;
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Role> roles = roleDAO.getAllRoles();
        for (Role role : roles) {
            tableModel.addRow(new Object[]{role.getId(), role.getName(), role.getLevel()});
        }
    }

    private void addRole() {
        JTextField nameField = new JTextField();
        JTextField levelField = new JTextField();
        Object[] fields = {
                "Role name:", nameField,
                "Role level (целое число):", levelField
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Добавить роль", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String roleName = nameField.getText();
                int roleLevel = Integer.parseInt(levelField.getText());
                roleDAO.addRole(new Role(roleName, roleLevel));
                refreshTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Уровень должен быть целым числом");
            }
        }
    }

    private void editRole() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите роль для редактирования");
            return;
        }

        int roleId = (int) tableModel.getValueAt(selectedRow, 0);
        String currentName = (String) tableModel.getValueAt(selectedRow, 1);
        int currentLevel = (int) tableModel.getValueAt(selectedRow, 2);

        JTextField nameField = new JTextField(currentName);
        JTextField levelField = new JTextField(String.valueOf(currentLevel));
        Object[] fields = {
                "Role name:", nameField,
                "Role level (целое число):", levelField
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Редактировать роль", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String roleName = nameField.getText();
                int roleLevel = Integer.parseInt(levelField.getText());
                Role updatedRole = new Role(roleId, roleName, roleLevel);
                roleDAO.updateRole(updatedRole);
                refreshTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Уровень должен быть целым числом");
            }
        }
    }

    private void deleteRole() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите роль для удаления");
            return;
        }
        int roleId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Удалить роль?", "Подтверждение", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            roleDAO.deleteRole(roleId);
            refreshTable();
        }
    }
}
