package org.example.ui;

import org.example.model.User;
import org.example.dao.UserDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserManagementFrame extends JFrame {
    private final UserDAO userDAO = new UserDAO();
    private final DefaultTableModel tableModel;
    private final JTable table;

    public UserManagementFrame() {
        setTitle("User Management");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Username", "Password"}, 0);
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

        addButton.addActionListener(e -> addUser());
        editButton.addActionListener(e -> editUser());
        deleteButton.addActionListener(e -> deleteUser());
        refreshButton.addActionListener(e -> refreshTable());

        setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            tableModel.addRow(new Object[]{user.getId(), user.getUsername(), user.getPassword()});
        }
    }

    private void addUser() {
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        Object[] fields = {
                "Username:", usernameField,
                "Password:", passwordField
        };
        int option = JOptionPane.showConfirmDialog(null, fields, "Добавить пользователя", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            userDAO.addUser(new User(usernameField.getText(), passwordField.getText()));
            refreshTable();
        }
    }

    private void editUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите пользователя для редактирования");
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        String currentUsername = (String) tableModel.getValueAt(selectedRow, 1);
        String currentPassword = (String) tableModel.getValueAt(selectedRow, 2);

        JTextField usernameField = new JTextField(currentUsername);
        JTextField passwordField = new JTextField(currentPassword);
        Object[] fields = {
                "Username:", usernameField,
                "Password:", passwordField
        };
        int option = JOptionPane.showConfirmDialog(null, fields, "Редактировать пользователя", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            User updatedUser = new User(userId, usernameField.getText(), passwordField.getText());
            userDAO.updateUser(updatedUser);
            refreshTable();
        }
    }

    private void deleteUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите пользователя для удаления");
            return;
        }
        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Удалить пользователя?", "Подтверждение", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            userDAO.deleteUser(userId);
            refreshTable();
        }
    }
}
