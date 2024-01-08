package com.example.internexpress.controller;

import com.example.internexpress.domain.Entity;
import com.example.internexpress.domain.User;
import com.example.internexpress.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class MainViewController {

    @FXML
    private Label nameLabel;
    @FXML
    private Label birthdateLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label emailLabel;


    private Entity loggedEntity;
    private Stage stage;
    private UserService userService;


    public void setStage(Stage s) {
        this.stage = s;
    }

    public void setService(UserService userService, Entity entity) {
        this.userService = userService;
        this.loggedEntity = entity;

        if (this.loggedEntity instanceof User loggedUser) {
            nameLabel.setText(loggedUser.getFirstName() + " " + loggedUser.getLastName());
            birthdateLabel.setText(loggedUser.getDate());
            switch (loggedUser.getGender()) {
                case "F" -> genderLabel.setText("Female");
                case "M" -> genderLabel.setText("Male");
                default -> genderLabel.setText("Other");
            }
            emailLabel.setText(loggedUser.getEmail());
        } else {
            //to do pt companie
        }
    }


    @FXML
    public void initialize() {
    }



}
