package com.example.clinicappointmentsystem.controllers;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {

    @FXML private StackPane calendarContainer;
    @FXML private ChoiceBox<String> specialtyFilterBox;
    @FXML private ChoiceBox<String> doctorFilterBox;

    private Calendar<String> clinicCalendar;
    private final String[] specialties = {"Dentistry", "Dermatology", "Cardiology", "Neurology", "Pediatrics"};

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupCalendar();

        // 1. ملء فلاتر التخصصات
        specialtyFilterBox.getItems().add("All Specialties");
        specialtyFilterBox.getItems().addAll(specialties);
        specialtyFilterBox.getSelectionModel().selectFirst();

        // 2. مستمع لتغير التخصص لتحديث لستة الدكاترة
        specialtyFilterBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadDoctorsBySpecialty(newVal);
            }
        });

        // 3. مستمع لتغير الدكتور لتحديث الكالندر فوراً
        doctorFilterBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadAppointmentsFromCSV();
            }
        });

        loadAppointmentsFromCSV();
    }

    private void setupCalendar() {
        CalendarView calendarView = new CalendarView();
        clinicCalendar = new Calendar<>("Clinic Schedule");
        clinicCalendar.setStyle(Calendar.Style.STYLE1);

        CalendarSource mySource = new CalendarSource("Management");
        mySource.getCalendars().add(clinicCalendar);
        calendarView.getCalendarSources().add(mySource);

        // إعدادات الواجهة المتوافقة مع CalendarFX
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.setShowSourceTray(false);
        calendarView.setShowPrintButton(false);
        calendarView.showMonthPage(); // يفتح على عرض الشهر افتراضياً

        calendarContainer.getChildren().clear();
        calendarContainer.getChildren().add(calendarView);
    }

    private void loadDoctorsBySpecialty(String specialty) {
        doctorFilterBox.getItems().clear();
        doctorFilterBox.getItems().add("All Doctors");

        try (BufferedReader br = new BufferedReader(new FileReader("DoctorData.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (specialty.equals("All Specialties") || (data.length > 2 && data[2].trim().equalsIgnoreCase(specialty.trim()))) {
                    doctorFilterBox.getItems().add(data[1].trim());
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading doctors for filter.");
        }
        doctorFilterBox.getSelectionModel().selectFirst();
    }

    private void loadAppointmentsFromCSV() {
        clinicCalendar.clear(); // مسح المواعيد الحالية قبل إعادة التحميل
        String selectedDoctor = doctorFilterBox.getValue();

        try (BufferedReader br = new BufferedReader(new FileReader("Appointments.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");

                if (data.length >= 4) {
                    String pName = data[0].trim();
                    String dName = data[1].trim();

                    // لو المختار "All Doctors" أو اسم الدكتور مطابق للسطر
                    if (selectedDoctor == null || selectedDoctor.equals("All Doctors") || dName.equalsIgnoreCase(selectedDoctor)) {
                        LocalDate date = LocalDate.parse(data[2].trim());
                        LocalTime time = LocalTime.parse(data[3].trim());

                        Entry<String> entry = new Entry<>("Patient: " + pName + "\nDr: " + dName);
                        entry.setInterval(date, time, date, time.plusHours(1));
                        clinicCalendar.addEntry(entry);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Appointments.csv empty or not found.");
        }
    }

    @FXML
    void refreshCalendar(ActionEvent event) { // تم إضافة ActionEvent لحل الـ LoadException
        loadAppointmentsFromCSV();
        System.out.println("Calendar Refreshed!");
    }

    @FXML
    void handleBackToMenu(ActionEvent event) {
        try {
            // تأكد من المسار الصحيح لملف المنيو بتاعك
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/clinicappointmentsystem/Views/MainMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Main Menu");
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToAddPatient(ActionEvent event) {
        try {
            // 1. تحميل ملف الشاشة الجديدة
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clinicappointmentsystem/Views/PatientScene.fxml"));
            Parent root = loader.load();

            // 2. الحصول على الـ Stage الحالي (النافذة اللي مفتوحة دالوقتي)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // 3. تغيير المشهد (Scene)
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Error: Could not find PatientScene.fxml");
            e.printStackTrace();
        }
    }
}