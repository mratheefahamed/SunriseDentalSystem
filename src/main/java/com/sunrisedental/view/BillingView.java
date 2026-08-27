package com.sunrisedental.view;

import com.sunrisedental.factory.TreatmentFeeFactory;
import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Bill;
import com.sunrisedental.service.DentalService;
import com.sunrisedental.service.DentalServiceImpl;

import javax.swing.*;
import java.awt.*;

/**
 * Interface to Calculate & Print Patient Treatment Bill / Receipt.
 */
public class BillingView extends JFrame {
    private JTextField txtAppNo, txtConsultationFee, txtTreatmentFee, txtTotalAmount;
    private JTextArea txtReceipt;
    private JButton btnFetch, btnCalculate, btnPrint, btnClose;
    private final DentalService dentalService = new DentalServiceImpl();
    private Appointment currentAppointment = null;

    public BillingView() {
        setTitle("Calculate & Print Patient Bill - Sunrise Dental Clinic");
        setSize(780, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initBillingUI();
    }

    private void initBillingUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(245, 124, 0));
        JLabel lblHeader = new JLabel("Calculate & Generate Patient Receipt");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHeader.setForeground(Color.WHITE);
        headerPanel.add(lblHeader);

        // Left Panel - Calculation Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Billing Details"));
        formPanel.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtAppNo = new JTextField(10);
        txtConsultationFee = new JTextField("2500.00", 10); // Standard consultation fee
        txtTreatmentFee = new JTextField(10);
        txtTreatmentFee.setEditable(false);
        txtTotalAmount = new JTextField(10);
        txtTotalAmount.setEditable(false);

        btnFetch = new JButton("Fetch APT");
        btnFetch.setBackground(new Color(245, 124, 0));
        btnFetch.setForeground(Color.WHITE);

        btnCalculate = new JButton("Calculate Total");
        btnCalculate.setFont(new Font("Segoe UI", Font.BOLD, 12));

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("APT Number:"), gbc);
        
        JPanel fetchRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        fetchRow.setOpaque(false);
        fetchRow.add(txtAppNo);
        fetchRow.add(btnFetch);

        gbc.gridx = 1;
        formPanel.add(fetchRow, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Consultation Fee (LKR):"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtConsultationFee, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Treatment Fee (LKR):"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtTreatmentFee, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Total Amount (LKR):"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtTotalAmount, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        formPanel.add(btnCalculate, gbc);

        // Right Panel - Receipt Preview
        JPanel receiptPanel = new JPanel(new BorderLayout());
        receiptPanel.setBorder(BorderFactory.createTitledBorder("Printable Receipt Preview"));
        txtReceipt = new JTextArea();
        txtReceipt.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtReceipt.setEditable(false);
        receiptPanel.add(new JScrollPane(txtReceipt), BorderLayout.CENTER);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        btnPrint = new JButton("Print / Save Receipt");
        btnPrint.setBackground(new Color(46, 125, 50));
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setFont(new Font("Segoe UI", Font.BOLD, 12));

        btnClose = new JButton("Close");
        btnRow.add(btnPrint);
        btnRow.add(btnClose);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, receiptPanel);
        splitPane.setDividerLocation(360);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);
        mainPanel.add(btnRow, BorderLayout.SOUTH);

        add(mainPanel);

        // Actions
        btnFetch.addActionListener(e -> fetchAppointment());
        btnCalculate.addActionListener(e -> calculateTotal());
        btnPrint.addActionListener(e -> printReceipt());
        btnClose.addActionListener(e -> this.dispose());
    }

    private void fetchAppointment() {
        String appNo = txtAppNo.getText().trim();
        if (appNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Appointment Number.", "Validation Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        currentAppointment = dentalService.getAppointment(appNo);
        if (currentAppointment != null) {
            double baseFee = TreatmentFeeFactory.getTreatmentBaseFee(currentAppointment.getTreatmentType());
            txtTreatmentFee.setText(String.format("%.2f", baseFee));
            calculateTotal();
        } else {
            JOptionPane.showMessageDialog(this, "No appointment record found for " + appNo, "Not Found", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateTotal() {
        if (currentAppointment == null) {
            fetchAppointment();
            if (currentAppointment == null) return;
        }

        try {
            double consultationFee = Double.parseDouble(txtConsultationFee.getText().trim());
            double total = dentalService.calculateBill(currentAppointment.getTreatmentType(), consultationFee);
            double treatmentFee = total - consultationFee;

            txtTreatmentFee.setText(String.format("%.2f", treatmentFee));
            txtTotalAmount.setText(String.format("%.2f", total));

            generateReceiptText(consultationFee, treatmentFee, total);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid consultation fee amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateReceiptText(double consultationFee, double treatmentFee, double total) {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("       SUNRISE DENTAL CLINIC - COLOMBO    \n");
        sb.append("           PATIENT PAYMENT RECEIPT        \n");
        sb.append("=========================================\n");
        sb.append("APT No       : ").append(currentAppointment.getAppointmentNo()).append("\n");
        sb.append("Patient Name : ").append(currentAppointment.getPatientName()).append("\n");
        sb.append("Contact No   : ").append(currentAppointment.getContactNumber()).append("\n");
        sb.append("Dentist Name : ").append(currentAppointment.getDentistName()).append("\n");
        sb.append("Treatment    : ").append(currentAppointment.getTreatmentType()).append("\n");
        sb.append("Date & Time  : ").append(currentAppointment.getAppointmentDate()).append(" ").append(currentAppointment.getAppointmentTime()).append("\n");
        sb.append("-----------------------------------------\n");
        sb.append(String.format("Consultation Fee : LKR %10.2f\n", consultationFee));
        sb.append(String.format("Treatment Fee    : LKR %10.2f\n", treatmentFee));
        sb.append("-----------------------------------------\n");
        sb.append(String.format("TOTAL AMOUNT     : LKR %10.2f\n", total));
        sb.append("=========================================\n");
        sb.append("         Thank you for visiting!         \n");
        sb.append("=========================================\n");

        txtReceipt.setText(sb.toString());
    }

    private void printReceipt() {
        if (currentAppointment == null || txtTotalAmount.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please calculate total bill first.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double consultationFee = Double.parseDouble(txtConsultationFee.getText().trim());
            double total = Double.parseDouble(txtTotalAmount.getText().trim());
            double treatmentFee = total - consultationFee;

            Bill bill = new Bill(currentAppointment.getAppointmentNo(), consultationFee, treatmentFee, total);
            boolean saved = dentalService.saveBill(bill);

            if (saved) {
                txtReceipt.print(); // Triggers Swing printable receipt dialog
                JOptionPane.showMessageDialog(this, "Receipt saved and printed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Receipt Print Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
