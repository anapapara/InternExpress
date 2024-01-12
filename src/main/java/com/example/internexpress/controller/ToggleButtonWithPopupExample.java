package com.example.internexpress.controller;

import com.example.internexpress.domain.User;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.input.MouseEvent;
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
    TreeItem<String> rootItem = new TreeItem<>("Job Areas");

    private ObservableList<CheckBoxTreeItem<String>> selectedCheckboxes = FXCollections.observableArrayList();


    public ToggleButtonWithPopupExample(User loggedUser) {
        toggleButton = new ToggleButton("Show Job Areas");

        CheckBoxTreeItem<String> item1 = new CheckBoxTreeItem<>("Computer Science");
        CheckBoxTreeItem<String> item2 = new CheckBoxTreeItem<>("Law");
        CheckBoxTreeItem<String> item3 = new CheckBoxTreeItem<>("Healthcare");
        CheckBoxTreeItem<String> item4 = new CheckBoxTreeItem<>("Business");
        CheckBoxTreeItem<String> item5 = new CheckBoxTreeItem<>("Education");
        CheckBoxTreeItem<String> item6 = new CheckBoxTreeItem<>("Engineering");
        CheckBoxTreeItem<String> item7 = new CheckBoxTreeItem<>("Science");
        CheckBoxTreeItem<String> item8 = new CheckBoxTreeItem<>("Creative Arts");
        CheckBoxTreeItem<String> item9 = new CheckBoxTreeItem<>("Social Sciences");
        CheckBoxTreeItem<String> item10 = new CheckBoxTreeItem<>("Communication & Media");
        CheckBoxTreeItem<String> item11 = new CheckBoxTreeItem<>("Agriculture");
        CheckBoxTreeItem<String> item12 = new CheckBoxTreeItem<>("Hospitality & Tourism");


        rootItem.getChildren().addAll(item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, item12);

        List<String> interestedAreas = loggedUser.getInterestedAreas();
        if (interestedAreas != null) {
            if(interestedAreas.contains("Computer Science")) {item1.setSelected(true);selectedCheckboxes.add(item1);}
            if(interestedAreas.contains("Law")) {item2.setSelected(true);selectedCheckboxes.add(item2);}
            if(interestedAreas.contains("Healthcare")) {item3.setSelected(true);selectedCheckboxes.add(item3);}
            if(interestedAreas.contains("Business")) {item4.setSelected(true);selectedCheckboxes.add(item4);}
            if(interestedAreas.contains("Education")) {item5.setSelected(true);selectedCheckboxes.add(item5);}
            if(interestedAreas.contains("Engineering")) {item6.setSelected(true);selectedCheckboxes.add(item6);}
            if(interestedAreas.contains("Science")) {item7.setSelected(true);selectedCheckboxes.add(item7);}
            if(interestedAreas.contains("Creative Arts")) {item8.setSelected(true);selectedCheckboxes.add(item8);}
            if(interestedAreas.contains("Social Sciences")) {item9.setSelected(true);selectedCheckboxes.add(item9);}
            if(interestedAreas.contains("Communication & Media")) {item10.setSelected(true);selectedCheckboxes.add(item10);}
            if(interestedAreas.contains("Agriculture")) {item11.setSelected(true);selectedCheckboxes.add(item11);}
            if(interestedAreas.contains("Hospitality & Tourism")) {item12.setSelected(true);selectedCheckboxes.add(item12);}
        }


        TreeView<String> treeView = new TreeView<>(rootItem);
        treeView.setShowRoot(false);
        treeView.setCellFactory(CheckBoxTreeCell.forTreeView());

        addCheckBoxListener(item1);
        addCheckBoxListener(item2);
        addCheckBoxListener(item3);
        addCheckBoxListener(item4);
        addCheckBoxListener(item5);
        addCheckBoxListener(item6);
        addCheckBoxListener(item7);
        addCheckBoxListener(item8);
        addCheckBoxListener(item9);
        addCheckBoxListener(item10);
        addCheckBoxListener(item11);
        addCheckBoxListener(item12);


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

    }

    private void addCheckBoxListener(CheckBoxTreeItem<String> checkBoxTreeItem) {
        checkBoxTreeItem.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                selectedCheckboxes.add(checkBoxTreeItem);
            } else {
                selectedCheckboxes.remove(checkBoxTreeItem);
            }
        });
    }

    public ToggleButton getToggleButton() {
        return toggleButton;
    }


    public List<String> getSelectedCheckboxes() {
        List<String> selectedValues = new ArrayList<>();
        for (var x : selectedCheckboxes)
            selectedValues.add(x.getValue());
        return selectedValues;
    }


}