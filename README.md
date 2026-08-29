# 🦷 Sunrise Dental Clinic — Management System

A computerized **Appointment & Patient Management System** for Sunrise Dental Clinic, Colombo.
Built with **Java Swing**, **MySQL**, and **REST Web Services** for the BSc Advanced Programming module.

## 📋 Features

| Feature | Description |
|---------|-------------|
| User Authentication | Separate Admin and Staff login with role-based access |
| Register Appointment | Book patient visits with double-booking prevention |
| Search Appointments | Search by APT number or view all records |
| Billing & Receipts | Calculate treatment fees (Factory Pattern) and print invoices |
| Financial Reports | Admin-only revenue analytics dashboard |
| REST Web Service | API endpoints at `http://localhost:8080/api/` |
| Help Manual | Built-in operating instructions for clinic staff |

## 🏗️ Design Patterns Used

- **Singleton Pattern** — `DBConnection.java` (Thread-safe database connection)
- **Factory Pattern** — `TreatmentFeeFactory.java` (Treatment fee calculation)
- **DAO Pattern** — `UserDao`, `AppointmentDao`, `BillDao` (Data access layer)

## 🛠️ Prerequisites

- Java JDK 17 or higher
- XAMPP (MySQL via phpMyAdmin)
- NetBeans IDE (recommended)

## 🗄️ Database Setup

1. Start **XAMPP** and ensure **MySQL** is running
2. Open **phpMyAdmin** (`http://localhost/phpmyadmin`)
3. Create a new database named: `sunrise_dental_db`
4. Run the SQL schema to create tables for `users`, `appointments`, `bills`, and `treatments`
5. Default users:
   - Admin: `admin` / `admin123`
   - Staff: `staff1` / `staff123`

## 🚀 How to Run

1. Open the project in **NetBeans**
2. Right-click `AppMain.java` → **Run File** (`Shift + F6`)
3. The REST Web Service starts automatically on port `8080`
4. Login window appears — enter credentials to access the dashboard

## 🌐 REST API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/status` | GET | Health check |
| `/api/appointments` | GET | List all appointments |
| `/api/revenue` | GET | Total clinic revenue |

## 🧪 Running Tests

In NetBeans: Right-click any test file in `src/test/java` → **Test File** (`Ctrl + F6`)

## 📁 Project Structure

src/main/java/com/sunrisedental/ ├── config/ — Database configuration (Singleton) ├── dao/ — Data Access Objects ├── factory/ — Treatment Fee Factory Pattern ├── main/ — Application entry point ├── model/ — Entity classes (User, Appointment, Bill, Treatment) ├── service/ — Business logic and REST Web Service ├── util/ — Input validation and UI theme └── view/ — Swing GUI views


## 📄 Version History

| Version | Description |
|---------|-------------|
| v1.0.0 | Initial release — Swing UI, database connection, login |
| v1.1.0 | UI enhancements and modern high-contrast styling |
| v1.2.0 | Automated JUnit 5 test suite and billing fixes |
| v1.3.0 | Table header fix, reports access control, CI/CD workflow |

## 👤 Author

**MR.Atheef Ahamed (CL/BSCSD/35/101)** — BSc (Hons) Software Engineering, Semester 01, Module  Advance Programming 