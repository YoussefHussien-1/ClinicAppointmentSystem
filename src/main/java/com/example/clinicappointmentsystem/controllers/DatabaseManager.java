package com.example.clinicappointmentsystem.controllers;

import java.sql.*;

public class DatabaseManager {

    static String URL = "jdbc:sqlite:clinic.db";

    public DatabaseManager() throws SQLException {
    }

    public static Connection connect(){
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void Initialization(){
        String createDoctorTable = """
            CREATE TABLE IF NOT EXISTS Doctor (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                specialty TEXT NOT NULL,
                phoneNumber TEXT
        );
    """;

        // 2. جدول المرضى
        String createPatientTable = """
            CREATE TABLE IF NOT EXISTS Patient (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                age INTEGER,
                phoneNumber TEXT,
                gender TEXT,
                neededSpecialty TEXT
        );
    """;

        // 3. جدول المواعيد (هنا بقى الربط الحقيقي - الـ Foreign Keys)
        String createAppointmentsTable = """
            CREATE TABLE IF NOT EXISTS Appointment (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                patient_id INTEGER,
                doctor_id INTEGER,
                appointment_date TEXT,
                FOREIGN KEY (patient_id) REFERENCES Patient(id),
                FOREIGN KEY (doctor_id) REFERENCES Doctor(id)
        );
    """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createPatientTable);
            stmt.execute(createDoctorTable);
            stmt.execute(createAppointmentsTable);
            System.out.println("Table Created Successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
