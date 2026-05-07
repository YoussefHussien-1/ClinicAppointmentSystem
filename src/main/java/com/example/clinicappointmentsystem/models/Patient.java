package com.example.clinicappointmentsystem.models;

import java.io.Serializable;

public class Patient implements Serializable {
    private int id;
    private String name;
    private int age;
    private String phone;
    private String gender;
    private String neededSpecialty; // الخانة الجديدة

    // Parameterized constructor - حدثناه ليشمل التخصص
    public Patient(int id, String name, int age, String phone, String gender, String neededSpecialty) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.gender = gender;
        this.neededSpecialty = neededSpecialty;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getPhone() { return phone; }
    public String getGender() { return gender; }
    public String getNeededSpecialty() { return neededSpecialty; }

    // تعديل ميثود الكتابة للملف لتشمل التخصص
    public String toFileString() {
        return this.id + "," + this.name + "," + this.age + "," +
                this.phone + "," + this.gender + "," + this.neededSpecialty;
    }
}