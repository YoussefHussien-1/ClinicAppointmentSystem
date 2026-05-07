package com.example.clinicappointmentsystem.controllers;

import com.example.clinicappointmentsystem.models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class TableViewController implements Initializable {

    @FXML private TableView<Appointment> appointmentsTable;
    @FXML private TableColumn<Appointment, String> colPatientName;
    @FXML private TableColumn<Appointment, String> colDoctorName;
    @FXML private TableColumn<Appointment, String> colDate;
    @FXML private TableColumn<Appointment, String> colTime;

    @FXML private TextField searchField;

    private ObservableList<Appointment> masterData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. ربط عواميد الجدول بمتغيرات موديل الـ Appointment
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colDoctorName.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("appointmentTime"));

        // 2. تحميل البيانات من الملف
        loadDataFromCSV();

        // 3. لوجيك البحث اللحظي (Search Logic)
        setupSearchFilter();
    }

    private void loadDataFromCSV() {
        masterData.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("Appointments.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) {
                    masterData.add(new Appointment(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim()));
                }
            }
            appointmentsTable.setItems(masterData);
        } catch (Exception e) {
            System.out.println("Error loading CSV: " + e.getMessage());
        }
    }

    private void setupSearchFilter() {
        FilteredList<Appointment> filteredData = new FilteredList<>(masterData, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(appointment -> {
                if (newValue == null || newValue.isEmpty()) return true;

                String lowerCaseFilter = newValue.toLowerCase();
                if (appointment.getPatientName().toLowerCase().contains(lowerCaseFilter)) return true;
                if (appointment.getDoctorName().toLowerCase().contains(lowerCaseFilter)) return true;

                return false; // لم يجد تطابق
            });
        });
        appointmentsTable.setItems(filteredData);
    }

    @FXML
    void handleDelete(ActionEvent event) {
        Appointment selected = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            masterData.remove(selected);
            updateCSVFile(); // تحديث الملف بعد المسح
            System.out.println("Appointment Cancelled!");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select an appointment to cancel.");
            alert.showAndWait();
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

    private void updateCSVFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("Appointments.csv"))) {
            for (Appointment app : masterData) {
                pw.println(app.toFileString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void backToCalendar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/clinicappointmentsystem/Views/AppointmentScene.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }
}