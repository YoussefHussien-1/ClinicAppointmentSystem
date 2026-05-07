package com.example.clinicappointmentsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TableViewApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PatientApp.class.getResource("/com/example/clinicappointmentsystem/Views/TableView.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Clinic System - Table View");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
