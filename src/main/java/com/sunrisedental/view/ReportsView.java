package com.sunrisedental.view;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.service.DentalService;
import com.sunrisedental.service.DentalServiceImpl;
import com.sunrisedental.util.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * Modern High-Contrast Admin Financial & Analytics Report Interface.
 */
public class ReportsView extends JFrame {
    private JLabel lblTotalRevenue, lblTotalAppointments;
    private JTextArea txtSummaryReport;
    private JButton btnRefresh, btnClose;
    private final DentalService dentalService = new DentalServiceImpl();

    public ReportsView() {
        setTitle("Financial Reports & Analytics - Sunrise Dental Clinic");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(UITheme.BG_LIGHT);
        setLayout(new BorderLayout());
        initReportsUI();
        loadAnalyticsData();
    }

    private void initReportsUI() {
        // Top Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(15, 23, 42));
        headerPanel.setPreferredSize(new Dimension(850, 65));
        headerPanel.setBorder(new EmptyBorder(12, 25, 12, 25));

        JLabel lblTitle = new JLabel("Clinic Management & Revenue Analytics Summary");
        lblTitle.setFont(UITheme.FONT_HEADER_MED);
        lblTitle.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel("Real-time revenue calculation & appointment performance reporting");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(148, 163, 184));

        JPanel titleBlock = new JPanel(new GridLayout(2, 1));
        titleBlock.setOpaque(false);
        titleBlock.add(lblTitle);
        titleBlock.add(lblSub);

        headerPanel.add(titleBlock, BorderLayout.WEST);

        // Stats Cards Panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        statsPanel.setBorder(new EmptyBorder(20, 25, 15, 25));
        statsPanel.setBackground(UITheme.BG_LIGHT);

        JPanel card1 = createStatCard("TOTAL BILLED REVENUE", "LKR 0.00", new Color(124, 58, 237));
        lblTotalRevenue = (JLabel) card1.getComponent(1);

        JPanel card2 = createStatCard("RECORDED APPOINTMENTS", "0 Visits", new Color(14, 116, 144));
        lblTotalAppointments = (JLabel) card2.getComponent(1);

        statsPanel.add(card1);
        statsPanel.add(card2);

        // Report Text Card
        JPanel reportCardContainer = new JPanel(new BorderLayout());
        reportCardContainer.setBackground(UITheme.BG_LIGHT);
        reportCardContainer.setBorder(new EmptyBorder(0, 25, 15, 25));

        JPanel reportCard = UITheme.createCardPanel();
        reportCard.setLayout(new BorderLayout());

        JLabel lblReportTitle = new JLabel("Treatment & Dentist Activity Log", JLabel.LEFT);
        lblReportTitle.setFont(UITheme.FONT_LABEL);
        lblReportTitle.setForeground(UITheme.TEXT_MAIN);
        lblReportTitle.setBorder(new EmptyBorder(0, 0, 8, 0));

        txtSummaryReport = new JTextArea();
        txtSummaryReport.setFont(UITheme.FONT_MONO);
        txtSummaryReport.setForeground(Color.BLACK); // Jet-black readable ink
        txtSummaryReport.setBackground(Color.WHITE);
        txtSummaryReport.setEditable(false);
        txtSummaryReport.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(txtSummaryReport);
        scrollPane.setBorder(BorderFactory.createLineBorder(UITheme.BORDER, 1));

        reportCard.add(lblReportTitle, BorderLayout.NORTH);
        reportCard.add(scrollPane, BorderLayout.CENTER);

        reportCardContainer.add(reportCard, BorderLayout.CENTER);

        // Bottom Actions Bar
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        bottomBar.setBackground(Color.WHITE);
        bottomBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER));

        btnRefresh = UITheme.createPrimaryButton("Refresh Analytics");
        btnClose = UITheme.createSecondaryButton("Close");

        bottomBar.add(btnRefresh);
        bottomBar.add(btnClose);

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.add(statsPanel, BorderLayout.NORTH);
        centerContainer.add(reportCardContainer, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(centerContainer, BorderLayout.CENTER);
        add(bottomBar, BorderLayout.SOUTH);

        // Action Handlers
        btnRefresh.addActionListener(e -> loadAnalyticsData());
        btnClose.addActionListener(e -> this.dispose());
    }

    private JPanel createStatCard(String title, String initialValue, Color accentColor) {
        JPanel card = UITheme.createCardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTitle.setForeground(UITheme.TEXT_MUTED);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblValue = new JLabel(initialValue);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblValue.setForeground(accentColor);
        lblValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(lblTitle);
        card.add(Box.createRigidArea(new Dimension(0, 6)));
        card.add(lblValue);
        return card;
    }

    private void loadAnalyticsData() {
        try {
            double revenue = dentalService.getTotalClinicRevenue();
            List<Appointment> list = dentalService.getAllAppointments();

            lblTotalRevenue.setText(String.format("LKR %,.2f", revenue));
            lblTotalAppointments.setText(list.size() + " Visits");

            StringBuilder sb = new StringBuilder();
            sb.append("=====================================================================================\n");
            sb.append("                       SUNRISE DENTAL CLINIC - EXECUTIVE REPORT                      \n");
            sb.append("=====================================================================================\n\n");
            sb.append(String.format("Total Recorded Appointments : %d visits\n", list.size()));
            sb.append(String.format("Total Billed Clinic Revenue : LKR %,.2f\n\n", revenue));
            sb.append("Recent Patient Appointments Log:\n");
            sb.append("-------------------------------------------------------------------------------------\n");
            sb.append(String.format("%-10s | %-20s | %-25s | %-15s\n", "APT NO", "PATIENT NAME", "DENTIST NAME", "TREATMENT"));
            sb.append("-------------------------------------------------------------------------------------\n");

            for (Appointment app : list) {
                sb.append(String.format("%-10s | %-20s | %-25s | %-15s\n",
                    app.getAppointmentNo(),
                    truncate(app.getPatientName(), 20),
                    truncate(app.getDentistName(), 25),
                    truncate(app.getTreatmentType(), 15)
                ));
            }
            sb.append("=====================================================================================\n");

            txtSummaryReport.setText(sb.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load report analytics: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String truncate(String text, int length) {
        if (text == null) return "";
        return text.length() <= length ? text : text.substring(0, length - 2) + "..";
    }
}
