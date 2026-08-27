package com.sunrisedental.view;

import com.sunrisedental.model.User;
import javax.swing.*;
import java.awt.*;

/**
 * Main Dashboard Interface with Role-Based Feature Access.
 */
public class MainDashboardView extends JFrame {
    private final User currentUser;

    public MainDashboardView(User user) {
        this.currentUser = user;
        setTitle("Sunrise Dental Clinic - Main Dashboard [" + user.getRole() + "]");
        setSize(850, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initDashboard();
    }

    private void initDashboard() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(25, 118, 210));
        headerPanel.setPreferredSize(new Dimension(850, 70));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblWelcome = new JLabel("Sunrise Dental Clinic Management System");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblWelcome.setForeground(Color.WHITE);

        JLabel lblUserInfo = new JLabel("User: " + currentUser.getFullName() + " | Role: " + currentUser.getRole());
        lblUserInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUserInfo.setForeground(new Color(225, 245, 254));

        headerPanel.add(lblWelcome, BorderLayout.WEST);
        headerPanel.add(lblUserInfo, BorderLayout.EAST);

        JPanel gridPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        gridPanel.setBackground(new Color(245, 247, 250));

        JButton btnRegister = createTileButton("1. Register Appointment", new Color(46, 125, 50));
        JButton btnSearch = createTileButton("2. Search Appointment", new Color(2, 136, 209));
        JButton btnBilling = createTileButton("3. Calculate & Print Bill", new Color(245, 124, 0));
        JButton btnReports = createTileButton("4. Reports & Analytics", new Color(123, 31, 162));
        JButton btnHelp = createTileButton("5. Help & Staff Manual", new Color(0, 150, 136));
        JButton btnLogout = createTileButton("6. Logout", new Color(198, 40, 40));

        // Enforce Role Access Control
        if (!"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            btnReports.setEnabled(false);
            btnReports.setToolTipText("Admin access required for financial & clinic analytics reports.");
            btnReports.setBackground(Color.LIGHT_GRAY);
        }

        gridPanel.add(btnRegister);
        gridPanel.add(btnSearch);
        gridPanel.add(btnBilling);
        gridPanel.add(btnReports);
        gridPanel.add(btnHelp);
        gridPanel.add(btnLogout);

        add(headerPanel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);

        // Action Handlers
        btnRegister.addActionListener(e -> new RegisterAppointmentView(currentUser).setVisible(true));
        btnSearch.addActionListener(e -> new SearchAppointmentView().setVisible(true));
        btnBilling.addActionListener(e -> new BillingView().setVisible(true));
        btnReports.addActionListener(e -> new ReportsView().setVisible(true));
        btnHelp.addActionListener(e -> new HelpView().setVisible(true));
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
                new LoginView().setVisible(true);
            }
        });
    }

    private JButton createTileButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
