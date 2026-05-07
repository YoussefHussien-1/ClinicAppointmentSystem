package com.example.clinicappointmentsystem.controllers;

import com.example.clinicappointmentsystem.models.Patient;
import com.example.clinicappointmentsystem.models.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PatientController implements Initializable {

    // تأكد إن الأسماء دي مطابقة للـ fx:id في الـ FXML
    @FXML private TextField NameFeild;
    @FXML private TextField AgeFeild;
    @FXML private TextField PhoneFeild;
    @FXML private ChoiceBox<String> genderBox;
    @FXML private ChoiceBox<String> specialtyChoiceBox;
    @FXML private ChoiceBox<String> doctorChoiceBox;
    @FXML private DatePicker datePicker;

    private String[] Specialties = {"Dentistry", "Dermatology", "Cardiology", "Neurology", "Pediatrics"};
    private String[] Genders = {"Male", "Female", "Others"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // ملء الاختيارات أول ما البرنامج يفتح
        if (genderBox != null) genderBox.getItems().addAll(Genders);
        if (specialtyChoiceBox != null) specialtyChoiceBox.getItems().addAll(Specialties);


        // ربط التخصص بالدكاترة
        specialtyChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadDoctorsBySpecialty(newVal);
            }
        });
    }

    private void loadDoctorsBySpecialty(String specialty) {
        if (doctorChoiceBox == null) return;
        doctorChoiceBox.getItems().clear();

        try (BufferedReader br = new BufferedReader(new FileReader("DoctorData.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length >= 3) {
                    // استخدام trim() هنا هو السر
                    String docName = data[1].trim();
                    String docSpec = data[2].trim();

                    if (docSpec.equalsIgnoreCase(specialty.trim())) {
                        doctorChoiceBox.getItems().add(docName);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("File not found!");
        }

        if (doctorChoiceBox.getItems().isEmpty()) {
            doctorChoiceBox.getItems().add("No Doctors available");
        }
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
    public void AddNewPatient(ActionEvent a) {
        if (isInputValid()) {
            try {
                // حفظ المريض
                Patient p = new Patient(0, NameFeild.getText(), Integer.parseInt(AgeFeild.getText()), PhoneFeild.getText(), genderBox.getValue(), specialtyChoiceBox.getValue());
                saveToFile("PatientData.csv", p.toFileString());

                // حفظ الموعد
                Appointment app = new Appointment(NameFeild.getText(), doctorChoiceBox.getValue(), datePicker.getValue().toString(), "10:00");
                saveToFile("Appointments.csv", app.toFileString());

                System.out.println("Saved Successfully!");
                clearFields(a);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Check fields, something is empty!");
        }
    }

    private boolean isInputValid() {
        return NameFeild != null && !NameFeild.getText().isEmpty() &&
                doctorChoiceBox.getValue() != null &&
                datePicker.getValue() != null;
    }

    private void saveToFile(String fileName, String data) {
        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.write(data + "\n");
        } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    private void clearFields(ActionEvent e) {
        NameFeild.clear();
        AgeFeild.clear();
        PhoneFeild.clear();
        genderBox.getSelectionModel().clearSelection();
        specialtyChoiceBox.getSelectionModel().clearSelection();
        doctorChoiceBox.getSelectionModel().clearSelection();
        datePicker.setValue(null);
    }
}