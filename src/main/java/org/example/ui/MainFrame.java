package org.example.ui;

import org.example.AccessControlService;
import org.example.ui.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final int _userId;
    private final AccessControlService _accessControlService = new AccessControlService();

    public MainFrame(int userId) {
        _userId = userId;

        setTitle("RBAC Admin Panel");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JButton manageUsersButton = new JButton("Управление пользователями");
        manageUsersButton.addActionListener(e -> {
            if (_accessControlService.hasPermissionFor(_userId, "view_users")) {
                new UserManagementFrame();
            } else {
                JOptionPane.showMessageDialog(this, "У вас нет доступа к управлению пользователями");
            }
        });

        JButton assignRolesButton = new JButton("Управление ролями пользователей");
        assignRolesButton.addActionListener(e -> new UserRolesManagementFrame());

        JButton assignPermissionsButton = new JButton("Управление правами ролей");
        assignPermissionsButton.addActionListener(e -> new RolePermissionsManagementFrame());

        JButton overviewButton = new JButton("Полный обзор RBAC");
        overviewButton.addActionListener(e -> new RBACOverviewFrame(_userId));

        JButton accessOverviewButton = new JButton("Обзор доступа пользователя");
        accessOverviewButton.addActionListener(e -> new UserAccessOverviewFrame());

        add(accessOverviewButton);
        add(manageUsersButton);
        add(assignRolesButton);
        add(assignPermissionsButton);
        add(overviewButton);

        /*
        Sad
        //кнокпка проверки прав текущего пользователя
        JButton debugButton = new JButton("Права пользователя");
        debugButton.addActionListener(e -> new PermissionDebugFrame(_userId));
        add(debugButton);*/

        setVisible(true);
    }
}



