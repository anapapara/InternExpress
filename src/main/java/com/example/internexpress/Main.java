package com.example.internexpress;

import com.example.internexpress.controller.SignUpAndLoginController;

import com.example.internexpress.domain.User;
import com.example.internexpress.repository.Repository;
import com.example.internexpress.repository.UserRepository;
import com.example.internexpress.service.UserService;
import com.example.internexpress.validator.UserValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {

    private static Stage stage;
    private String dbPath = "E:\\re\\InternExpress\\src\\main\\resources\\bd.properties";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Properties props = new Properties();
        try {
            props.load(new FileReader(dbPath));
        } catch (Exception var13) {
            System.out.println(var13);
        }

        Repository<Long, User> userDatabaseRepository = new UserRepository(props);
        UserService userService = new UserService(userDatabaseRepository, new UserValidator());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("controller/login-view.fxml"));
        BorderPane root = loader.load();
        SignUpAndLoginController ctrl = loader.getController();
        ctrl.setServices(userService);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Winternet");
        primaryStage.show();
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stage.getScene().setRoot(pane);

    }

}
