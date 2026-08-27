package com.sunrisedental.view;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.User;
import com.sunrisedental.service.DentalService;
import com.sunrisedental.service.DentalServiceImpl;
import com.sunrisedental.util.InputValidator;
import com.sunrisedental.util.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;

/**
 * Modern High-Contrast Appointment Registration Form.
 */
public class RegisterAppointmentView extends JFrame {
    private JTextField txtAppNo, txtPatientName, txtAddress, txtContact, txtDate, txtTime;
    private JComboBox<String> cbDentist, cbTreatment;
    private JButton btnSave, btnClear, btnClose;
    private final DentalService dentalService = new DentalServiceImpl();
    private final User currentUser;

    public RegisterAppointmentView(User user) {
        this.currentUser = user;
        setTitle("Register Patient Appointment - Sunrise Dental Clinic");
        setSize(650, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(UITheme.BG_LIGHT);
        setLayout(new BorderLayout());
        initForm();
        loadNextAppointmentNo();
    }

    private void initForm() {
        // Top Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(15, 23, 42));
        headerPanel.setPreferredSize(new Dimension(650, 65));
        headerPanel.setBorder(new EmptyBorder(12, 25, 12, 25));

        JLabel lblTitle = new JLabel("Register New Patient Appointment");
        lblTitle.setFont(UITheme.FONT_HEADER_MED);
        lblTitle.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel("Automatic double-booking protection enabled");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(148, 163, 184));

        JPanel titleBlock = new JPanel(new GridLayout(2, 1));
        titleBlock.setOpaque(false);
        titleBlock.add(lblTitle);
        titleBlock.add(lblSub);

        headerPanel.add(titleBlock, BorderLayout.WEST);

        // Center Card Form
        JPanel centerContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        centerContainer.setBackground(UITheme.BG_LIGHT);

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(580, 420));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtAppNo = new JTextField();
        txtAppNo.setEditable(false);
        txtAppNo.setBackground(new Color(241, 245, 249));
        UITheme.styleTextField(txtAppNo);

        txtPatientName = new JTextField();
        UITheme.styleTextField(txtPatientName);

        txtAddress = new JTextField();
        UITheme.styleTextField(txtAddress);

        txtContact = new JTextField();
        UITheme.styleTextField(txtContact);

        cbDentist = new JComboBox<>(new String[]{
            "Dr. Nimal Perera (General & Cleaning)",
            "Dr. Sunethra Silva (Orthodontics)",
            "Dr. K. L. Fernando (Root Canal Specialist)",
            "Dr. Aruni Jayawardena (Cosmetic Whitening)",
            "Dr. Mahesh Gunaratne (Tooth Extractions)"
        });
        UITheme.styleComboBox(cbDentist);

        cbTreatment = new JComboBox<>(new String[]{
            "General Consultation & Cleaning",
            "Tooth Extraction",
            "Root Canal Treatment",
            "Dental Filling",
            "Teeth Whitening",
            "Orthodontic Braces Adjustment"
        });
        UITheme.styleComboBox(cbTreatment);

        txtDate = new JTextField(LocalDate.now().toString());
        UITheme.styleTextField(txtDate);

        txtTime = new JTextField("09:00");
        UITheme.styleTextField(txtTime);

        addFormField(card, gbc, 0, "Appointment No:", txtAppNo);
        addFormField(card, gbc, 1, "Patient Full Name:", txtPatientName);
        addFormField(card, gbc, 2, "Address:", txtAddress);
        addFormField(card, gbc, 3, "Contact Number (e.g. 0771234567):", txtContact);
        addFormField(card, gbc, 4, "Assigned Dentist:", cbDentist);
        addFormField(card, gbc, 5, "Treatment Type:", cbTreatment);
        addFormField(card, gbc, 6, "Appointment Date (YYYY-MM-DD):", txtDate);
        addFormField(card, gbc, 7, "Appointment Time (24hr HH:mm):", txtTime);

        centerContainer.add(card);

        // Bottom Actions Panel
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        bottomBar.setBackground(Color.WHITE);
        bottomBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER));

        btnSave = UITheme.createSuccessButton("Save Appointment");
        btnClear = UITheme.createSecondaryButton("Clear Form");
        btnClose = UITheme.createSecondaryButton("Close");

        bottomBar.add(btnSave);
        bottomBar.add(btnClear);
        bottomBar.add(btnClose);

        add(headerPanel, BorderLayout.NORTH);
        add(centerContainer, BorderLayout.CENTER);
        add(bottomBar, BorderLayout.SOUTH);

        // Action Handlers
        btnSave.addActionListener(e -> saveAppointment());
        btnClear.addActionListener(e -> clearForm());
        btnClose.addActionListener(e -> this.dispose());
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String labelText, Component comp) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.35;
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(UITheme.FONT_LABEL);
        lbl.setForeground(UITheme.TEXT_MAIN);
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        panel.add(comp, gbc);
    }

    private void loadNextAppointmentNo() {
        try {
            txtAppNo.setText(dentalService.generateNextAppointmentNo());
        } catch (Exception e) {
            txtAppNo.setText("APT-1001");
        }
    }

    private void saveAppointment() {
        String appNo = txtAppNo.getText().trim();
        String patientName = txtPatientName.getText().trim();
        String address = txtAddress.getText().trim();
        String contact = txtContact.getText().trim();
        String dentist = (String) cbDentist.getSelectedItem();
        String treatment = (String) cbTreatment.getSelectedItem();
        String date = txtDate.getText().trim();
        String time = txtTime.getText().trim();

        if (patientName.isEmpty() || address.isEmpty() || contact.isEmpty() || date.isEmpty() || time.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all mandatory fields.", "Validation Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!InputValidator.isValidPhone(contact)) {
            JOptionPane.showMessageDialog(this, "Invalid contact number format! Please enter a valid Sri Lankan mobile number (e.g. 0771234567).", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!InputValidator.isValidDate(date)) {
            JOptionPane.showMessageDialog(this, "Invalid date format! Please use YYYY-MM-DD (e.g. 2026-09-01).", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!InputValidator.isValidTime(time)) {
            JOptionPane.showMessageDialog(this, "Invalid time format! Please use HH:mm 24hr format (e.g. 09:30 or 14:00).", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Appointment app = new Appointment(appNo, patientName, address, contact, dentist, treatment, date, time, currentUser.getUsername());

        try {
            boolean success = dentalService.registerAppointment(app);
            if (success) {
                JOptionPane.showMessageDialog(this, "Appointment " + appNo + " registered successfully for patient " + patientName + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadNextAppointmentNo();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to register appointment.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Double Booking Alert", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "System error: " + ex.getMessage(), "Execution Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtPatientName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtDate.setText(LocalDate.now().toString());
        txtTime.setText("09:00");
    }
}
