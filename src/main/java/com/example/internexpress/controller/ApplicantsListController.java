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
        listApplicantsView.setCellFactory(param -> new XCell("Accept", "-fx-background-color:  #ffccd5  ; -fx-text-fill: #800f2f; -fx-border-color: #800f2f;-fx-border-width: 0 2 2 0;"));
        listApplicantsView.setCellFactory(param -> new XCell("Reject", "-fx-background-color: #2196F3; -fx-text-fill:  #fff;-fx-border-color:  #90CAF9;-fx-border-width: 0 2 2 0;"));

    }


    class XCell extends ListCell<User> {
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
                if (buttonText.equals("Accept")) {
                    User selected = getListView().getItems().get(getIndex());
                    ApplicantsListController.this.internshipService.handleAppliance(selected, selectedInternship, "Accepted");
                    internshipApplicants.setAll(selectedInternship.getApplicants());
                    updateItem(selected, false);
                } else {
                    User selected = getListView().getItems().get(getIndex());
                    ApplicantsListController.this.internshipService.handleAppliance(selected, selectedInternship, "Rejected");
                    internshipApplicants.setAll(selectedInternship.getApplicants());
                    updateItem(selected, false);
                }
            });
        }

        @Override
        protected void updateItem(User item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            if (empty) {
                setGraphic(null);
            } else {
                this.button.setText(btntext);
                this.button.setDisable(false);
                label.setText(item != null ? item.toString() : "<null>");
                setGraphic(hbox);
            }
        }
    }

}
