package com.sunrisedental.model;

/**
 * Model entity representing a Dental Treatment option and base fee.
 */
public class Treatment {
    private int treatmentId;
    private String treatmentName;
    private double baseFee;

    public Treatment() {
    }

    public Treatment(int treatmentId, String treatmentName, double baseFee) {
        this.treatmentId = treatmentId;
        this.treatmentName = treatmentName;
        this.baseFee = baseFee;
    }

    public Treatment(String treatmentName, double baseFee) {
        this.treatmentName = treatmentName;
        this.baseFee = baseFee;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public double getBaseFee() {
        return baseFee;
    }

    public void setBaseFee(double baseFee) {
        this.baseFee = baseFee;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "treatmentId=" + treatmentId +
                ", treatmentName='" + treatmentName + '\'' +
                ", baseFee=" + baseFee +
                '}';
    }
}
