package com.sunrisedental.dao;

import com.sunrisedental.config.DBConnection;
import com.sunrisedental.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for User Authentication and Account Persistence.
 */
public class UserDao {

    /**
     * Authenticates a user by username and password.
     */
    public User authenticate(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        String sql = "SELECT user_id, username, password, full_name, role FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username.trim());
            ps.setString(2, password.trim());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("[UserDao Error] Authentication failure: " + e.getMessage());
        }
        return null;
    }

    /**
     * Checks if a username already exists.
     */
    public boolean userExists(String username) {
        if (username == null) return false;
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("[UserDao Error] User check failure: " + e.getMessage());
        }
        return false;
    }

    /**
     * Registers a new user account (Admin only action).
     */
    public boolean registerUser(User user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            return false;
        }
        String sql = "INSERT INTO users (username, password, full_name, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, user.getUsername().trim());
            ps.setString(2, user.getPassword().trim());
            ps.setString(3, user.getFullName().trim());
            ps.setString(4, user.getRole() != null ? user.getRole().toUpperCase() : "STAFF");

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[UserDao Error] User registration failure: " + e.getMessage());
        }
        return false;
    }

    /**
     * Returns all registered system users.
     */
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT user_id, username, password, full_name, role FROM users ORDER BY user_id ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("full_name"),
                    rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            System.err.println("[UserDao Error] Get all users failure: " + e.getMessage());
        }
        return list;
    }
}
