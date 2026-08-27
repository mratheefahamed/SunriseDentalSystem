package com.sunrisedental.model;

import java.sql.Timestamp;

/**
 * Model entity representing a Patient Bill / Invoice.
 */
public class Bill {
    private int billId;
    private String appointmentNo;
    private double consultationFee;
    private double treatmentFee;
    private double totalAmount;
    private String paymentStatus; // 'PAID' or 'PENDING'
    private Timestamp billedAt;

    public Bill() {
    }

    public Bill(String appointmentNo, double consultationFee, double treatmentFee, double totalAmount) {
        this.appointmentNo = appointmentNo;
        this.consultationFee = consultationFee;
        this.treatmentFee = treatmentFee;
        this.totalAmount = totalAmount;
        this.paymentStatus = "PAID";
    }

    public Bill(int billId, String appointmentNo, double consultationFee, double treatmentFee, double totalAmount, String paymentStatus, Timestamp billedAt) {
        this.billId = billId;
        this.appointmentNo = appointmentNo;
        this.consultationFee = consultationFee;
        this.treatmentFee = treatmentFee;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.billedAt = billedAt;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getAppointmentNo() {
        return appointmentNo;
    }

    public void setAppointmentNo(String appointmentNo) {
        this.appointmentNo = appointmentNo;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public double getTreatmentFee() {
        return treatmentFee;
    }

    public void setTreatmentFee(double treatmentFee) {
        this.treatmentFee = treatmentFee;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Timestamp getBilledAt() {
        return billedAt;
    }

    public void setBilledAt(Timestamp billedAt) {
        this.billedAt = billedAt;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "billId=" + billId +
                ", appointmentNo='" + appointmentNo + '\'' +
                ", consultationFee=" + consultationFee +
                ", treatmentFee=" + treatmentFee +
                ", totalAmount=" + totalAmount +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
