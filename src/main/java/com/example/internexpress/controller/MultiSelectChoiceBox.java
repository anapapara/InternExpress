package com.example.internexpress.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.stream.Collectors;

public class MultiSelectChoiceBox<T> extends StackPane {
    private final ChoiceBox<String> choiceBox;
    private final ListView<CheckBoxListItem<T>> listView;

    public MultiSelectChoiceBox() {
        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add("Select Options");
        choiceBox.setValue("Select Options");

        listView = new ListView<>();

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(choiceBox, new Label("Selected Options:"), listView);

        getChildren().add(hbox);
    }

    public ObservableList<T> getSelectedItems() {
        return listView.getItems().stream()
                .filter(CheckBoxListItem::isSelected)
                .map(CheckBoxListItem::getItem)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    public void setItems(ObservableList<T> items) {
        listView.getItems().clear();
        listView.getItems().addAll(items.stream().map(CheckBoxListItem::new).toList());
    }

    public ChoiceBox<String> getChoiceBox() {
        return choiceBox;
    }

    public ListView<CheckBoxListItem<T>> getListView() {
        return listView;
    }

    public static class CheckBoxListItem<T> {
        private final T item;
        private boolean selected;

        public CheckBoxListItem(T item) {
            this.item = item;
        }

        public T getItem() {
            return item;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}
