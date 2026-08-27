package com.sunrisedental.util;

import java.util.regex.Pattern;

/**
 * Utility class for validating user input formats across Swing views.
 */
public class InputValidator {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(?:0|\\+94)?7[0-9]{8}$");
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$"); // YYYY-MM-DD
    private static final Pattern TIME_PATTERN = Pattern.compile("^(?:[01]\\d|2[0-3]):[0-5]\\d$"); // HH:mm (24hr)

    /**
     * Validates Sri Lankan mobile phone numbers (e.g. 0771234567 or +94771234567).
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    /**
     * Validates date string in YYYY-MM-DD format.
     */
    public static boolean isValidDate(String dateStr) {
        return dateStr != null && DATE_PATTERN.matcher(dateStr.trim()).matches();
    }

    /**
     * Validates time string in HH:mm 24hr format.
     */
    public static boolean isValidTime(String timeStr) {
        return timeStr != null && TIME_PATTERN.matcher(timeStr.trim()).matches();
    }
}
