package com.example.internexpress.controller;

import com.example.internexpress.domain.User;
import com.example.internexpress.service.InternshipService;
import com.example.internexpress.service.UserService;
import com.example.internexpress.validator.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAnnouncementController {

    @FXML
    private ComboBox<String> internshipTypeBox;

    @FXML
    private TextField titleField;

    @FXML
    private TextField domainField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField durationField;

    @FXML
    private DatePicker startDate;


    @FXML
    private TextField detailsLinkField;

    @FXML
    private Button cancelButton;


    private UserService userService;
    private User loggedUser;

    private InternshipService internshipService;
    public void setService(UserService userService, User loggedUser, InternshipService internshipService) {
        this.userService = userService;
        this.loggedUser = loggedUser;
        this.internshipService = internshipService;
    }

    @FXML
    public void initialize(){
        internshipTypeBox.getItems().addAll("Face to face", "Hybrid", "Remote");

    }
    public void createAnnouncement(ActionEvent actionEvent) {
        internshipService.addInternship(titleField.getText(), durationField.getText(), domainField.getText(), internshipTypeBox.getValue(), startDate.getValue().toString(), descriptionField.getText(), detailsLinkField.getText(),loggedUser);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Your announcment was succesfully added!",ButtonType.CLOSE);
        alert.show();
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root;
            loader.setLocation(getClass().getResource("main-view-company.fxml"));
            root = loader.load();
            MainViewControllerCompany ctrl = loader.getController();
            ctrl.setService(userService, loggedUser, internshipService);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Winternet");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);


            dialogStage.show();
        }catch (ValidationException | IOException e) {
            System.out.println(e);
        }
    }
    public void cancelCreationOfAnnouncement(ActionEvent actionEvent) {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root;
            loader.setLocation(getClass().getResource("main-view-company.fxml"));
            root = loader.load();
            MainViewControllerCompany ctrl = loader.getController();
            ctrl.setService(userService, loggedUser, internshipService);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Winternet");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);


            dialogStage.show();
        }catch (ValidationException | IOException e) {
            System.out.println(e);
        }
    }
}
