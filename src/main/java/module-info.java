module com.example.internexpress {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.internexpress to javafx.fxml;
    exports com.example.internexpress;
    exports com.example.internexpress.controller;
    exports com.example.internexpress.service;
    exports com.example.internexpress.domain;
    exports com.example.internexpress.repository;
    exports com.example.internexpress.validator;
    opens com.example.internexpress.controller to javafx.fxml;
    opens com.example.internexpress.domain to javafx.fxml;
}