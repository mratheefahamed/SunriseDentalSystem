package com.sunrisedental.view;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.User;
import com.sunrisedental.service.DentalService;
import com.sunrisedental.service.DentalServiceImpl;
import com.sunrisedental.util.InputValidator;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Appointment Registration Swing Form.
 * Collects patient details, dentist assignment, treatment type, date & time.
 * Enforces double-booking checks and input validation.
 */
public class RegisterAppointmentView extends JFrame {
    private JTextField txtAppNo, txtPatientName, txtAddress, txtContact, txtDate, txtTime;
    private JComboBox<String> cbDentist, cbTreatment;
    private JButton btnSave, btnClear, btnClose;
    private final DentalService dentalService = new DentalServiceImpl();
    private final User currentUser;

    public RegisterAppointmentView(User user) {
        this.currentUser = user;
        setTitle("Register New Patient Appointment - Sunrise Dental Clinic");
        setSize(580, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        initForm();
        loadNextAppointmentNo();
    }

    private void initForm() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(46, 125, 50));
        JLabel lblHeader = new JLabel("Register New Patient Appointment");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHeader.setForeground(Color.WHITE);
        headerPanel.add(lblHeader);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        formPanel.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtAppNo = new JTextField(15);
        txtAppNo.setEditable(false);
        txtAppNo.setBackground(new Color(235, 238, 242));

        txtPatientName = new JTextField(15);
        txtAddress = new JTextField(15);
        txtContact = new JTextField(15);

        cbDentist = new JComboBox<>(new String[]{
            "Dr. Nimal Perera (General & Cleaning)",
            "Dr. Sunethra Silva (Orthodontics)",
            "Dr. K. L. Fernando (Root Canal Specialist)",
            "Dr. Aruni Jayawardena (Cosmetic Whitening)",
            "Dr. Mahesh Gunaratne (Tooth Extractions)"
        });

        cbTreatment = new JComboBox<>(new String[]{
            "General Consultation & Cleaning",
            "Tooth Extraction",
            "Root Canal Treatment",
            "Dental Filling",
            "Teeth Whitening",
            "Orthodontic Braces Adjustment"
        });

        txtDate = new JTextField(LocalDate.now().toString()); // YYYY-MM-DD
        txtTime = new JTextField("09:00"); // HH:mm

        addFormField(formPanel, gbc, 0, "Appointment No:", txtAppNo);
        addFormField(formPanel, gbc, 1, "Patient Name:", txtPatientName);
        addFormField(formPanel, gbc, 2, "Address:", txtAddress);
        addFormField(formPanel, gbc, 3, "Contact Number:", txtContact);
        addFormField(formPanel, gbc, 4, "Dentist Name:", cbDentist);
        addFormField(formPanel, gbc, 5, "Treatment Type:", cbTreatment);
        addFormField(formPanel, gbc, 6, "Appointment Date (YYYY-MM-DD):", txtDate);
        addFormField(formPanel, gbc, 7, "Appointment Time (HH:mm):", txtTime);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnSave = new JButton("Save Appointment");
        btnSave.setBackground(new Color(46, 125, 50));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 13));

        btnClear = new JButton("Clear");
        btnClose = new JButton("Close");

        btnPanel.add(btnSave);
        btnPanel.add(btnClear);
        btnPanel.add(btnClose);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Actions
        btnSave.addActionListener(e -> saveAppointment());
        btnClear.addActionListener(e -> clearForm());
        btnClose.addActionListener(e -> this.dispose());
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String labelText, Component comp) {
        gbc.gridx = 0; gbc.gridy = row;
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
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

        // Validations
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
                JOptionPane.showMessageDialog(this, "Appointment " + appNo + " registered successfully for patient " + patientName + "!", "Registration Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadNextAppointmentNo();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to register appointment. Please check database connection.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Double Booking Alert", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "System error: " + ex.getMessage(), "Execution Failure", JOptionPane.ERROR_MESSAGE);
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
