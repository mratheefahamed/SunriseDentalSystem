package com.sunrisedental.view;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.service.DentalService;
import com.sunrisedental.service.DentalServiceImpl;
import com.sunrisedental.util.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Modern High-Contrast Search & Display Appointment Interface.
 */
public class SearchAppointmentView extends JFrame {
    private JTextField txtSearchAppNo;
    private JButton btnSearch, btnLoadAll, btnClose;
    private JTable tableAppointments;
    private DefaultTableModel tableModel;
    private final DentalService dentalService = new DentalServiceImpl();

    public SearchAppointmentView() {
        setTitle("Search Appointments - Sunrise Dental Clinic");
        setSize(950, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(UITheme.BG_LIGHT);
        setLayout(new BorderLayout());
        initSearchUI();
        loadAllAppointments();
    }

    private void initSearchUI() {
        // Top Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(15, 23, 42));
        headerPanel.setPreferredSize(new Dimension(950, 65));
        headerPanel.setBorder(new EmptyBorder(12, 25, 12, 25));

        JLabel lblTitle = new JLabel("Search & Display Appointment Directory");
        lblTitle.setFont(UITheme.FONT_HEADER_MED);
        lblTitle.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel("Query appointments by unique Appointment ID or browse all entries");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(148, 163, 184));

        JPanel titleBlock = new JPanel(new GridLayout(2, 1));
        titleBlock.setOpaque(false);
        titleBlock.add(lblTitle);
        titleBlock.add(lblSub);

        headerPanel.add(titleBlock, BorderLayout.WEST);

        // Search Bar Card Panel
        JPanel searchCard = UITheme.createCardPanel();
        searchCard.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 6));

        JLabel lblSearch = new JLabel("Enter Appointment No:");
        lblSearch.setFont(UITheme.FONT_LABEL);
        lblSearch.setForeground(UITheme.TEXT_MAIN);

        txtSearchAppNo = new JTextField(14);
        UITheme.styleTextField(txtSearchAppNo);

        btnSearch = UITheme.createPrimaryButton("Search Appointment");
        btnLoadAll = UITheme.createSecondaryButton("Show All Records");

        searchCard.add(lblSearch);
        searchCard.add(txtSearchAppNo);
        searchCard.add(btnSearch);
        searchCard.add(btnLoadAll);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(headerPanel, BorderLayout.NORTH);
        
        JPanel searchContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        searchContainer.setBackground(UITheme.BG_LIGHT);
        searchCard.setPreferredSize(new Dimension(900, 65));
        searchContainer.add(searchCard);
        
        topContainer.add(searchContainer, BorderLayout.SOUTH);

        // Center Table Card
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(UITheme.BG_LIGHT);
        tableContainer.setBorder(new EmptyBorder(0, 25, 15, 25));

        String[] columnNames = {"APT No", "Patient Name", "Address", "Contact", "Dentist", "Treatment", "Date", "Time", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableAppointments = new JTable(tableModel);
        UITheme.styleTable(tableAppointments);

        JScrollPane scrollPane = new JScrollPane(tableAppointments);
        scrollPane.setBorder(BorderFactory.createLineBorder(UITheme.BORDER, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        tableContainer.add(scrollPane, BorderLayout.CENTER);

        // Bottom Bar
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        bottomBar.setBackground(Color.WHITE);
        bottomBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER));

        btnClose = UITheme.createSecondaryButton("Close");
        bottomBar.add(btnClose);

        add(topContainer, BorderLayout.NORTH);
        add(tableContainer, BorderLayout.CENTER);
        add(bottomBar, BorderLayout.SOUTH);

        // Actions
        btnSearch.addActionListener(e -> searchAppointment());
        btnLoadAll.addActionListener(e -> loadAllAppointments());
        btnClose.addActionListener(e -> this.dispose());
    }

    private void searchAppointment() {
        String appNo = txtSearchAppNo.getText().trim();
        if (appNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Appointment Number to search (e.g. APT-1001).", "Validation Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Appointment app = dentalService.getAppointment(appNo);
        tableModel.setRowCount(0);

        if (app != null) {
            tableModel.addRow(new Object[]{
                app.getAppointmentNo(),
                app.getPatientName(),
                app.getAddress(),
                app.getContactNumber(),
                app.getDentistName(),
                app.getTreatmentType(),
                app.getAppointmentDate(),
                app.getAppointmentTime(),
                app.getStatus()
            });
        } else {
            JOptionPane.showMessageDialog(this, "No appointment record found for Appointment Number: " + appNo, "Not Found", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void loadAllAppointments() {
        tableModel.setRowCount(0);
        List<Appointment> list = dentalService.getAllAppointments();
        for (Appointment app : list) {
            tableModel.addRow(new Object[]{
                app.getAppointmentNo(),
                app.getPatientName(),
                app.getAddress(),
                app.getContactNumber(),
                app.getDentistName(),
                app.getTreatmentType(),
                app.getAppointmentDate(),
                app.getAppointmentTime(),
                app.getStatus()
            });
        }
    }
}
