package com.example.internexpress.controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ToggleButtonWithPopupExample {

    private final ToggleButton toggleButton;
    private final Popup popup;
    private TreeItem<CheckBox> root ;

    private final ObservableList<CheckBox> selectedCheckBoxes;
    public TreeItem<CheckBox> getTreeItem(){
        return root;
    }
    public void selectCheckBox(String checkBoxText) {
        for (TreeItem<CheckBox> child : root.getChildren()) {
            CheckBox checkBox = child.getValue();
            System.out.println("TEXT---------------"+ checkBox.getText());
            if (checkBox.getText().equals(checkBoxText)) {
                checkBox.setSelected(true);
            }
        }
    }
    public List<String> getCheckedValues() {
        List<String> list = new ArrayList<>();
        for (TreeItem<CheckBox> child : root.getChildren()) {
            CheckBox checkBox = child.getValue();
            System.out.println("TEXT---------------"+ checkBox.getText());
            if (checkBox.isSelected()) {
                list.add(checkBox.getText());
            }
        }
        return list;
    }

    public ToggleButtonWithPopupExample() {

        toggleButton = new ToggleButton("Show Job Areas");

        // Create CheckBox instances for job areas
        CheckBox checkBoxComputerScience = new CheckBox("Computer Science");
        CheckBox checkBoxLaw = new CheckBox("Law");
        CheckBox checkBoxHealthcare = new CheckBox("Healthcare");
        CheckBox checkBoxBusiness = new CheckBox("Business");
        CheckBox checkBoxEducation = new CheckBox("Education");
        CheckBox checkBoxEngineering = new CheckBox("Engineering");
        CheckBox checkBoxScience = new CheckBox("Science");
        CheckBox checkBoxArts = new CheckBox("Creative Arts");
        CheckBox checkBoxSocialSciences = new CheckBox("Social Sciences");
        CheckBox checkBoxCommunicationMedia = new CheckBox("Communication & Media");
        CheckBox checkBoxAgriculture = new CheckBox("Agriculture");
        CheckBox checkBoxHospitality = new CheckBox("Hospitality & Tourism");

        root =  new TreeItem<>(new CheckBox("Job Areas"));
        root.getChildren().addAll(
                new TreeItem<>(checkBoxComputerScience),
                new TreeItem<>(checkBoxLaw),
                new TreeItem<>(checkBoxHealthcare),
                new TreeItem<>(checkBoxBusiness),
                new TreeItem<>(checkBoxEducation),
                new TreeItem<>(checkBoxEngineering),
                new TreeItem<>(checkBoxScience),
                new TreeItem<>(checkBoxArts),
                new TreeItem<>(checkBoxSocialSciences),
                new TreeItem<>(checkBoxCommunicationMedia),
                new TreeItem<>(checkBoxAgriculture),
                new TreeItem<>(checkBoxHospitality)
        );

        TreeView<CheckBox> treeView = new TreeView<>(root);
        treeView.setShowRoot(false);

        // Set a custom StringConverter
        treeView.setCellFactory(param -> new CheckBoxTreeCell<CheckBox>() {
            @Override
            public void updateItem(CheckBox item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.getText());
                }
            }
        });

        // Create a Popup for the checkboxes
        popup = new Popup();
        popup.getContent().add(treeView);

        // Handle the toggle button action
        toggleButton.setOnAction(event -> {
            if (toggleButton.isSelected()) {
                // Show the popup
                popup.show(toggleButton.getScene().getWindow());
                toggleButton.setText("Close the list");
            } else {
                // Hide the popup
                popup.hide();
                toggleButton.setText("Show Job Areas");
            }
        });
        selectedCheckBoxes = FXCollections.observableArrayList();
        treeView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<TreeItem<CheckBox>>) change -> {
            selectedCheckBoxes.clear();
            while (change.next()) {
                if (change.wasAdded()) {
                    for (TreeItem<CheckBox> item : change.getAddedSubList()) {
                        selectedCheckBoxes.add(item.getValue());
                    }
                }
                if (change.wasRemoved()) {
                    for (TreeItem<CheckBox> item : change.getRemoved()) {
                        selectedCheckBoxes.remove(item.getValue());
                    }
                }
            }
            System.out.println("Selected Checkboxes: " + selectedCheckBoxes);
        });
    }

    public ToggleButton getToggleButton() {
        return toggleButton;
    }
    public ObservableList<CheckBox> getSelectedCheckBoxes() {
        return selectedCheckBoxes;
    }
}