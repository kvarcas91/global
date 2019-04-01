package main.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private final String username = "root";
    private final String password = "root";
    private static LoginController instance;

    public LoginController () {
        instance = this;
    }

    public static LoginController getInstance()
    {
        return instance;
    }
    public String userName() {
        return username;
    }

    @FXML
    GridPane root;

    @FXML
    JFXTextField userTextField;

    @FXML
    JFXPasswordField passwordField;

    @FXML
    Label userName;

    @FXML
    void pressMe (ActionEvent event) {
        checkUser();
    }

    private void checkUser () {

        String message;

        if (userTextField.getText().equals(username) && passwordField.getText().equals(password))
            loadMain();
        else message = "Incorrect";
    }

    private void loadMain () {


        try {
            /*GridPane pane = FXMLLoader.load(getClass().getResource("../UI/root.fxml"));
            root.getChildren().setAll(pane);

            RootController rootController = new RootController();
            rootController.setUser("test bla bla bla"); */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../UI/root.fxml"));
            Parent parent = loader.load();
            RootController rootController = loader.getController();
            rootController.setUser("test user from login");
            Stage stage = new Stage();
            stage.setWidth(1000);
            stage.setHeight(600);
            stage.setResizable(true);
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            Rectangle2D primScreenBound = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBound.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBound.getHeight() - stage.getHeight()) / 4);
            stage.setScene(new Scene(parent));
            stage.show();
            Stage loginStage = (Stage) root.getScene().getWindow();
            loginStage.close();

        }
        catch (IOException e){
            e.printStackTrace();
        }




        /*Stage stage = (Stage) root.getScene().getWindow();
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setResizable(true);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        Rectangle2D primScreenBound = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBound.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBound.getHeight() - stage.getHeight()) / 4); */

    }

    public void initialize (URL url, ResourceBundle bundle) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        userTextField.getValidators().add(validator);
        passwordField.getValidators().add(validator);

        userTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    userTextField.validate();
                }
            }
        });

        passwordField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!newValue) passwordField.validate();
            }
        });

        try {
            Image errorIcon = new Image(new FileInputStream("src/main/Resources/Drawable/error.png"));
            validator.setIcon(new ImageView(errorIcon));
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        }

}
