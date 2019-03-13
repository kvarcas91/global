package main.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private final String username = "root";
    private final String password = "root";

    @FXML
    JFXTextField userTextField;

    @FXML
    JFXPasswordField passwordField;

    @FXML
    void pressMe (ActionEvent event) {
        checkUser();
    }

    private void checkUser () {

        String message;

        if (userTextField.getText().equals(username) && passwordField.getText().equals(password))
            message = "Correct";
        else message = "Incorrect";
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
