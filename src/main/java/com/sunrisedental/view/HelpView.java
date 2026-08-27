package com.sunrisedental.view;

import javax.swing.*;
import java.awt.*;

/**
 * Help Section Interface providing operating manual for clinic reception staff.
 */
public class HelpView extends JFrame {

    public HelpView() {
        setTitle("Staff Operating Manual & System Help - Sunrise Dental Clinic");
        setSize(700, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initHelpUI();
    }

    private void initHelpUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 150, 136));
        JLabel lblHeader = new JLabel("Staff User Manual & Operating Instructions");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHeader.setForeground(Color.WHITE);
        headerPanel.add(lblHeader);

        JTextArea txtHelp = new JTextArea();
        txtHelp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtHelp.setEditable(false);
        txtHelp.setLineWrap(true);
        txtHelp.setWrapStyleWord(true);
        txtHelp.setMargin(new Insets(15, 15, 15, 15));

        StringBuilder sb = new StringBuilder();
        sb.append("Welcome to the Sunrise Dental Clinic Management System!\n\n");
        sb.append("This system helps reception staff and administrators manage patient appointments, treatment billing, and clinic records efficiently.\n\n");
        sb.append("---------------------------------------------------------------------------------------------------------\n");
        sb.append("1. USER AUTHENTICATION & LOGIN\n");
        sb.append("   - Log in using your assigned Staff or Admin credentials.\n");
        sb.append("   - Staff accounts can register appointments, search records, and issue bills.\n");
        sb.append("   - Admin accounts have exclusive access to financial reports and analytics.\n\n");
        sb.append("2. REGISTERING A NEW PATIENT APPOINTMENT\n");
        sb.append("   - Click '1. Register Appointment' from the main dashboard.\n");
        sb.append("   - The system automatically generates a unique Appointment Number (e.g. APT-1001).\n");
        sb.append("   - Fill in mandatory patient details: Name, Address, Contact (e.g. 0771234567), Dentist, Treatment, Date, and Time.\n");
        sb.append("   - Double Booking Protection: The system will alert you if the selected dentist is already booked at that date and time.\n\n");
        sb.append("3. SEARCHING & DISPLAYING APPOINTMENT DETAILS\n");
        sb.append("   - Click '2. Search Appointment' from the dashboard.\n");
        sb.append("   - Enter an Appointment Number and click 'Search' to view full patient details.\n");
        sb.append("   - Click 'Show All' to view all registered appointments.\n\n");
        sb.append("4. CALCULATING AND PRINTING PATIENT BILLS\n");
        sb.append("   - Click '3. Calculate & Print Bill' from the dashboard.\n");
        sb.append("   - Enter the Appointment Number and click 'Fetch APT'.\n");
        sb.append("   - The system uses the Factory Pattern to auto-fill treatment base fees.\n");
        sb.append("   - Click 'Calculate Total' and then 'Print / Save Receipt' to issue an official receipt.\n\n");
        sb.append("5. EXITING THE SYSTEM\n");
        sb.append("   - Use 'Logout' on the dashboard or exit button on the login screen to close the app safely.\n");

        txtHelp.setText(sb.toString());

        JScrollPane scrollPane = new JScrollPane(txtHelp);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton btnClose = new JButton("Close Manual");
        btnClose.setBackground(new Color(0, 150, 136));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnClose.addActionListener(e -> this.dispose());
        btnRow.add(btnClose);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(btnRow, BorderLayout.SOUTH);

        add(mainPanel);
    }
}
