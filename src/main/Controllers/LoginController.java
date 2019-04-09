package main.Controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.Entities.Bands;
import main.Entities.Event;
import main.Entities.User;
import main.Interfaces.NotificationPane;
import main.Networking.JDBC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class LoginController implements Initializable, NotificationPane {

    @FXML
    BorderPane root;

    @FXML
    private HBox errorPane;

    @FXML
    private Text errorMessage;

    @FXML
    private JFXTextField userTextField;

    @FXML
    private JFXPasswordField passwordField;

    private User user = null;
    private Loader loader;
    private JDBC database = null;
    private Connection connection = null;

    public LoginController() {
        loader = new Loader();
        database = new JDBC();
    }

    @FXML
    private void register (ActionEvent e) {
        loader.loadRegister(root);
    }

    @FXML
    private void login (ActionEvent e) {
        if (checkConnection()) {

            if (userTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                setNotificationPane("Some fields are empty", null);
            }
            else {
                String query = String.format("SELECT * FROM USERS WHERE User_Name = '%s' AND User_Password = '%s'",
                        userTextField.getText(), passwordField.getText());
                user = (User) database.get(query, User.class.getName());


                //User user = database.verifyLogin(userTextField.getText(), passwordField.getText());

                if (user != null) loader.loadMain(root, user);
                else setNotificationPane("Incorrect username or password", null);
            }
        }
    }

    private boolean checkConnection () {
        connection = database.getConnection();
        if (connection == null) {
            setNotificationPane("No Network Connection", null);
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void setNotificationPane(String message, String color) {
        if (message != null) {
            errorPane.setVisible(true);
            errorMessage.setText(message);
            Task task = hideNotificationPane();
            new Thread(task).start();
        }
    }

    @Override
    public Task hideNotificationPane() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                Thread.sleep(2000);
                errorPane.setVisible(false);
                return true;
            }
        };

    }

    @Override
    public void initialize (URL url, ResourceBundle bundle) {
        checkConnection();
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
