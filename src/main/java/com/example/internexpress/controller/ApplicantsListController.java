package com.example.internexpress.controller;

import com.example.internexpress.domain.Internship;
import com.example.internexpress.domain.User;
import com.example.internexpress.service.InternshipService;
import com.example.internexpress.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.List;

public class ApplicantsListController {
    private UserService userService;
    private User loggedUser;
    private InternshipService internshipService;
    private Internship selectedInternship;

    @FXML
    private ListView<User> listApplicantsView;
    private ObservableList<User> internshipApplicants = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        listApplicantsView.setItems(internshipApplicants);
    }

    public void setService(UserService userService, User loggedUser, InternshipService internshipService, Internship selectedInternship) {
        this.userService = userService;
        this.loggedUser = loggedUser;
        this.internshipService = internshipService;
        this.selectedInternship = selectedInternship;
        initializeApplicantsList();
    }

    private void initializeApplicantsList() {
        List<User> allApplicants = selectedInternship.getApplicants();
        List<User> mockedList = new ArrayList<>();
        if (!allApplicants.isEmpty()) {
            internshipApplicants.setAll(allApplicants);
        } else {
            User user = new User();
            user.setFirstName("error");
            user.setLastName("error");
            mockedList.add(user);
            internshipApplicants.setAll(mockedList);

        }
        listApplicantsView.setCellFactory(param -> new XCell("Accept", "Reject",
                "-fx-background-color:  #ffccd5  ; -fx-text-fill: #800f2f; -fx-border-color: #800f2f;-fx-border-width: 0 2 2 0;",
                "-fx-background-color: #2196F3; -fx-text-fill:  #fff;-fx-border-color:  #90CAF9;-fx-border-width: 0 2 2 0;"));

    }


    class XCell extends ListCell<User> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button1 = new Button("");
        Button button2 = new Button("");
        String buttonText1;
        String buttonText2;
        int selectedButton=0;

        public XCell(String text1, String text2, String style1, String style2) {
            super();
            buttonText1 = text1;
            buttonText2 = text2;
            button1.setText(buttonText1);
            button1.setStyle(style1);
            button2.setText(buttonText2);
            button2.setStyle(style2);
            hbox.setStyle("-fx-alignment: center");
            hbox.getChildren().addAll(label, pane, button1, button2);
            label.setStyle("-fx-text-fill: #2196f3;");
            HBox.setHgrow(pane, Priority.ALWAYS);
            button1.setOnAction(e -> {
                User selected = getListView().getItems().get(getIndex());
                if (buttonText1.equals("Accept")) {
                    internshipService.handleAcceptAppliance(selected, selectedInternship, "Accepted");
                    internshipApplicants.setAll(selectedInternship.getApplicants());
                    selectedButton = 1;
                }
                updateItem(selected, false);
            });
            button2.setOnAction(e -> {
                User selected = getListView().getItems().get(getIndex());
                if (buttonText2.equals("Reject")) {
                    internshipService.handleAcceptAppliance(selected, selectedInternship, "Rejected");
                    internshipApplicants.setAll(selectedInternship.getApplicants());
                    selectedButton = 2;
                }
                updateItem(selected, false);
            });
        }

        @Override
        protected void updateItem(User item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            if (empty) {
                setGraphic(null);
            } else {
                if (selectedButton == 1) {
                    this.button1.setText("Already accepted");
                    this.button1.setDisable(true);
                    this.button2.setDisable(true);
                } else if (selectedButton == 2) {
                    this.button2.setText("Already rejected");
                    this.button2.setDisable(true);
                    this.button1.setDisable(true);
                }
                else{
                    if(selectedInternship.getAcceptedUsers().contains(item)){
                        this.button1.setText("Already accepted");
                        this.button1.setDisable(true);
                        this.button2.setDisable(true);
                    }
                }

                label.setText(item != null ? item.toString() : "<null>");
                setGraphic(hbox);
            }
        }
    }

}
