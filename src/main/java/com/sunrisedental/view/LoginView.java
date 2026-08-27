package com.sunrisedental.view;

import com.sunrisedental.model.User;
import com.sunrisedental.service.DentalService;
import com.sunrisedental.service.DentalServiceImpl;

import javax.swing.*;
import java.awt.*;

/**
 * Login Interface for Sunrise Dental Clinic System.
 * Supports Admin and Staff authentication.
 */
public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnClear, btnExit;
    private final DentalService dentalService = new DentalServiceImpl();

    public LoginView() {
        setTitle("Sunrise Dental Clinic - User Authentication");
        setSize(460, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("Sunrise Dental Clinic Login", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(25, 118, 210));

        JLabel lblSubtitle = new JLabel("Enter authorized Admin or Staff credentials", JLabel.CENTER);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitle.setForeground(Color.GRAY);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        gbc.gridy = 1;
        panel.add(lblSubtitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel.add(lblUser, gbc);

        txtUsername = new JTextField(16);
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel.add(lblPass, gbc);

        txtPassword = new JPasswordField(16);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogin.setBackground(new Color(25, 118, 210));
        btnLogin.setForeground(Color.WHITE);

        btnClear = new JButton("Clear");
        btnClear.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(btnLogin);
        btnPanel.add(btnClear);
        btnPanel.add(btnExit);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(btnPanel, gbc);

        add(panel);

        // Action Listeners
        btnLogin.addActionListener(e -> performLogin());
        btnClear.addActionListener(e -> {
            txtUsername.setText("");
            txtPassword.setText("");
            txtUsername.requestFocus();
        });
        btnExit.addActionListener(e -> System.exit(0));
    }

    private void performLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Username and Password.", "Validation Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            User user = dentalService.login(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login Successful!\nWelcome " + user.getFullName() + " (" + user.getRole() + ")", "Authentication Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new MainDashboardView(user).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password.", "Authentication Failed", JOptionPane.ERROR_MESSAGE);
                txtPassword.setText("");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error during login: " + ex.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
