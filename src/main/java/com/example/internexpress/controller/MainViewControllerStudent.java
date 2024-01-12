package com.example.internexpress.controller;

import com.example.internexpress.domain.Internship;
import com.example.internexpress.domain.User;
import com.example.internexpress.service.InternshipService;
import com.example.internexpress.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainViewControllerStudent {


    public Button createEventButton;
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
    private Button openCreateViewButton;

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
    @FXML
    private HBox hboxInterestedAreas;
    @FXML
    private ChoiceBox<String> interestedAreasChoiseBox;

    private User loggedUser;
    private Stage stage;
    private UserService userService;

    private InternshipService internshipService;

    ObservableList<Internship> userInternships = FXCollections.observableArrayList();
    ToggleButtonWithPopupExample controllerToggle = new ToggleButtonWithPopupExample();

    public void setStage(Stage s) {
        this.stage = s;
    }

    public void setService(UserService userService, User entity,InternshipService iservice) {
        this.userService = userService;
        this.loggedUser = entity;
        this.internshipService = iservice;
        setFieldsValues();
        init();
        initializeEvents();


    }

    private void initializeEvents() {
        List<Internship> myInternships = internshipService.getInternshipsByCreator(loggedUser.getId());
        List<Internship> myMockedList = new ArrayList<>();
        myMockedList.add(new Internship("MockedInternship","3 months","Law","hybrid","10.02.2024","Lawyer wanna be","link",loggedUser));
        if(myInternships.size() != 0){
            userInternships.setAll(myInternships);
            listViewUserInternships.setCellFactory(param -> new XCell("View applicants", "-fx-background-color:  #ffccd5  ; -fx-text-fill: #800f2f; -fx-border-color: #800f2f;-fx-border-width: 0 2 2 0;"));
        }else{
            userInternships.setAll(myMockedList);
            listViewUserInternships.setCellFactory(param -> new XCell("View applicants", "-fx-background-color:  #ffccd5  ; -fx-text-fill: #800f2f; -fx-border-color: #800f2f;-fx-border-width: 0 2 2 0;"));
        }



    }

    public void init(){
        List<Internship> myInternships = internshipService.getInternshipsByCreator(loggedUser.getId());
        List<Internship> myMockedList = new ArrayList<>();
        myMockedList.add(new Internship("MockedInternship","3 months","Law","hybrid","10.02.2024","Lawyer wanna be","link",loggedUser));
        if(myInternships.size()!=0){
            userInternships.setAll(myInternships);
        }else{
            userInternships.setAll(myMockedList);
        }

    }


    @FXML
    public void initialize() {
        listViewUserInternships.setItems(userInternships);
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

         ObservableList<String> interestedAreasObservableList =  FXCollections.observableArrayList("Computer Science","Law","Art");
         ToggleButton toggleButton = controllerToggle.getToggleButton();

         // Adding the MultiSelectChoiceBox to the existing HBox
         hboxInterestedAreas.getChildren().addAll(toggleButton);

     }
     /*
    MARIA HELP
    */
    public void updateProfile(ActionEvent actionEvent) {
        //controllerToggle.selectCheckBox("Law");
        //List<String> testlist = controllerToggle.getCheckedValues();
        List<CheckBox> list = controllerToggle.getSelectedCheckBoxes();
        loggedUser = userService.updateCompanyProfile(loggedUser.getId(),firstNameField.getText(), lastNameField.getText(), birthdateField.getEditor().getText(),genderTextField.getText(),emailTextField.getText(), loggedUser.getUserType(), companyNameField.getText(), companyDetailsField.getText(),companyLinkField.getText());
        setFieldsValues();
    }


    public void openCreateView(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("create-internship-anouncement-view.fxml"));
            AnchorPane root = loader.load();
            CreateAnnouncementController ctrl = loader.getController();
            ctrl.setService(userService,loggedUser,internshipService);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create announcement");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.setOnCloseRequest(h -> userInternships.setAll(internshipService.getInternshipsByCreator(loggedUser.getId())));
            dialogStage.show();
            Stage stage = (Stage) openCreateViewButton.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //INNER CLASSES ------------------------------------
    class XCell extends ListCell<Internship> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("");
        String btntext;

        public void openApplicantListView(ActionEvent actionEvent, Internship selectedInternship) throws IOException {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("view-applicants.fxml"));
                AnchorPane root = loader.load();
                ApplicantsListController ctrl = loader.getController();
                ctrl.setService(userService,loggedUser,internshipService, selectedInternship);
                Stage dialogStage = new Stage();
                dialogStage.setTitle("View applicants");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);
                dialogStage.setOnCloseRequest(h -> userInternships.setAll(internshipService.getInternshipsByCreator(loggedUser.getId())));
                dialogStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                try {
                    openApplicantListView(e, selected);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

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
