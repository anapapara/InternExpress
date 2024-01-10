package com.example.internexpress.controller;

import com.example.internexpress.domain.User;
import com.example.internexpress.service.InternshipService;
import com.example.internexpress.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
    }
}
