package com.sunrisedental.view;

import com.sunrisedental.util.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Modern High-Contrast Staff Operating Manual & Help Interface.
 */
public class HelpView extends JFrame {

    public HelpView() {
        setTitle("Staff Operating Manual & Help - Sunrise Dental Clinic");
        setSize(780, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(UITheme.BG_LIGHT);
        setLayout(new BorderLayout());
        initHelpUI();
    }

    private void initHelpUI() {
        // Top Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(15, 23, 42));
        headerPanel.setPreferredSize(new Dimension(780, 65));
        headerPanel.setBorder(new EmptyBorder(12, 25, 12, 25));

        JLabel lblTitle = new JLabel("Staff User Manual & Operating Instructions");
        lblTitle.setFont(UITheme.FONT_HEADER_MED);
        lblTitle.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel("Step-by-step guidance for new reception and administration personnel");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(148, 163, 184));

        JPanel titleBlock = new JPanel(new GridLayout(2, 1));
        titleBlock.setOpaque(false);
        titleBlock.add(lblTitle);
        titleBlock.add(lblSub);

        headerPanel.add(titleBlock, BorderLayout.WEST);

        // Center Manual Card
        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.setBackground(UITheme.BG_LIGHT);
        centerContainer.setBorder(new EmptyBorder(20, 25, 15, 25));

        JPanel manualCard = UITheme.createCardPanel();
        manualCard.setLayout(new BorderLayout());

        JTextArea txtHelp = new JTextArea();
        txtHelp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtHelp.setForeground(UITheme.TEXT_MAIN); // High contrast dark slate text
        txtHelp.setBackground(Color.WHITE);
        txtHelp.setEditable(false);
        txtHelp.setLineWrap(true);
        txtHelp.setWrapStyleWord(true);
        txtHelp.setMargin(new Insets(15, 15, 15, 15));

        StringBuilder sb = new StringBuilder();
        sb.append("Welcome to the Sunrise Dental Clinic Management System!\n\n");
        sb.append("This computerized system eliminates manual notebook workflows, prevents double bookings, streamlines billing, and securely records patient visits.\n\n");
        sb.append("═══════════════════════════════════════════════════════════════════════════════════════\n");
        sb.append("1. USER AUTHENTICATION & ROLE ACCESS\n");
        sb.append("   • System access requires a verified username and password.\n");
        sb.append("   • STAFF Role: Can register appointments, search records, and calculate/print bills.\n");
        sb.append("   • ADMIN Role: Exclusive access to financial analytics and revenue reports.\n\n");
        sb.append("2. REGISTERING A NEW APPOINTMENT\n");
        sb.append("   • Click '1. Register Appointment' on the main dashboard.\n");
        sb.append("   • Unique Appointment Number (e.g. APT-1001) is automatically generated.\n");
        sb.append("   • Fill in patient details: Name, Address, Contact (e.g. 0771234567), Assigned Dentist, Treatment Type, Date (YYYY-MM-DD), and Time (HH:mm).\n");
        sb.append("   • Double Booking Alert: The system will automatically prevent saving if the chosen dentist is already booked at that date and time.\n\n");
        sb.append("3. SEARCHING & DISPLAYING APPOINTMENTS\n");
        sb.append("   • Click '2. Search Appointments' to look up an appointment by its ID.\n");
        sb.append("   • Click 'Show All Records' to view all scheduled visits in a tabular format.\n\n");
        sb.append("4. CALCULATING AND PRINTING BILLS / RECEIPTS\n");
        sb.append("   • Click '3. Billing & Receipts' from the dashboard.\n");
        sb.append("   • Enter the Appointment Number and click 'Fetch APT'.\n");
        sb.append("   • The Factory Pattern automatically populates the treatment base fee.\n");
        sb.append("   • Click 'Calculate Total Bill' to compute Consultation + Treatment fees.\n");
        sb.append("   • Click 'Print / Save Receipt' to issue an official receipt and save it to the database.\n\n");
        sb.append("5. EXITING THE SYSTEM\n");
        sb.append("   • Use the 'Logout' button on the dashboard or 'Exit System' to safely terminate your session.\n");

        txtHelp.setText(sb.toString());

        JScrollPane scrollPane = new JScrollPane(txtHelp);
        scrollPane.setBorder(BorderFactory.createLineBorder(UITheme.BORDER, 1));

        manualCard.add(scrollPane, BorderLayout.CENTER);
        centerContainer.add(manualCard, BorderLayout.CENTER);

        // Bottom Bar
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        bottomBar.setBackground(Color.WHITE);
        bottomBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER));

        JButton btnClose = UITheme.createSecondaryButton("Close Manual");
        btnClose.addActionListener(e -> this.dispose());
        bottomBar.add(btnClose);

        add(headerPanel, BorderLayout.NORTH);
        add(centerContainer, BorderLayout.CENTER);
        add(bottomBar, BorderLayout.SOUTH);
    }
}
