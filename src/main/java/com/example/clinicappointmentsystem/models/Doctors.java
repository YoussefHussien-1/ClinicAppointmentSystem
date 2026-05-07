package com.example.clinicappointmentsystem.models;

public class Doctors {
    private int id;
    private String name;
    private String Specialty;
    private String phone;

    public Doctors(int id,String name , String Specialty , String phone){
        this.id = id;
        this.name = name ;
        this.Specialty = Specialty;
        this.phone = phone;
    }

    // getter
    public String getName(){return this.name;}
    public String getSpecialty(){return this.Specialty;}
    public String getPhone(){return this.phone;}



    public String toFileString() {
        return this.id + "," + this.name + "," + this.Specialty + "," + this.phone;
    }

}
