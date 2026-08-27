package com.sunrisedental.view;

import com.sunrisedental.model.User;
import com.sunrisedental.service.DentalService;
import com.sunrisedental.service.DentalServiceImpl;
import com.sunrisedental.util.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Modern High-Contrast Login Interface for Sunrise Dental Clinic.
 */
public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnClear, btnExit;
    private final DentalService dentalService = new DentalServiceImpl();

    public LoginView() {
        setTitle("Sunrise Dental Clinic - Staff & Admin Login");
        setSize(480, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(UITheme.BG_LIGHT);
        setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() {
        // Outer Container
        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(UITheme.BG_LIGHT);

        // Center Card Panel
        JPanel card = UITheme.createCardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(380, 400));

        // Header Title & Logo Text
        JLabel lblClinicIcon = new JLabel("• SUNRISE DENTAL CLINIC •", JLabel.CENTER);
        lblClinicIcon.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblClinicIcon.setForeground(UITheme.PRIMARY);
        lblClinicIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel("Welcome Back", JLabel.CENTER);
        lblTitle.setFont(UITheme.FONT_HEADER_LARGE);
        lblTitle.setForeground(UITheme.TEXT_MAIN);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Sign in with your authorized credentials", JLabel.CENTER);
        lblSubtitle.setFont(UITheme.FONT_SUBTITLE);
        lblSubtitle.setForeground(UITheme.TEXT_MUTED);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lblClinicIcon);
        card.add(Box.createRigidArea(new Dimension(0, 6)));
        card.add(lblTitle);
        card.add(Box.createRigidArea(new Dimension(0, 4)));
        card.add(lblSubtitle);
        card.add(Box.createRigidArea(new Dimension(0, 24)));

        // Username Field (Centered)
        JLabel lblUser = new JLabel("Username", JLabel.CENTER);
        lblUser.setFont(UITheme.FONT_LABEL);
        lblUser.setForeground(UITheme.TEXT_MAIN);
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtUsername = new JTextField();
        txtUsername.setHorizontalAlignment(JTextField.CENTER);
        UITheme.styleTextField(txtUsername);
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        card.add(lblUser);
        card.add(Box.createRigidArea(new Dimension(0, 6)));
        card.add(txtUsername);
        card.add(Box.createRigidArea(new Dimension(0, 14)));

        // Password Field (Centered)
        JLabel lblPass = new JLabel("Password", JLabel.CENTER);
        lblPass.setFont(UITheme.FONT_LABEL);
        lblPass.setForeground(UITheme.TEXT_MAIN);
        lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtPassword = new JPasswordField();
        txtPassword.setHorizontalAlignment(JTextField.CENTER);
        UITheme.styleTextField(txtPassword);
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        card.add(lblPass);
        card.add(Box.createRigidArea(new Dimension(0, 6)));
        card.add(txtPassword);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        // Buttons
        btnLogin = UITheme.createPrimaryButton("Sign In to Portal");
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonRow = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonRow.setOpaque(false);
        btnClear = UITheme.createSecondaryButton("Clear");
        btnExit = UITheme.createSecondaryButton("Exit");
        buttonRow.add(btnClear);
        buttonRow.add(btnExit);
        buttonRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        card.add(btnLogin);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(buttonRow);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        // Role hints
        JLabel lblHint = new JLabel("Default: admin / admin123 | staff1 / staff123", JLabel.CENTER);
        lblHint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblHint.setForeground(UITheme.TEXT_MUTED);
        lblHint.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblHint);

        outerPanel.add(card);
        add(outerPanel, BorderLayout.CENTER);

        // Action Handlers
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
            JOptionPane.showMessageDialog(this, "Database Connection Error: " + ex.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
