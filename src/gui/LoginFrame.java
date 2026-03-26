package gui;

import exception.LoginFailedException;
import model.User;
import service.AuthService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;
    private final AuthService authService;

    public LoginFrame() {
        this.authService = new AuthService();
        setTitle("Online Examination - Login");
        setSize(400, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        registerEvents();
    }

    private void initComponents() {
        setLayout(new GridLayout(4, 2, 10, 10));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        roleComboBox = new JComboBox<>(new String[]{"STUDENT", "ADMIN"});
        loginButton = new JButton("Login");

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Role:"));
        add(roleComboBox);
        add(new JLabel());
        add(loginButton);
    }

    private void registerEvents() {
        loginButton.addActionListener(e -> performLogin());
    }

    private void performLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        try {
            User user = authService.login(username, password, role);
            JOptionPane.showMessageDialog(this, "Welcome, " + user.getUsername() + "!");
            openDashboard(user);
        } catch (LoginFailedException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openDashboard(User user) {
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            new AdminDashboard().setVisible(true);
        } else {
            new StudentDashboard(user.getUsername()).setVisible(true);
        }
        dispose();
    }
}
