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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.Entities.Customer;
import main.Entities.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Loader implements Initializable {

    private User user = null;

    @FXML
    GridPane root;

    @FXML
    JFXTextField userTextField;

    @FXML
    JFXPasswordField passwordField;

    public void register (ActionEvent event) {
        loadRegister(root);
    }

    public void login (ActionEvent event) {
        checkUser();
    }

    private void checkUser () {
        rootUser();
        if (userTextField.getText().equals(user.getUserName()) && passwordField.getText().equals(user.getUserPassword())) {
            loadMain(root, user);
        }

    }

    private void rootUser () {
        User.Builder builder = new User.Builder(0, "PUBLIC", "root", "root")
                .email("root@root.co.uk")
                .address1("Hogwarts")
                .address2("DumbleDoor Office")
                .town("not Luton")
                .postCode("GL HF")
                .phoneNumber("123456789");
        user = new Customer(builder, "Harry", "Potter");
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
