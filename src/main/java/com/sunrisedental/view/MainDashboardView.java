package com.sunrisedental.view;

import com.sunrisedental.model.User;
import com.sunrisedental.util.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Modern Role-Based Dashboard Interface for Sunrise Dental Clinic.
 */
public class MainDashboardView extends JFrame {
    private final User currentUser;

    public MainDashboardView(User user) {
        this.currentUser = user;
        setTitle("Sunrise Dental Clinic - Main Dashboard [" + user.getRole() + "]");
        setSize(900, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(UITheme.BG_LIGHT);
        setLayout(new BorderLayout());
        initDashboard();
    }

    private void initDashboard() {
        // Top Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(15, 23, 42)); // Dark Slate Header
        headerPanel.setPreferredSize(new Dimension(900, 75));
        headerPanel.setBorder(new EmptyBorder(12, 25, 12, 25));

        JPanel titleBlock = new JPanel(new GridLayout(2, 1, 0, 2));
        titleBlock.setOpaque(false);

        JLabel lblClinic = new JLabel("Sunrise Dental Clinic - Colombo");
        lblClinic.setFont(UITheme.FONT_HEADER_MED);
        lblClinic.setForeground(Color.WHITE);

        JLabel lblTagline = new JLabel("Computerized Patient & Appointment Management System");
        lblTagline.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTagline.setForeground(new Color(148, 163, 184));

        titleBlock.add(lblClinic);
        titleBlock.add(lblTagline);

        // User info tag
        JPanel userBadge = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        userBadge.setOpaque(false);

        JLabel lblUser = new JLabel("Logged in as: " + currentUser.getFullName() + " (" + currentUser.getRole() + ")");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUser.setForeground(new Color(224, 242, 254));

        JButton btnLogoutTop = UITheme.createDangerButton("Logout");
        btnLogoutTop.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogoutTop.setBorder(new EmptyBorder(6, 14, 6, 14));

        userBadge.add(lblUser);
        userBadge.add(btnLogoutTop);

        headerPanel.add(titleBlock, BorderLayout.WEST);
        headerPanel.add(userBadge, BorderLayout.EAST);

        // Center Action Tiles
        JPanel gridPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        gridPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        gridPanel.setBackground(UITheme.BG_LIGHT);

        JButton btnRegister = createActionCard("1. Register Appointment", "Book new patient visit & select dentist", UITheme.SUCCESS, "➕");
        JButton btnSearch = createActionCard("2. Search Appointments", "Search by APT No or view full directory", UITheme.PRIMARY, "🔍");
        JButton btnBilling = createActionCard("3. Billing & Receipts", "Calculate treatment fee & print invoice", UITheme.WARNING, "💳");
        JButton btnReports = createActionCard("4. Financial Reports", "View clinic revenue & patient statistics", new Color(124, 58, 237), "📊");
        JButton btnHelp = createActionCard("5. Help & Manual", "Operating instructions for clinic staff", new Color(13, 148, 136), "❓");
        JButton btnExitApp = createActionCard("6. Exit System", "Safely close and terminate the session", UITheme.DANGER, "🚪");

        // Role-based Access Rules
        if (!"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            btnReports.setEnabled(false);
            btnReports.setBackground(new Color(241, 245, 249));
            btnReports.setToolTipText("Admin access required for financial analytics.");
        }

        gridPanel.add(btnRegister);
        gridPanel.add(btnSearch);
        gridPanel.add(btnBilling);
        gridPanel.add(btnReports);
        gridPanel.add(btnHelp);
        gridPanel.add(btnExitApp);

        // Bottom Status Bar
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(Color.WHITE);
        statusBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER),
            new EmptyBorder(8, 25, 8, 25)
        ));

        JLabel lblStatus = new JLabel("● System Online | Database: sunrise_dental_db | Web Service REST API Port: 8080");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblStatus.setForeground(UITheme.SUCCESS_DARK);

        statusBar.add(lblStatus, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        // Actions
        btnRegister.addActionListener(e -> new RegisterAppointmentView(currentUser).setVisible(true));
        btnSearch.addActionListener(e -> new SearchAppointmentView().setVisible(true));
        btnBilling.addActionListener(e -> new BillingView().setVisible(true));
        btnReports.addActionListener(e -> new ReportsView().setVisible(true));
        btnHelp.addActionListener(e -> new HelpView().setVisible(true));
        btnLogoutTop.addActionListener(e -> handleLogout());
        btnExitApp.addActionListener(e -> handleExit());
    }

    private JButton createActionCard(String title, String subtitle, Color themeColor, String icon) {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (isEnabled()) {
                    if (getModel().isPressed()) {
                        g2.setColor(new Color(241, 245, 249));
                    } else if (getModel().isRollover()) {
                        g2.setColor(new Color(248, 250, 252));
                    } else {
                        g2.setColor(Color.WHITE);
                    }
                } else {
                    g2.setColor(new Color(241, 245, 249));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

                // Draw colored accent top border
                if (isEnabled()) {
                    g2.setColor(themeColor);
                    g2.fillRoundRect(0, 0, getWidth(), 6, 6, 6);
                }

                g2.setColor(UITheme.BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setLayout(new BoxLayout(btn, BoxLayout.Y_AXIS));
        btn.setBorder(new EmptyBorder(16, 18, 16, 18));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        lblIcon.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(UITheme.TEXT_MAIN);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDesc = new JLabel("<html><p style='width:180px;'>" + subtitle + "</p></html>");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDesc.setForeground(UITheme.TEXT_MUTED);
        lblDesc.setAlignmentX(Component.LEFT_ALIGNMENT);

        btn.add(lblIcon);
        btn.add(Box.createRigidArea(new Dimension(0, 8)));
        btn.add(lblTitle);
        btn.add(Box.createRigidArea(new Dimension(0, 4)));
        btn.add(lblDesc);

        return btn;
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            new LoginView().setVisible(true);
        }
    }

    private void handleExit() {
        int confirm = JOptionPane.showConfirmDialog(this, "Exit the Sunrise Dental Clinic system?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
