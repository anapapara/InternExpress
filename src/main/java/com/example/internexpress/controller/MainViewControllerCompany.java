package com.example.internexpress.controller;

import com.example.internexpress.domain.Entity;
import com.example.internexpress.domain.User;
import com.example.internexpress.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainViewControllerCompany {


    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label birthdateLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label emailLabel;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailTextField;
    @FXML
    private TextField genderTextField;

    @FXML
    private DatePicker birthdateField;

    @FXML
    private TextField companyNameField;

    @FXML
    private TextField companyDetailsField;

    @FXML
    private TextField companyLinkField;



    @FXML
    Button updateCompanyButton;

    private User loggedUser;
    private Stage stage;
    private UserService userService;


    public void setStage(Stage s) {
        this.stage = s;
    }

    public void setService(UserService userService, User entity) {
        this.userService = userService;
        this.loggedUser = entity;
        setFieldsValues();



    }


    @FXML
    public void initialize() {
    }


     public void setFieldsValues(){
         firstNameField.setText(loggedUser.getFirstName());
         lastNameField.setText(loggedUser.getLastName());
         birthdateField.getEditor().setText(loggedUser.getDate());
         switch (loggedUser.getGender()) {
             case "F" -> genderTextField.setText("Female");
             case "M" -> genderTextField.setText("Male");
             default -> genderTextField.setText("Other");
         }
         emailTextField.setText(loggedUser.getEmail());
         companyNameField.setText(loggedUser.getCompanyName());
     }
    public void updateProfile(ActionEvent actionEvent) {
        loggedUser = userService.updateCompanyProfile(loggedUser.getId(),firstNameField.getText(), lastNameField.getText(), birthdateField.getEditor().getText(),genderTextField.getText(),emailTextField.getText(), loggedUser.getUserType(), companyNameField.getText(), companyDetailsField.getText(),companyLinkField.getText());
        setFieldsValues();
    }
}
