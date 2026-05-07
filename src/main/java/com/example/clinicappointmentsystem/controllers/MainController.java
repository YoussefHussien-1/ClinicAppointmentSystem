package com.example.clinicappointmentsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {

    // ميثود عامة لتبديل الشاشات عشان منكررش الكود
    private void switchScene(ActionEvent event, String fxmlFile, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/clinicappointmentsystem/Views/" + fxmlFile));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error: Could not load " + fxmlFile);
            e.printStackTrace();
        }
    }

    @FXML
    void openCalendar(ActionEvent event) {
        switchScene(event, "AppointmentScene.fxml", "Clinic Schedule");
    }

    @FXML
    void openPatientTable(ActionEvent event) {
        switchScene(event, "TableView.fxml", "Patient Records");
    }

    @FXML
    void openRegistration(ActionEvent event) {
        switchScene(event, "PatientScene.fxml", "New Appointment");
    }

    @FXML
    void handleExit(ActionEvent event) {
        System.exit(0);
    }

    public void openDashboard(ActionEvent event) {switchScene(event , "Dashboard.fxml", "DashBoard");}

    public void openAddDoctor(ActionEvent event) {switchScene(event, "Doctorscene.fxml", "Add Doctor");}
}