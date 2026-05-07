package com.example.clinicappointmentsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppointmentApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(PatientApp.class.getResource("/com/example/clinicappointmentsystem/Views/AppointmentScene.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Clinic System - Appointment");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
