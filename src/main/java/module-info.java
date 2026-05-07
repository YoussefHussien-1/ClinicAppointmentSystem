module com.example.clinicappointmentsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.calendarfx.view;

    opens com.example.clinicappointmentsystem.controllers to javafx.fxml;

    opens com.example.clinicappointmentsystem.models to javafx.base;

    exports com.example.clinicappointmentsystem;

    exports com.example.clinicappointmentsystem.controllers;
}