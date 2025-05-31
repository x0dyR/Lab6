package org.example.ui;

import org.example.AccessControlService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final int _userId;
    private final AccessControlService _accessControlService = new AccessControlService();

    public MainFrame(int userId) {
        _userId = userId;

        setTitle("RBAC Admin Panel");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        if (_accessControlService.hasPermissionFor(_userId, "view_users")) {
            JButton manageUsersButton = new JButton("Управление пользователями");
            manageUsersButton.addActionListener(e -> new UserManagementFrame());
            add(manageUsersButton);
        }

        if (_accessControlService.hasPermissionFor(_userId, "manage_user_roles")) {
            JButton assignRolesButton = new JButton("Управление ролями пользователей");
            assignRolesButton.addActionListener(e -> new UserRolesManagementFrame());
            add(assignRolesButton);
        }

        if (_accessControlService.hasPermissionFor(_userId, "manage_role_permissions")) {
            JButton assignPermissionsButton = new JButton("Управление правами ролей");
            assignPermissionsButton.addActionListener(e -> new RolePermissionsManagementFrame());
            add(assignPermissionsButton);
        }

        if (_accessControlService.hasPermissionFor(_userId, "rbac_overview")) {
            JButton overviewButton = new JButton("Полный обзор RBAC");
            overviewButton.addActionListener(e -> new RBACOverviewFrame(_userId));
            add(overviewButton);
        }

        if (_accessControlService.hasPermissionFor(_userId, "user_access_overview")) {
            JButton accessOverviewButton = new JButton("Обзор доступа пользователя");
            accessOverviewButton.addActionListener(e -> new UserAccessOverviewFrame());
            add(accessOverviewButton);
        }

        // Заказы (основной функционал)

        if (_accessControlService.hasPermissionFor(_userId, "place_orders")) {
            JButton placeOrderButton = new JButton("Создать заказ");
            placeOrderButton.addActionListener(e -> new PlaceOrderFrame(_userId));
            add(placeOrderButton);
        }

        if (_accessControlService.hasPermissionFor(_userId, "process_orders")) {
            JButton processOrderButton = new JButton("Обслуживание заказов");
            processOrderButton.addActionListener(e -> new ProcessOrdersFrame());
            add(processOrderButton);
        }

        setVisible(true);
    }
}


/*
        Sad
        //кнокпка проверки прав текущего пользователя
        JButton debugButton = new JButton("Права пользователя");
        debugButton.addActionListener(e -> new PermissionDebugFrame(_userId));
        add(debugButton);*/


