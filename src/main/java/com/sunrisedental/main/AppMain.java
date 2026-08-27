package com.sunrisedental.main;

import com.sunrisedental.view.LoginView;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Application Entry Point Launcher for Sunrise Dental System.
 */
public class AppMain {

    public static void main(String[] args) {
        // Set System Look & Feel for modern native UI rendering
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        // Start Embedded Distributed Web Service Server (REST API)
        com.sunrisedental.service.DentalRestWebService.startWebService();

        // Launch Login View on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
