package org.example.ui;

import org.example.dao.RoleDAO;
import org.example.dao.UserDAO;
import org.example.dao.UserRoleDAO;
import org.example.model.Role;
import org.example.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserRolesManagementFrame extends JFrame {
    private final UserDAO userDAO = new UserDAO();
    private final RoleDAO roleDAO = new RoleDAO();
    private final UserRoleDAO userRoleDAO = new UserRoleDAO();

    private final JComboBox<User> userComboBox = new JComboBox<>();
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Role ID", "Role Name"}, 0);
    private final JTable table = new JTable(tableModel);

    public UserRolesManagementFrame() {
        setTitle("Управление ролями пользователей");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        loadUsers();

        userComboBox.addActionListener(e -> refreshRolesTable());
        add(userComboBox, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton addButton = new JButton("Добавить роль");
        JButton removeButton = new JButton("Удалить роль");

        addButton.addActionListener(e -> addRole());
        removeButton.addActionListener(e -> removeRole());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadUsers() {
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            userComboBox.addItem(user);
        }
    }

    private void refreshRolesTable() {
        tableModel.setRowCount(0);
        User user = (User) userComboBox.getSelectedItem();
        if (user != null) {
            List<Role> roles = userRoleDAO.getRolesByUser(user.getId());
            for (Role role : roles) {
                tableModel.addRow(new Object[]{role.getId(), role.getName()});
            }
        }
    }

    private void addRole() {
        User user = (User) userComboBox.getSelectedItem();
        if (user == null) return;

        List<Role> allRoles = roleDAO.getAllRoles();
        Role selected = (Role) JOptionPane.showInputDialog(
                this,
                "Выберите роль для назначения:",
                "Назначить роль",
                JOptionPane.PLAIN_MESSAGE,
                null,
                allRoles.toArray(),
                null
        );

        if (selected != null) {
            userRoleDAO.assignRoleToUser(user.getId(), selected.getId());
            refreshRolesTable();
        }
    }

    private void removeRole() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) return;

        User user = (User) userComboBox.getSelectedItem();
        int roleId = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Удалить роль у пользователя?", "Подтверждение", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            userRoleDAO.removeRoleFromUser(user.getId(), roleId);
            refreshRolesTable();
        }
    }
}
