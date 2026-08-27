package com.sunrisedental.factory;

import com.sunrisedental.config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Factory Design Pattern implementation for dynamically calculating base fees
 * for dental treatment types.
 */
public class TreatmentFeeFactory {

    /**
     * Factory method returning base fee for a given treatment type.
     * Checks database first; falls back to static defaults if DB row absent.
     */
    public static double getTreatmentBaseFee(String treatmentType) {
        if (treatmentType == null || treatmentType.trim().isEmpty()) {
            return 3500.00; // Default consultation fee fallback
        }

        String sql = "SELECT base_fee FROM treatments WHERE treatment_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, treatmentType.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("base_fee");
                }
            }
        } catch (SQLException e) {
            System.err.println("[TreatmentFeeFactory Warning] DB query failed, using static factory mapping: " + e.getMessage());
        }

        // Fallback static mapping
        switch (treatmentType.trim()) {
            case "General Consultation & Cleaning":
                return 3500.00;
            case "Tooth Extraction":
                return 5000.00;
            case "Root Canal Treatment":
                return 18000.00;
            case "Dental Filling":
                return 4500.00;
            case "Teeth Whitening":
                return 15000.00;
            case "Orthodontic Braces Adjustment":
                return 8000.00;
            default:
                return 4000.00;
        }
    }
}
