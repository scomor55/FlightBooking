package ba.unsa.etf.rpr.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Register extends Application {


    public Button signinBtn;
    public TextField fieldUsername;

    public String tempUsername;
    public String tempPassword;
    public TextField fieldPassword;


    public static void main(String[] args) {
        // System.out.println("Hello World!");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
        stage.setTitle("Register");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

    public boolean signin(){

        try{

            Properties p = new Properties();
            p.load(ClassLoader.getSystemResource("application.properties.template").openStream());
            String url = p.getProperty("db.connection_string");
            String usr = p.getProperty("db.username");
            String pswd = p.getProperty("db.password");

            Connection conn = DriverManager.getConnection(url, usr, pswd);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO Users (username , password)"+
                    "VALUES (?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, fieldUsername.getText());
            preparedStatement.setString(2, fieldPassword.getText());

            preparedStatement.executeUpdate();


        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
    public void signinClick(ActionEvent actionEvent) {
        if (fieldUsername.getText().isEmpty()) {
            fieldUsername.getStyleClass().add("invalidField");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Niste unijeli korisničko ime");
            alert.setContentText("Korisničko ime ne smije biti prazno");

            alert.showAndWait();
            return;
        }

        boolean check = signin();
        if (!check) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Pogrijesio si");
            alert.setContentText("Greska Safete");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ispravan unos");
            alert.setHeaderText("Uspjesno ste kreirali racun ");
            alert.setContentText("Svaka cast");
            alert.showAndWait();
        }






    }
}
