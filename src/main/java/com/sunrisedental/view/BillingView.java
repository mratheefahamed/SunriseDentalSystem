package com.sunrisedental.view;

import com.sunrisedental.factory.TreatmentFeeFactory;
import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Bill;
import com.sunrisedental.service.DentalService;
import com.sunrisedental.service.DentalServiceImpl;
import com.sunrisedental.util.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Modern High-Contrast Billing & Receipt Generation Interface.
 */
public class BillingView extends JFrame {
    private JTextField txtAppNo, txtConsultationFee, txtTreatmentFee, txtTotalAmount;
    private JTextArea txtReceipt;
    private JButton btnFetch, btnCalculate, btnPrint, btnClose;
    private final DentalService dentalService = new DentalServiceImpl();
    private Appointment currentAppointment = null;

    public BillingView() {
        setTitle("Calculate & Print Bill - Sunrise Dental Clinic");
        setSize(880, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(UITheme.BG_LIGHT);
        setLayout(new BorderLayout());
        initBillingUI();
    }

    private void initBillingUI() {
        // Top Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(15, 23, 42));
        headerPanel.setPreferredSize(new Dimension(880, 65));
        headerPanel.setBorder(new EmptyBorder(12, 25, 12, 25));

        JLabel lblTitle = new JLabel("Calculate & Generate Patient Receipt");
        lblTitle.setFont(UITheme.FONT_HEADER_MED);
        lblTitle.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel("Factory pattern automated treatment fee calculation & printable receipt");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(148, 163, 184));

        JPanel titleBlock = new JPanel(new GridLayout(2, 1));
        titleBlock.setOpaque(false);
        titleBlock.add(lblTitle);
        titleBlock.add(lblSub);

        headerPanel.add(titleBlock, BorderLayout.WEST);

        // Center Split Layout
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        centerPanel.setBackground(UITheme.BG_LIGHT);

        // Left Card - Calculation Form
        JPanel formCard = UITheme.createCardPanel();
        formCard.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtAppNo = new JTextField(8);
        UITheme.styleTextField(txtAppNo);

        btnFetch = UITheme.createPrimaryButton("Fetch APT");
        btnFetch.setBorder(new EmptyBorder(6, 12, 6, 12));

        txtConsultationFee = new JTextField("2500.00");
        UITheme.styleTextField(txtConsultationFee);

        txtTreatmentFee = new JTextField();
        txtTreatmentFee.setEditable(false);
        txtTreatmentFee.setBackground(new Color(241, 245, 249));
        UITheme.styleTextField(txtTreatmentFee);

        txtTotalAmount = new JTextField();
        txtTotalAmount.setEditable(false);
        txtTotalAmount.setBackground(new Color(254, 243, 199)); // Soft amber highlight
        txtTotalAmount.setFont(new Font("Segoe UI", Font.BOLD, 15));
        txtTotalAmount.setForeground(new Color(180, 83, 9));
        UITheme.styleTextField(txtTotalAmount);

        btnCalculate = UITheme.createSuccessButton("Calculate Total Bill");

        gbc.gridx = 0; gbc.gridy = 0;
        formCard.add(createLabel("Appointment No:"), gbc);

        JPanel fetchRow = new JPanel(new BorderLayout(8, 0));
        fetchRow.setOpaque(false);
        fetchRow.add(txtAppNo, BorderLayout.CENTER);
        fetchRow.add(btnFetch, BorderLayout.EAST);

        gbc.gridx = 1;
        formCard.add(fetchRow, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formCard.add(createLabel("Consultation Fee (LKR):"), gbc);
        gbc.gridx = 1;
        formCard.add(txtConsultationFee, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formCard.add(createLabel("Treatment Fee (LKR):"), gbc);
        gbc.gridx = 1;
        formCard.add(txtTreatmentFee, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formCard.add(createLabel("Total Amount (LKR):"), gbc);
        gbc.gridx = 1;
        formCard.add(txtTotalAmount, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 8, 8, 8);
        formCard.add(btnCalculate, gbc);

        // Right Card - Printable Invoice Receipt Preview
        JPanel receiptCard = UITheme.createCardPanel();
        receiptCard.setLayout(new BorderLayout());

        JLabel lblReceiptHeader = new JLabel("Printable Invoice Preview", JLabel.LEFT);
        lblReceiptHeader.setFont(UITheme.FONT_LABEL);
        lblReceiptHeader.setForeground(UITheme.TEXT_MAIN);
        lblReceiptHeader.setBorder(new EmptyBorder(0, 0, 10, 0));

        txtReceipt = new JTextArea();
        txtReceipt.setFont(UITheme.FONT_MONO);
        txtReceipt.setForeground(Color.BLACK); // Jet black readable ink text
        txtReceipt.setBackground(Color.WHITE);
        txtReceipt.setEditable(false);
        txtReceipt.setMargin(new Insets(12, 12, 12, 12));

        JScrollPane scrollReceipt = new JScrollPane(txtReceipt);
        scrollReceipt.setBorder(BorderFactory.createLineBorder(UITheme.BORDER, 1));

        receiptCard.add(lblReceiptHeader, BorderLayout.NORTH);
        receiptCard.add(scrollReceipt, BorderLayout.CENTER);

        centerPanel.add(formCard);
        centerPanel.add(receiptCard);

        // Bottom Actions Bar
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        bottomBar.setBackground(Color.WHITE);
        bottomBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER));

        btnPrint = UITheme.createPrimaryButton("Print / Save Receipt");
        btnClose = UITheme.createSecondaryButton("Close");

        bottomBar.add(btnPrint);
        bottomBar.add(btnClose);

        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomBar, BorderLayout.SOUTH);

        // Action Handlers
        btnFetch.addActionListener(e -> fetchAppointment());
        btnCalculate.addActionListener(e -> calculateTotal());
        btnPrint.addActionListener(e -> printReceipt());
        btnClose.addActionListener(e -> this.dispose());
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(UITheme.FONT_LABEL);
        lbl.setForeground(UITheme.TEXT_MAIN);
        return lbl;
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
        sb.append("           OFFICIAL PAYMENT RECEIPT       \n");
        sb.append("=========================================\n");
        sb.append("APT No       : ").append(currentAppointment.getAppointmentNo()).append("\n");
        sb.append("Patient Name : ").append(currentAppointment.getPatientName()).append("\n");
        sb.append("Contact No   : ").append(currentAppointment.getContactNumber()).append("\n");
        sb.append("Dentist Name : ").append(currentAppointment.getDentistName()).append("\n");
        sb.append("Treatment    : ").append(currentAppointment.getTreatmentType()).append("\n");
        sb.append("Date & Time  : ").append(currentAppointment.getAppointmentDate()).append(" ").append(currentAppointment.getAppointmentTime()).append("\n");
        sb.append("-----------------------------------------\n");
        sb.append(String.format("Consultation Fee : LKR %10.2f\n", consultationFee));
        sb.append(String.format("Treatment Base Fee: LKR %10.2f\n", treatmentFee));
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
                txtReceipt.print();
                JOptionPane.showMessageDialog(this, "Receipt saved and printed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Receipt Print Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
