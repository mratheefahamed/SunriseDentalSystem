package com.sunrisedental.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TreatmentFeeFactoryTest {

    @Test
    @DisplayName("Test Base Fee Calculation for Known Treatments")
    void testKnownTreatmentFees() {
        assertEquals(3500.00, TreatmentFeeFactory.getTreatmentBaseFee("General Consultation & Cleaning"), 0.01);
        assertEquals(5000.00, TreatmentFeeFactory.getTreatmentBaseFee("Tooth Extraction"), 0.01);
        assertEquals(18000.00, TreatmentFeeFactory.getTreatmentBaseFee("Root Canal Treatment"), 0.01);
        assertEquals(4500.00, TreatmentFeeFactory.getTreatmentBaseFee("Dental Filling"), 0.01);
        assertEquals(15000.00, TreatmentFeeFactory.getTreatmentBaseFee("Teeth Whitening"), 0.01);
        assertEquals(8000.00, TreatmentFeeFactory.getTreatmentBaseFee("Orthodontic Braces Adjustment"), 0.01);
    }

    @Test
    @DisplayName("Test Default Fallback Fee for Null or Unknown Treatments")
    void testFallbackFees() {
        assertTrue(TreatmentFeeFactory.getTreatmentBaseFee(null) > 0);
        assertTrue(TreatmentFeeFactory.getTreatmentBaseFee("Unknown Procedure") > 0);
    }
}
