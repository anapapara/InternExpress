package com.example.internexpress.controller;

import com.example.internexpress.domain.User;
import com.example.internexpress.service.UserService;
import com.example.internexpress.validator.ValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class SignUpAndLoginController {
    @FXML
    private TextField emailField1;
    @FXML
    private PasswordField passwordField1;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel1;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private ComboBox genderCombobox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField passwordField2;
    @FXML
    private TextField emailField2;
    @FXML
    private TextField companyNameField1;
    @FXML
    private Label errorLabel2;
    @FXML
    private HBox hboxCompany;

    @FXML
    private ComboBox userTypeCombox1;

    private UserService userService;

    @FXML
    public void initialize() {
        genderCombobox.getItems().addAll("Male", "Female", "Other");
        userTypeCombox1.getItems().addAll("Student", "Company");
        hboxCompany.setVisible(false);
    }

    public void setHboxCompanyName() {
        Object value = userTypeCombox1.getValue();
        if ("Company".equals(value)) {
            hboxCompany.setVisible(true);
        }
        else {
            hboxCompany.setVisible(false);
        }
    }

    public void setServices(UserService service) {
        this.userService = service;
    }

    public void loginAction() {
        try {
            User user = userService.login(emailField1.getText(), passwordField1.getText());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("main-view.fxml"));

            AnchorPane root = loader.load();
            MainViewController ctrl = loader.getController();
            ctrl.setService(userService, user);


            Stage dialogStage = new Stage();
            dialogStage.setTitle("Winternet");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);


            ctrl.setStage(dialogStage);
            dialogStage.show();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
        } catch (ValidationException e) {
            errorLabel1.setText(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signupAction() throws IOException {
        String gender;
        Object value = genderCombobox.getValue();
        if ("Male".equals(value)) {
            gender = "M";
        } else if ("Female".equals(value)) {
            gender = "F";
        } else {
            gender = "other";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = formatter.format(datePicker.getValue());
        try {
            Object value2 = userTypeCombox1.getValue();
            User user;
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root;


            if ("Company".equals(value2)) {
                user = userService.signup(firstNameField.getText(), lastNameField.getText(), date, gender,
                        emailField2.getText(), passwordField2.getText(), userTypeCombox1.getValue().toString(), companyNameField1.getText());
                loader.setLocation(getClass().getResource("main-view-company.fxml"));
                root = loader.load();
                MainViewControllerCompany ctrl = loader.getController();
                ctrl.setService(userService, user);
            }
            else{
                user = userService.signup(firstNameField.getText(), lastNameField.getText(), date, gender,
                        emailField2.getText(), passwordField2.getText(), userTypeCombox1.getValue().toString(), "");
                loader.setLocation(getClass().getResource("main-view.fxml"));
                root = loader.load();
                MainViewController ctrl = loader.getController();
                ctrl.setService(userService, user);
            }

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Winternet");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);


            dialogStage.show();
            Stage stage = (Stage) errorLabel2.getScene().getWindow();
            stage.close();
        } catch (ValidationException e) {
            errorLabel2.setText(e.getMessage());
        }
    }
}
