package com.example.clinicappointmentsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(PatientApp.class.getResource("/com/example/clinicappointmentsystem/Views/Dashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        stage.setTitle("Clinic System - Dashboard");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
