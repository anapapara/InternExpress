package com.example.internexpress.controller;

import com.example.internexpress.domain.Internship;
import com.example.internexpress.domain.User;
import com.example.internexpress.service.InternshipService;
import com.example.internexpress.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;

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
    private ListView<Internship> listViewUserInternships;

    @FXML
    Button updateCompanyButton;

    private User loggedUser;
    private Stage stage;
    private UserService userService;

    private InternshipService internshipService;

    ObservableList<Internship> userInternships = FXCollections.observableArrayList();

    public void setStage(Stage s) {
        this.stage = s;
    }

    public void setService(UserService userService, User entity,InternshipService iservice) {
        this.userService = userService;
        this.loggedUser = entity;
        this.internshipService = iservice;
        setFieldsValues();
        initializeEvents();


    }

    private void initializeEvents() {
        //suggestedEvents.setAll(eventService.getSuggestedEventsForUser(networkService.getLoggedUser()));
        List<Internship> myInternships = internshipService.getInternshipsByCreator(loggedUser.getId());
        List<Internship> myMockedList = new ArrayList<>();
        myMockedList.add(new Internship("MockedInternship","3 months","Law","hybrid","10.02.2024","Lawyer wanna be","link",loggedUser));
        if(myInternships != null){
            userInternships.setAll(myInternships);
            //listViewSuggestedEvents.setCellFactory(param -> new XCell("Subscribe", "-fx-background-color: #2196F3; -fx-text-fill:  #fff;-fx-border-color:  #90CAF9;-fx-border-width: 0 2 2 0;"));
            listViewUserInternships.setCellFactory(param -> new XCell("View applicants", "-fx-background-color:  #ffccd5  ; -fx-text-fill: #800f2f; -fx-border-color: #800f2f;-fx-border-width: 0 2 2 0;"));
        }else{
            userInternships.setAll(myMockedList);
            listViewUserInternships.setCellFactory(param -> new XCell("View applicants", "-fx-background-color:  #ffccd5  ; -fx-text-fill: #800f2f; -fx-border-color: #800f2f;-fx-border-width: 0 2 2 0;"));
        }



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



    //INNER CLASSES ------------------------------------
    class XCell extends ListCell<Internship> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("");
        String btntext;

        public XCell(String buttonText, String style) {
            super();
            btntext = buttonText;
            button.setText(buttonText);
            button.setStyle(style);
            hbox.setStyle("-fx-alignment: center");
            hbox.getChildren().addAll(label, pane, button);
            label.setStyle("-fx-text-fill: #2196f3;");
            HBox.setHgrow(pane, Priority.ALWAYS);
            button.setOnAction(e -> {
                //if (buttonText.equals("View applicants")) {

                    Internship selected = getListView().getItems().get(getIndex());
                    //ManageFriendsController.this.eventService.subscribeUser(selected, ManageFriendsController.this.networkService.getLoggedUser());


                    //suggestedEvents.setAll(eventService.getSuggestedEventsForUser(networkService.getLoggedUser()));
                    userInternships.setAll(internshipService.getInternshipsByCreator(loggedUser.getId()));
                    //notifications.setAll(eventService.getEventsForNotification(networkService.getLoggedUser()));
                    //updateNotificationLabel();

                    updateItem(selected, false);

//                } else {
//                    Internship selected = getListView().getItems().get(getIndex());
//                    //ManageFriendsController.this.eventService.unsubscribeUser(selected, ManageFriendsController.this.networkService.getLoggedUser());
//
//                    //suggestedEvents.setAll(eventService.getSuggestedEventsForUser(networkService.getLoggedUser()));
//                    userInternships.setAll(internshipService.getInternshipsByCreator(loggedUser.getId()));
//                    //notifications.setAll(eventService.getEventsForNotification(networkService.getLoggedUser()));
//                    //updateNotificationLabel();
//
//                    updateItem(selected, false);
//
//                }
            });

        }

        @Override
        protected void updateItem(Internship item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            if (empty) {
                setGraphic(null);
            } else {
//                if (ChronoUnit.HOURS.between(LocalDateTime.now(), LocalDateTime.parse(item.getStartDate())) < 0) {
//                    this.button.setText("Can't subscribe");
//                    this.button.setDisable(true);
//                } else {
//                    this.button.setText(btntext);
//                    this.button.setDisable(false);
//                }
                label.setText(item != null ? item.toString() : "<null>");
                setGraphic(hbox);

            }
        }
    }

}
