package com.sunrisedental.view;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.service.DentalService;
import com.sunrisedental.service.DentalServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Admin Reports & Financial Analytics Interface.
 * Value-addition report feature for clinic management.
 */
public class ReportsView extends JFrame {
    private JLabel lblTotalRevenue, lblTotalAppointments;
    private JTextArea txtSummaryReport;
    private JButton btnRefresh, btnClose;
    private final DentalService dentalService = new DentalServiceImpl();

    public ReportsView() {
        setTitle("Admin Financial & Clinic Analytics Reports - Sunrise Dental Clinic");
        setSize(750, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initReportsUI();
        loadAnalyticsData();
    }

    private void initReportsUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(123, 31, 162));
        JLabel lblHeader = new JLabel("Clinic Management & Revenue Analytics Summary");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHeader.setForeground(Color.WHITE);
        headerPanel.add(lblHeader);

        // Stats Cards Panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        statsPanel.setBackground(new Color(245, 247, 250));

        JPanel card1 = createStatCard("Total Accumulated Revenue", "LKR 0.00", new Color(123, 31, 162));
        lblTotalRevenue = (JLabel) card1.getComponent(1);

        JPanel card2 = createStatCard("Total Appointments Registered", "0 Appointments", new Color(2, 136, 209));
        lblTotalAppointments = (JLabel) card2.getComponent(1);

        statsPanel.add(card1);
        statsPanel.add(card2);

        // Report Text Panel
        txtSummaryReport = new JTextArea();
        txtSummaryReport.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtSummaryReport.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtSummaryReport);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daily Treatment & Dentist Breakdown Report"));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        btnRefresh = new JButton("Refresh Analytics");
        btnRefresh.setBackground(new Color(123, 31, 162));
        btnRefresh.setForeground(Color.WHITE);

        btnClose = new JButton("Close");
        btnRow.add(btnRefresh);
        btnRow.add(btnClose);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.add(statsPanel, BorderLayout.NORTH);
        centerContainer.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerContainer, BorderLayout.CENTER);
        mainPanel.add(btnRow, BorderLayout.SOUTH);

        add(mainPanel);

        // Actions
        btnRefresh.addActionListener(e -> loadAnalyticsData());
        btnClose.addActionListener(e -> this.dispose());
    }

    private JPanel createStatCard(String title, String initialValue, Color color) {
        JPanel card = new JPanel(new GridLayout(2, 1));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        card.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTitle.setForeground(Color.GRAY);

        JLabel lblValue = new JLabel(initialValue);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblValue.setForeground(color);

        card.add(lblTitle);
        card.add(lblValue);
        return card;
    }

    private void loadAnalyticsData() {
        try {
            double revenue = dentalService.getTotalClinicRevenue();
            List<Appointment> list = dentalService.getAllAppointments();

            lblTotalRevenue.setText(String.format("LKR %.2f", revenue));
            lblTotalAppointments.setText(list.size() + " Appointments");

            StringBuilder sb = new StringBuilder();
            sb.append("=========================================================================\n");
            sb.append("                 SUNRISE DENTAL CLINIC - MANAGEMENT REPORT               \n");
            sb.append("=========================================================================\n\n");
            sb.append(String.format("Total Recorded Appointments : %d\n", list.size()));
            sb.append(String.format("Total Billed Clinic Revenue : LKR %.2f\n\n", revenue));
            sb.append("Recent Appointments Breakdown:\n");
            sb.append("-------------------------------------------------------------------------\n");
            sb.append(String.format("%-10s | %-18s | %-20s | %-12s\n", "APT NO", "PATIENT NAME", "DENTIST NAME", "TREATMENT"));
            sb.append("-------------------------------------------------------------------------\n");

            for (Appointment app : list) {
                sb.append(String.format("%-10s | %-18s | %-20s | %-12s\n",
                    app.getAppointmentNo(),
                    truncate(app.getPatientName(), 18),
                    truncate(app.getDentistName(), 20),
                    truncate(app.getTreatmentType(), 12)
                ));
            }
            sb.append("=========================================================================\n");

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
