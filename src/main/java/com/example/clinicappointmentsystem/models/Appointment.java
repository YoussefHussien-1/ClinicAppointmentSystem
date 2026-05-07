package com.example.clinicappointmentsystem.models;

public class Appointment {
    private String patientName;
    private String doctorName;
    private String appointmentDate; // تأكد من الاسم هنا
    private String appointmentTime; // تأكد من الاسم هنا

    public Appointment(String patientName, String doctorName, String appointmentDate, String appointmentTime) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }

    // الـ Getters دي هي اللي الـ TableView بيدور عليها:
    public String getPatientName() { return patientName; }
    public String getDoctorName() { return doctorName; }
    public String getAppointmentDate() { return appointmentDate; }
    public String getAppointmentTime() { return appointmentTime; }

    public String toFileString() {
        return patientName + "," + doctorName + "," + appointmentDate + "," + appointmentTime;
    }
}