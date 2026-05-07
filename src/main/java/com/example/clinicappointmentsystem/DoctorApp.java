package com.example.clinicappointmentsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DoctorApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(PatientApp.class.getResource("/com/example/clinicappointmentsystem/Views/Doctorscene.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        stage.setTitle("Clinic System - Add Doctor");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
