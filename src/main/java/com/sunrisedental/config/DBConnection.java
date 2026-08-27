package com.sunrisedental.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton Pattern implementation for Database Connection.
 * Ensures a single shared JDBC connection instance across the application lifecycle.
 */
public class DBConnection {
    private static Connection connection = null;
    private static final String URL = "jdbc:mysql://localhost:3306/sunrise_dental_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Adjust password if configured in MySQL

    private DBConnection() {
        // Private constructor prevents external instantiation
    }

    /**
     * Returns the singleton Connection instance.
     * Re-establishes connection if closed or null.
     */
    public static synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("[DBConnection] Connected successfully to sunrise_dental_db");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("[DBConnection Error] MySQL JDBC Driver not found on classpath: " + e.getMessage());
            throw new RuntimeException("MySQL JDBC Driver missing", e);
        } catch (SQLException e) {
            System.err.println("[DBConnection Error] Database connection failure: " + e.getMessage());
            throw new RuntimeException("Database connection failed: " + e.getMessage(), e);
        }
        return connection;
    }
}
