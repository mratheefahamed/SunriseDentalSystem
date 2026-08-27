package com.sunrisedental.dao;

import com.sunrisedental.config.DBConnection;
import com.sunrisedental.model.Appointment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Appointment Management & Double Booking Prevention.
 */
public class AppointmentDao {

    /**
     * Checks if a dentist already has an appointment scheduled at the given date and time.
     */
    public boolean isDoubleBooking(String dentistName, String date, String time) {
        if (dentistName == null || date == null || time == null) {
            return false;
        }

        String sql = "SELECT 1 FROM appointments WHERE dentist_name = ? AND appointment_date = ? AND appointment_time = ? AND status != 'CANCELLED'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, dentistName.trim());
            ps.setString(2, date.trim());
            ps.setString(3, time.trim());
            
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("[AppointmentDao Error] Double booking check failure: " + e.getMessage());
        }
        return false;
    }

    /**
     * Saves a new patient appointment to the database.
     */
    public boolean saveAppointment(Appointment app) {
        if (app == null || app.getAppointmentNo() == null) {
            return false;
        }

        String sql = "INSERT INTO appointments (appointment_no, patient_name, address, contact_number, dentist_name, treatment_type, appointment_date, appointment_time, created_by, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, app.getAppointmentNo().trim());
            ps.setString(2, app.getPatientName().trim());
            ps.setString(3, app.getAddress().trim());
            ps.setString(4, app.getContactNumber().trim());
            ps.setString(5, app.getDentistName().trim());
            ps.setString(6, app.getTreatmentType().trim());
            ps.setString(7, app.getAppointmentDate().trim());
            ps.setString(8, app.getAppointmentTime().trim());
            ps.setString(9, app.getCreatedBy() != null ? app.getCreatedBy().trim() : "system");
            ps.setString(10, app.getStatus() != null ? app.getStatus() : "SCHEDULED");

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[AppointmentDao Error] Save appointment failure: " + e.getMessage());
        }
        return false;
    }

    /**
     * Searches an appointment by unique appointment number.
     */
    public Appointment findByAppointmentNo(String appNo) {
        if (appNo == null || appNo.trim().isEmpty()) {
            return null;
        }

        String sql = "SELECT appointment_no, patient_name, address, contact_number, dentist_name, treatment_type, appointment_date, appointment_time, created_by, status FROM appointments WHERE appointment_no = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, appNo.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Appointment(
                        rs.getString("appointment_no"),
                        rs.getString("patient_name"),
                        rs.getString("address"),
                        rs.getString("contact_number"),
                        rs.getString("dentist_name"),
                        rs.getString("treatment_type"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_time"),
                        rs.getString("created_by"),
                        rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("[AppointmentDao Error] Find appointment failure: " + e.getMessage());
        }
        return null;
    }

    /**
     * Returns all appointments ordered by appointment date and time.
     */
    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT appointment_no, patient_name, address, contact_number, dentist_name, treatment_type, appointment_date, appointment_time, created_by, status FROM appointments ORDER BY appointment_date DESC, appointment_time ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(new Appointment(
                    rs.getString("appointment_no"),
                    rs.getString("patient_name"),
                    rs.getString("address"),
                    rs.getString("contact_number"),
                    rs.getString("dentist_name"),
                    rs.getString("treatment_type"),
                    rs.getString("appointment_date"),
                    rs.getString("appointment_time"),
                    rs.getString("created_by"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("[AppointmentDao Error] Get all appointments failure: " + e.getMessage());
        }
        return list;
    }

    /**
     * Generates a unique next appointment number (e.g. APT-1001).
     */
    public String generateNextAppointmentNo() {
        String sql = "SELECT COUNT(*) AS total FROM appointments";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt("total") + 1;
                return String.format("APT-%04d", count);
            }
        } catch (SQLException e) {
            System.err.println("[AppointmentDao Warning] System counter failed, using timestamp: " + e.getMessage());
        }
        return "APT-" + System.currentTimeMillis() % 10000;
    }
}
