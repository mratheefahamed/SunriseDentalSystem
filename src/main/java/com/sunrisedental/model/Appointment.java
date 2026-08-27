package com.sunrisedental.model;

/**
 * Model entity representing a Dental Patient Appointment.
 */
public class Appointment {
    private String appointmentNo;
    private String patientName;
    private String address;
    private String contactNumber;
    private String dentistName;
    private String treatmentType;
    private String appointmentDate;
    private String appointmentTime;
    private String createdBy;
    private String status; // 'SCHEDULED', 'COMPLETED', 'CANCELLED'

    public Appointment() {
    }

    public Appointment(String appointmentNo, String patientName, String address, 
                       String contactNumber, String dentistName, String treatmentType, 
                       String appointmentDate, String appointmentTime, String createdBy) {
        this(appointmentNo, patientName, address, contactNumber, dentistName, treatmentType, appointmentDate, appointmentTime, createdBy, "SCHEDULED");
    }

    public Appointment(String appointmentNo, String patientName, String address, 
                       String contactNumber, String dentistName, String treatmentType, 
                       String appointmentDate, String appointmentTime, String createdBy, String status) {
        this.appointmentNo = appointmentNo;
        this.patientName = patientName;
        this.address = address;
        this.contactNumber = contactNumber;
        this.dentistName = dentistName;
        this.treatmentType = treatmentType;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.createdBy = createdBy;
        this.status = status;
    }

    public String getAppointmentNo() {
        return appointmentNo;
    }

    public void setAppointmentNo(String appointmentNo) {
        this.appointmentNo = appointmentNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentNo='" + appointmentNo + '\'' +
                ", patientName='" + patientName + '\'' +
                ", dentistName='" + dentistName + '\'' +
                ", treatmentType='" + treatmentType + '\'' +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
