package main.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.Entities.User;
import main.Interfaces.Notifications;
import main.Networking.JDBC;
import main.Utils.Loader;
import main.Utils.Validators;
import main.Utils.WriteLog;
import main.View.NotificationPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController extends Controller implements Initializable, Notifications {

    private final static Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    @FXML private BorderPane root;
    @FXML private HBox errorPane;
    @FXML private Text errorMessage;
    @FXML private JFXTextField userTextField;
    @FXML private JFXPasswordField passwordField;
    @FXML private JFXButton btnLogin, btnRegister;

    private Loader loader;
    private static LoginController instance = null;

    private LoginController() {
        instance = this;
        loader = Loader.getInstance();
        WriteLog.addHandler(LOGGER);
        LOGGER.log(Level.INFO, "Creating LoginController instance from constructor at: {0}\n", LocalTime.now());
    }

    public static LoginController getInstance() {
        if (instance == null) {
            synchronized (LoginController.class) {
                if (instance == null) {
                    return new LoginController();
                }
            }
        }
        return instance;
    }

    private void login () {

            if (Validators.validate(userTextField, passwordField)) {

                if (JDBC.isConnected()) {
                    String query = String.format("SELECT * FROM USERS WHERE (User_Name = '%s' OR User_Email = '%s') AND User_Password = '%s'",
                            userTextField.getText(), userTextField.getText(), passwordField.getText());
                    User user = (User) JDBC.get(query, User.class.getName());

                    if (user != null) loader.loadMain(root, user, RootController.getInstance());

                    else NotificationPane.show("Incorrect username or password");
                }
                else {
                    NotificationPane.show("No network connection");
                    JDBC.createConnection();
                }
            }
    }


    private void setActionListeners () {
        userTextField.focusedProperty().addListener(((observableValue, aBoolean, newValue) -> {
            if (!newValue) userTextField.validate();
        }) );

        passwordField.focusedProperty().addListener(((observableValue, aBoolean, newValue) -> {
            if (!newValue) passwordField.validate();
        }) );

        userTextField.setOnAction(e -> login());
        passwordField.setOnAction(e -> login());
        btnLogin.setOnAction(e -> login());
        btnRegister.setOnAction(e -> {
            loader.loadRegister(root, RegisterController.getInstance());
            Destroy();
        });
    }

    @Override
    public void initializeNotificationPane() {
        NotificationPane.createInstance(errorPane, errorMessage);
    }


    @Override
    public void initialize (URL url, ResourceBundle bundle) {

        initializeNotificationPane();

        if (!JDBC.isConnected()) {
            NotificationPane.show("No network connection");
            JDBC.createConnection();
        }

        // Setting validators
        RequiredFieldValidator validator = new RequiredFieldValidator();

        try {
            Image errorIcon = new Image(new FileInputStream("src/main/Resources/Drawable/error.png"));
            validator.setIcon(new ImageView(errorIcon));
        }
        catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "FileNotFoundException at: {0}; error message: {1}\n", new Object[]{LocalTime.now(), e.getMessage()});
        }

        userTextField.getValidators().add(validator);
        passwordField.getValidators().add(validator);

        setActionListeners();
    }


    private void Destroy () {
        if (instance != null) {
            LOGGER.log(Level.INFO, "Destroying LoginController instance at: {0}\n", LocalTime.now());
            instance = null;
        }
    }
}
