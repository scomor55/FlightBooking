package ba.unsa.etf.rpr.controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AdminUsersController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminUser.fxml"));
        stage.setTitle("User management");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }
/* Implementation of functionalities starts here */
    public void addUser(ActionEvent actionEvent) {

    }

    public void updateUser(ActionEvent actionEvent) {

    }

    public void deleteUser(ActionEvent actionEvent) {

    }
}
