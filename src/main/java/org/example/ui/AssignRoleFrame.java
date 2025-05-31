package org.example.ui;

import org.example.*;
import org.example.dao.RoleDAO;
import org.example.dao.UserDAO;
import org.example.dao.UserRoleDAO;
import org.example.model.Role;
import org.example.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AssignRoleFrame extends JFrame {
    private final UserDAO userDAO = new UserDAO();
    private final RoleDAO roleDAO = new RoleDAO();
    private final UserRoleDAO userRoleDAO = new UserRoleDAO();
    private final int _userId;
    private final AccessControlService _accessControlService = new AccessControlService();

    public AssignRoleFrame(int userId) {
        _userId = userId;

        setTitle("Назначение ролей пользователям");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        JComboBox<User> userComboBox = new JComboBox<>();
        JComboBox<Role> roleComboBox = new JComboBox<>();

        // Загружаем пользователей
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            userComboBox.addItem(user);
        }

        // Загружаем роли
        List<Role> roles = roleDAO.getAllRoles();
        for (Role role : roles) {
            roleComboBox.addItem(role);
        }

        JButton assignButton = new JButton("Назначить роль");

        assignButton.addActionListener(e -> {
            if (!_accessControlService.hasPermissionFor(_userId, "edit_roles")) {
                JOptionPane.showMessageDialog(this, "У вас нет прав для назначения ролей");
                return;
            }

            User selectedUser = (User) userComboBox.getSelectedItem();
            Role selectedRole = (Role) roleComboBox.getSelectedItem();

            userRoleDAO.assignRoleToUser(selectedUser.getId(), selectedRole.getId());
            JOptionPane.showMessageDialog(this, "Роль назначена успешно");
        });

        add(new JLabel("Выберите пользователя:"));
        add(userComboBox);
        add(new JLabel("Выберите роль:"));
        add(roleComboBox);
        add(assignButton);

        setVisible(true);
    }
}
