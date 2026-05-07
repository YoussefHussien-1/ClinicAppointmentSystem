package com.example.clinicappointmentsystem.controllers;

import com.example.clinicappointmentsystem.models.Doctors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DoctorController implements Initializable {
    private int id;
    private String name;
    private String phoneNumber;
    private String speciality;
    @FXML
    private ChoiceBox<String> SpecialityBox;

    private String[] Specialties = {"Cardiology", "Pediatrics", "Orthopedics", "Dermatology", "Neurology", "Internal Medicine","Dentistry", "Ophthalmology"};

    @FXML
    private TextField NameFeild;
    @FXML
    private TextField PhoneNumberFeild;
    @FXML
    void handleBackToMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/clinicappointmentsystem/Views/MainMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Clinic System - Main Menu");
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void AddNewDoctor(ActionEvent a){
        try{
            if(!NameFeild.getText().isEmpty() && !PhoneNumberFeild.getText().isEmpty() && SpecialityBox.getValue() != null){
                name = NameFeild.getText();
                phoneNumber = PhoneNumberFeild.getText();
                speciality = SpecialityBox.getValue();


                Doctors doctor = new Doctors(id,name , speciality, phoneNumber);
                id +=1;


                try(FileWriter write = new FileWriter("DoctorData.csv", true)){
                    write.write(doctor.toFileString() +  '\n');
                    System.out.println(doctor.toFileString());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


            }
            else{
                System.out.println("You can not submit with empty feild");
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }



    @FXML
    private void clearFields(ActionEvent e) {
        NameFeild.clear();
        PhoneNumberFeild.clear();
        SpecialityBox.setValue(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpecialityBox.getItems().addAll(Specialties);
    }
}
