package com.sunrisedental.service;

import com.sunrisedental.dao.AppointmentDao;
import com.sunrisedental.dao.BillDao;
import com.sunrisedental.dao.UserDao;
import com.sunrisedental.factory.TreatmentFeeFactory;
import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Bill;
import com.sunrisedental.model.User;
import java.util.List;

/**
 * Service Layer Implementation.
 */
public class DentalServiceImpl implements DentalService {
    private final UserDao userDao = new UserDao();
    private final AppointmentDao appointmentDao = new AppointmentDao();
    private final BillDao billDao = new BillDao();

    @Override
    public User login(String username, String password) {
        return userDao.authenticate(username, password);
    }

    @Override
    public boolean registerAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment details cannot be null.");
        }
        if (appointmentDao.isDoubleBooking(
                appointment.getDentistName(), 
                appointment.getAppointmentDate(), 
                appointment.getAppointmentTime())) {
            throw new IllegalArgumentException("Double booking error! Dentist " + appointment.getDentistName() + 
                    " already has an appointment scheduled at " + appointment.getAppointmentDate() + " " + appointment.getAppointmentTime() + ".");
        }
        return appointmentDao.saveAppointment(appointment);
    }

    @Override
    public Appointment getAppointment(String appointmentNo) {
        return appointmentDao.findByAppointmentNo(appointmentNo);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentDao.getAllAppointments();
    }

    @Override
    public double calculateBill(String treatmentType, double consultationFee) {
        double baseFee = TreatmentFeeFactory.getTreatmentBaseFee(treatmentType);
        return consultationFee + baseFee;
    }

    @Override
    public boolean saveBill(Bill bill) {
        return billDao.saveBill(bill);
    }

    @Override
    public Bill getBill(String appointmentNo) {
        return billDao.getBillByAppointmentNo(appointmentNo);
    }

    @Override
    public double getTotalClinicRevenue() {
        return billDao.getTotalClinicRevenue();
    }

    @Override
    public String generateNextAppointmentNo() {
        return appointmentDao.generateNextAppointmentNo();
    }
}
