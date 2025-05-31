package org.example.ui;

import org.example.dao.UserDAO;
import org.example.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginFrame extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final UserDAO userDAO = new UserDAO();

    public LoginFrame() {
        setTitle("RBAC Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        add(loginButton);

        JButton registerButton = new JButton("Register");
        add(registerButton);

        loginButton.addActionListener(new LoginAction());
        registerButton.addActionListener(new RegisterAction());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class LoginAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            List<User> users = userDAO.getAllUsers();
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(null, "Welcome " + username);
                    dispose(); // закрываем окно логина
                    new MainFrame(user.getId()); // ПЕРЕДАЕМ userId в MainFrame
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Invalid credentials");
        }
    }

    private class RegisterAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            userDAO.addUser(new User(username, password));
            JOptionPane.showMessageDialog(null, "User registered");
        }
    }
}
