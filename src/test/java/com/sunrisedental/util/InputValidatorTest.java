package com.sunrisedental.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InputValidatorTest {

    @Test
    @DisplayName("Test Valid Sri Lankan Phone Numbers")
    void testValidPhoneNumbers() {
        assertTrue(InputValidator.isValidPhone("0771234567"));
        assertTrue(InputValidator.isValidPhone("0719876543"));
        assertTrue(InputValidator.isValidPhone("+94771234567"));
    }

    @Test
    @DisplayName("Test Invalid Phone Numbers")
    void testInvalidPhoneNumbers() {
        assertFalse(InputValidator.isValidPhone("12345"));
        assertFalse(InputValidator.isValidPhone("abcdefghij"));
        assertFalse(InputValidator.isValidPhone("0112345678")); // Landline, not mobile
        assertFalse(InputValidator.isValidPhone(null));
        assertFalse(InputValidator.isValidPhone(""));
    }

    @Test
    @DisplayName("Test Valid Date Formats (YYYY-MM-DD)")
    void testValidDates() {
        assertTrue(InputValidator.isValidDate("2026-08-28"));
        assertTrue(InputValidator.isValidDate("2026-12-31"));
    }

    @Test
    @DisplayName("Test Invalid Date Formats")
    void testInvalidDates() {
        assertFalse(InputValidator.isValidDate("28-08-2026"));
        assertFalse(InputValidator.isValidDate("2026/08/28"));
        assertFalse(InputValidator.isValidDate("invalid-date"));
        assertFalse(InputValidator.isValidDate(null));
    }

    @Test
    @DisplayName("Test Valid 24hr Time Formats (HH:mm)")
    void testValidTimes() {
        assertTrue(InputValidator.isValidTime("09:00"));
        assertTrue(InputValidator.isValidTime("14:30"));
        assertTrue(InputValidator.isValidTime("23:59"));
    }

    @Test
    @DisplayName("Test Invalid Time Formats")
    void testInvalidTimes() {
        assertFalse(InputValidator.isValidTime("9:00"));
        assertFalse(InputValidator.isValidTime("25:00"));
        assertFalse(InputValidator.isValidTime("12:60"));
        assertFalse(InputValidator.isValidTime("invalid"));
        assertFalse(InputValidator.isValidTime(null));
    }
}
