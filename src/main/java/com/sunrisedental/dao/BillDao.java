package com.sunrisedental.dao;

import com.sunrisedental.config.DBConnection;
import com.sunrisedental.model.Bill;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Billing Calculation and Payment Receipt Records.
 */
public class BillDao {

    /**
     * Saves a new billing transaction.
     */
    public boolean saveBill(Bill bill) {
        if (bill == null || bill.getAppointmentNo() == null) {
            return false;
        }

        String sql = "INSERT INTO bills (appointment_no, consultation_fee, treatment_fee, total_amount, payment_status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bill.getAppointmentNo().trim());
            ps.setDouble(2, bill.getConsultationFee());
            ps.setDouble(3, bill.getTreatmentFee());
            ps.setDouble(4, bill.getTotalAmount());
            ps.setString(5, bill.getPaymentStatus() != null ? bill.getPaymentStatus() : "PAID");

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[BillDao Error] Save bill failure: " + e.getMessage());
        }
        return false;
    }

    /**
     * Retrieves a bill by appointment number.
     */
    public Bill getBillByAppointmentNo(String appNo) {
        if (appNo == null || appNo.trim().isEmpty()) {
            return null;
        }

        String sql = "SELECT bill_id, appointment_no, consultation_fee, treatment_fee, total_amount, payment_status, billed_at FROM bills WHERE appointment_no = ? ORDER BY bill_id DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, appNo.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Bill(
                        rs.getInt("bill_id"),
                        rs.getString("appointment_no"),
                        rs.getDouble("consultation_fee"),
                        rs.getDouble("treatment_fee"),
                        rs.getDouble("total_amount"),
                        rs.getString("payment_status"),
                        rs.getTimestamp("billed_at")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("[BillDao Error] Get bill failure: " + e.getMessage());
        }
        return null;
    }

    /**
     * Calculates total accumulated clinic revenue (For Admin Financial Reports).
     */
    public double getTotalClinicRevenue() {
        String sql = "SELECT SUM(total_amount) AS revenue FROM bills WHERE payment_status = 'PAID'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("revenue");
            }
        } catch (SQLException e) {
            System.err.println("[BillDao Error] Revenue calculation failure: " + e.getMessage());
        }
        return 0.0;
    }

    /**
     * Returns all billing records.
     */
    public List<Bill> getAllBills() {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT bill_id, appointment_no, consultation_fee, treatment_fee, total_amount, payment_status, billed_at FROM bills ORDER BY bill_id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(new Bill(
                    rs.getInt("bill_id"),
                    rs.getString("appointment_no"),
                    rs.getDouble("consultation_fee"),
                    rs.getDouble("treatment_fee"),
                    rs.getDouble("total_amount"),
                    rs.getString("payment_status"),
                    rs.getTimestamp("billed_at")
                ));
            }
        } catch (SQLException e) {
            System.err.println("[BillDao Error] Get all bills failure: " + e.getMessage());
        }
        return list;
    }
}
