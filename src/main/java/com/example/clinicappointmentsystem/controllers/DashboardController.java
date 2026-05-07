package com.example.clinicappointmentsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML private Label totalPatientsLabel;
    @FXML private Label totalDoctorsLabel;
    @FXML private Label totalAppointmentsLabel;
    @FXML private PieChart specialtyPieChart;
    @FXML private BarChart<String, Number> appointmentsBarChart;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateStatistics();
        setupPieChart();
        setupBarChart();
    }


    @FXML
    void handleBackToMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/clinicappointmentsystem/Views/MainMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setMaximized(true); // عشان تفضل الشاشة كبيرة
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int countLines(String fileName){
        int count = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            while (br.readLine() != null) count++;
        }catch (Exception e){
            return 0;
        }
        return count;
    }

    private void updateStatistics(){
        totalPatientsLabel.setText(String.valueOf(countLines("PatientData.csv")));
        totalAppointmentsLabel.setText(String.valueOf(countLines("Appointments.csv")));
        totalDoctorsLabel.setText(String.valueOf(countLines("DoctorData.csv")));
    }


    private void setupPieChart() {
        // اللوجيك: بنفتح ملف الدكاترة ونعد كل تخصص فيه كام دكتور
        Map<String, Integer> specialtyCounts = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("DoctorData.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 2) {
                    String specialty = data[2];
                    specialtyCounts.put(specialty, specialtyCounts.getOrDefault(specialty, 0) + 1);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        // تحويل البيانات لشكل يفهمه الـ PieChart
        for (Map.Entry<String, Integer> entry : specialtyCounts.entrySet()) {
            specialtyPieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }


    private void setupBarChart() {
        // اللوجيك: بنفتح ملف المواعيد ونعد كل يوم فيه كام حجز
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Appointments");

        Map<String, Integer> dateCounts = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Appointments.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 2) {
                    String date = data[2]; // تاريخ الموعد
                    dateCounts.put(date, dateCounts.getOrDefault(date, 0) + 1);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        for (Map.Entry<String, Integer> entry : dateCounts.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        appointmentsBarChart.getData().add(series);
    }


}
