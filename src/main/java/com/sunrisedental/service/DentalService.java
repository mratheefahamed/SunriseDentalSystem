package com.sunrisedental.service;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Bill;
import com.sunrisedental.model.User;
import java.util.List;

/**
 * Service Layer Interface for Sunrise Dental System.
 * Decouples presentation views from backend DAO logic.
 */
public interface DentalService {
    User login(String username, String password);
    boolean registerAppointment(Appointment appointment);
    Appointment getAppointment(String appointmentNo);
    List<Appointment> getAllAppointments();
    double calculateBill(String treatmentType, double consultationFee);
    boolean saveBill(Bill bill);
    Bill getBill(String appointmentNo);
    double getTotalClinicRevenue();
    String generateNextAppointmentNo();
}
