package com.sunrisedental.view;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.service.DentalService;
import com.sunrisedental.service.DentalServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Interface to Search & Display Appointment Details by Appointment Number or List All.
 */
public class SearchAppointmentView extends JFrame {
    private JTextField txtSearchAppNo;
    private JButton btnSearch, btnLoadAll, btnClose;
    private JTable tableAppointments;
    private DefaultTableModel tableModel;
    private final DentalService dentalService = new DentalServiceImpl();

    public SearchAppointmentView() {
        setTitle("Search & Display Appointment Details - Sunrise Dental Clinic");
        setSize(880, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initSearchUI();
        loadAllAppointments();
    }

    private void initSearchUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(2, 136, 209));
        JLabel lblHeader = new JLabel("Search & View Patient Appointment Details");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHeader.setForeground(Color.WHITE);
        headerPanel.add(lblHeader);

        JPanel searchBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        searchBarPanel.setBackground(new Color(245, 247, 250));

        JLabel lblSearch = new JLabel("Appointment No:");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtSearchAppNo = new JTextField(12);

        btnSearch = new JButton("Search Appointment");
        btnSearch.setBackground(new Color(2, 136, 209));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 12));

        btnLoadAll = new JButton("Show All");
        btnClose = new JButton("Close");

        searchBarPanel.add(lblSearch);
        searchBarPanel.add(txtSearchAppNo);
        searchBarPanel.add(btnSearch);
        searchBarPanel.add(btnLoadAll);
        searchBarPanel.add(btnClose);

        // Table Setup
        String[] columnNames = {"APT No", "Patient Name", "Address", "Contact", "Dentist", "Treatment", "Date", "Time", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableAppointments = new JTable(tableModel);
        tableAppointments.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tableAppointments.setRowHeight(24);
        tableAppointments.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableAppointments.getTableHeader().setBackground(new Color(225, 245, 254));

        JScrollPane scrollPane = new JScrollPane(tableAppointments);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(headerPanel, BorderLayout.NORTH);
        topContainer.add(searchBarPanel, BorderLayout.SOUTH);
        
        mainPanel.add(topContainer, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

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
