package com.sunrisedental.service;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sunrisedental.model.Appointment;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Embedded Distributed Web Service Server.
 * Exposes RESTful Web Service APIs for external clients, web frontends, or mobile apps.
 * Fulfills Distributed Web Application requirement (LO III).
 */
public class DentalRestWebService {
    private static final int PORT = 8080;
    private static HttpServer server;
    private static final DentalService dentalService = new DentalServiceImpl();

    /**
     * Starts the distributed HTTP REST Web Service server.
     */
    public static void startWebService() {
        try {
            if (server != null) return;
            server = HttpServer.create(new InetSocketAddress(PORT), 0);

            // API Endpoint 1: Get All Appointments JSON
            server.createContext("/api/appointments", new AppointmentsHandler());

            // API Endpoint 2: Get Clinic Revenue JSON
            server.createContext("/api/revenue", new RevenueHandler());

            // API Endpoint 3: Web Service Status Check
            server.createContext("/api/status", new StatusHandler());

            server.setExecutor(null); // Creates default executor
            server.start();
            System.out.println("[Distributed Web Service] REST API Server running at http://localhost:" + PORT + "/api/");
        } catch (IOException e) {
            System.err.println("[Web Service Error] Failed to start HTTP server: " + e.getMessage());
        }
    }

    /**
     * Stops the REST Web Service server safely.
     */
    public static void stopWebService() {
        if (server != null) {
            server.stop(0);
            server = null;
            System.out.println("[Distributed Web Service] Server stopped.");
        }
    }

    // Handler 1: /api/appointments
    static class AppointmentsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                List<Appointment> appointments = dentalService.getAllAppointments();
                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < appointments.size(); i++) {
                    Appointment app = appointments.get(i);
                    json.append("{")
                        .append("\"appointmentNo\":\"").append(app.getAppointmentNo()).append("\",")
                        .append("\"patientName\":\"").append(app.getPatientName()).append("\",")
                        .append("\"dentistName\":\"").append(app.getDentistName()).append("\",")
                        .append("\"treatmentType\":\"").append(app.getTreatmentType()).append("\",")
                        .append("\"appointmentDate\":\"").append(app.getAppointmentDate()).append("\",")
                        .append("\"appointmentTime\":\"").append(app.getAppointmentTime()).append("\"")
                        .append("}");
                    if (i < appointments.size() - 1) json.append(",");
                }
                json.append("]");

                sendJsonResponse(exchange, 200, json.toString());
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }

    // Handler 2: /api/revenue
    static class RevenueHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                double revenue = dentalService.getTotalClinicRevenue();
                String json = String.format("{\"totalRevenue\": %.2f, \"currency\": \"LKR\"}", revenue);
                sendJsonResponse(exchange, 200, json);
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    // Handler 3: /api/status
    static class StatusHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String json = "{\"serviceName\":\"Sunrise Dental Clinic REST Web Service\",\"status\":\"UP\",\"port\":" + PORT + "}";
            sendJsonResponse(exchange, 200, json);
        }
    }

    private static void sendJsonResponse(HttpExchange exchange, int statusCode, String responseJson) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        byte[] bytes = responseJson.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
