package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        // FXML style
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Dummy Login App");
        primaryStage.setScene(new Scene(root, 300, 110));
        primaryStage.setResizable(false);
        primaryStage.show();

        /*
        // Class style
        Label userIdLbl = new Label("Username:");
        TextField userIdTxt = new TextField();
        Label userPwdLbl = new Label("Password:");
        PasswordField userPwdTxt = new PasswordField();
        GridPane root = new GridPane();
        root.setVgap(20);
        //root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);
        GridPane.setConstraints(userIdLbl, 0, 0);
        GridPane.setConstraints(userIdTxt, 1, 0);
        GridPane.setConstraints(userPwdLbl, 0, 1);
        GridPane.setConstraints(userPwdTxt, 1, 1);
        root.getChildren().addAll(userIdLbl, userIdTxt, userPwdLbl, userPwdTxt);
        Button signInBtn = new Button ("Login");
        signInBtn.setPrefWidth(Double.MAX_VALUE);
        root.add(signInBtn,0,2,2,1);
        Scene scene = new Scene(root,250,150);
        primaryStage.setScene(scene);
        primaryStage.show();
        */
    }

}