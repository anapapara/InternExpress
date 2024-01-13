package com.example.internexpress.controller;

import com.example.internexpress.domain.Internship;
import com.example.internexpress.domain.User;
import com.example.internexpress.service.InternshipService;
import com.example.internexpress.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MainViewControllerStudent {


    public Button createEventButton;
    @FXML
    public TextField universityDetailsField;
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
    private Button logoutButton;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailTextField;
    @FXML
    private ComboBox<String> genderCombobox;

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

    @FXML
    private TextField searchBar;

    private User loggedUser;
    private Stage stage;
    private UserService userService;

    private InternshipService internshipService;

    ObservableList<Internship> userInternships = FXCollections.observableArrayList();

    FilteredList<Internship> filteredList;

    ToggleButtonWithPopupExample controllerToggle;

    public void setStage(Stage s) {
        this.stage = s;
    }

    public void setService(UserService userService, User entity, InternshipService iservice) {
        this.userService = userService;
        this.loggedUser = entity;
        this.internshipService = iservice;
        controllerToggle = new ToggleButtonWithPopupExample(loggedUser);
        setFieldsValues();
        init();
        initializeEvents();
    }

    private void initializeEvents() {
        List<Internship> internships = new ArrayList<>();
        List<String> interestedAreas = loggedUser.getInterestedAreas();
        for (String s : interestedAreas)
            internships.addAll(internshipService.getInternshipsByDomain(s));

        List<Internship> myMockedList = new ArrayList<>();
        myMockedList.add(new Internship("MockedInternship", "3 months", "Law", "hybrid", "10.02.2024", "Lawyer wanna be", "link", loggedUser));
        if (!internships.isEmpty()) {
            userInternships.setAll(internships);
            listViewUserInternships.setCellFactory(param -> new XCell("Apply", "-fx-background-color:  #ffccd5  ; -fx-text-fill: #800f2f; -fx-border-color: #800f2f;-fx-border-width: 0 2 2 0;"));
        } else {
            userInternships.setAll(myMockedList);
        }


    }

    public void init() {
        List<Internship> internships = new ArrayList<>();
        List<String> interestedAreas = loggedUser.getInterestedAreas();
        for (String s : interestedAreas)
            internships.addAll(internshipService.getInternshipsByDomain(s));

        List<Internship> myMockedList = new ArrayList<>();
        myMockedList.add(new Internship("MockedInternship", "3 months", "Law", "hybrid", "10.02.2024", "Lawyer wanna be", "link", loggedUser));
        if (!internships.isEmpty()) {
            userInternships.setAll(internships);
        } else {
            userInternships.setAll(myMockedList);
        }

    }


    @FXML
    public void initialize() {
        genderCombobox.getItems().addAll("Male", "Female", "Other");
        this.filteredList= new FilteredList<>(userInternships);
        listViewUserInternships.setItems(filteredList);
        this.filteredList.setPredicate((internship)-> {return true;});
        this.searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            this.filteredList.setPredicate(this.createPredicate(newValue));
        });
    }

    private Predicate<? super Internship> createPredicate(String searchText) {
        return (internship) -> {
            if (searchText != null && !searchText.isEmpty() && !searchText.equals(" ")) {
                return internship.getTitle().contains(searchText) || internship.getInternshipType().contains(searchText) || internship.getDuration().contains(searchText)|| internship.getDomain().contains(searchText);
            } else {
                return true;
            }
        };
    }


    public void setFieldsValues() {
        firstNameField.setText(loggedUser.getFirstName());
        lastNameField.setText(loggedUser.getLastName());
        birthdateField.getEditor().setText(loggedUser.getDate());
        switch (loggedUser.getGender()) {
            case "Female" -> genderCombobox.setValue("Female");
            case "Male" -> genderCombobox.setValue("Male");
            default -> genderCombobox.setValue("Other");
        }
        universityDetailsField.setText(loggedUser.getGraduatedFrom());
        emailTextField.setText(loggedUser.getEmail());
        ToggleButton toggleButton = controllerToggle.getToggleButton();
        if (!hboxInterestedAreas.getChildren().contains(toggleButton))
            hboxInterestedAreas.getChildren().addAll(toggleButton);
    }

    public void updateProfile(ActionEvent actionEvent) {
        List<String> interestedAreas = controllerToggle.getSelectedCheckboxes();
        loggedUser = userService.updateStudentProfile(loggedUser.getId(), firstNameField.getText(), lastNameField.getText(), birthdateField.getEditor().getText(), genderCombobox.getValue(), emailTextField.getText(), loggedUser.getUserType(), interestedAreas, universityDetailsField.getText());
        setFieldsValues();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your profile has been successfully updated!", ButtonType.CLOSE);
        alert.show();
    }

    public void logoutAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login-register-WinternExpress.fxml"));

        BorderPane root = loader.load();

        SignUpAndLoginController ctrl = loader.getController();
        ctrl.setServices(userService, internshipService);

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Winternet");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        dialogStage.show();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }

    public void searchInternship(){
//        String domain = "";
//        for(String i : loggedUser.getInterestedAreas()){
//            domain+=i;
//            domain+=",";
//        }
//        List<Internship> internships = internshipService.getInternshipsByDomain(domain.substring(0,domain.length()-1));
//        userInternships.setAll(internships);
//        FilteredList<Internship> filteredList = new FilteredList<>(userInternships, x -> true);
//        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
//            filteredList.setPredicate(internship -> newValue.isEmpty() || newValue.isBlank()
//                    || internship.getTitle().startsWith(newValue));
//
//        });
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
                ctrl.setService(userService, loggedUser, internshipService, selectedInternship);
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

                Internship selected = getListView().getItems().get(getIndex());
                internshipService.addAppliance(selected, loggedUser);

                List<Internship> internships = new ArrayList<>();
                List<String> interestedAreas = loggedUser.getInterestedAreas();
                for (String s : interestedAreas)
                    internships.addAll(internshipService.getInternshipsByDomain(s));
                userInternships.setAll(internships);

            });

        }

        @Override
        protected void updateItem(Internship item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            if (empty) {
                setGraphic(null);
            } else {
                if (button.getText().equals("Apply")) {
                    this.button.setText("Pending");
                    this.button.setDisable(true);
                    //this.button.setStyle("-fx-background-color: #ffA500; -fx-text-fill: #FFFFFF;");
                }
                if (internshipService.getApplianceStatus(loggedUser.getId(), getListView().getItems().get(getIndex()).getId()).equals("Pending")) {
                    this.button.setText("Pending");
                    this.button.setDisable(true);
                    this.button.setStyle("-fx-background-color: #ffA500; -fx-text-fill: #FFFFFF;");
                } else if (internshipService.getApplianceStatus(loggedUser.getId(), getListView().getItems().get(getIndex()).getId()).equals("Accepted")) {
                    this.button.setText("Accepted");
                    this.button.setDisable(true);
                    this.button.setStyle("-fx-background-color: #00ff00; -fx-text-fill: #FFFFFF;");
                } else if (internshipService.getApplianceStatus(loggedUser.getId(), getListView().getItems().get(getIndex()).getId()).equals("Rejected")) {
                    this.button.setText("Rejected");
                    this.button.setDisable(true);
                    this.button.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #FFFFFF;");
                } else {
                    this.button.setText(btntext);
                    this.button.setDisable(false);
                }
                label.setText(item != null ? item.toString() : "<null>");
                setGraphic(hbox);

            }
        }
    }

}
