package main.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import main.Entities.User;
import main.Interfaces.Notifications;
import main.Networking.JDBC;
import main.Utils.Loader;
import main.Utils.Validators;
import main.Utils.WriteLog;
import main.View.Circles;
import main.View.NotificationPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController extends Controller implements Initializable, Notifications, ChangeListener<Toggle>{

    private static final Logger LOGGER = Logger.getLogger(RegisterController.class.getName());

    @FXML private BorderPane root;
    @FXML private HBox progressLayout, accTypeLayout, errorPane;
    @FXML private GridPane userNameLayout, optionalLayout;
    @FXML private JFXRadioButton first, second;
    @FXML private JFXTextField firstName, lastName, organisationName, userName, address1Field, address2Field, townField,
            postCodeField, emailField, webAddressField, phoneNumberField;

    @FXML private JFXPasswordField passwordField, verifyPasswordField;
    @FXML private JFXButton register, btnLogin;
    @FXML private ToggleGroup toggleGroup;
    @FXML private Text registerText, errorMessage;
    @FXML private ImageView btnBack, btnNext;

    private byte position = 0;
    private HashMap<String, Pane> scenes = new HashMap<>();
    private String[] labelText = new String[3];

    private AccountTypes accType = AccountTypes.NONE;

    private static RegisterController instance = null;
    private Pattern pattern = Pattern.compile("^.+@.+\\..+$");


    private RegisterController() {
        instance = this;
        WriteLog.addHandler(LOGGER);
        LOGGER.log(Level.INFO, "Creating RegisterController instance from constructor at: {0}\n", LocalTime.now());
    }

    public static RegisterController getInstance() {
        if (instance == null) {
            synchronized (RegisterController.class) {
                if (instance == null) {
                    return new RegisterController();
                }
            }
        }
        else LOGGER.log(Level.INFO, "Tried to get instance at: {0}\n", LocalTime.now());
        return instance;
    }


    private void setLayout (byte position, String scene, String labelText) {
        this.position = position;

        if (scene != null) {
            setVisibility(scene);

            Circles.draw(position);
            registerText.setText(labelText);
        }
    }


    private void setVisibility (String scene) {
        this.scenes.forEach((k, v) -> {
            if (scene.equals(k)) v.setVisible(true);
            else v.setVisible(false);
        });
    }


    private void setUserInfoVisibility () {
        if (accType == AccountTypes.ORGANIZATION) {
            firstName.setVisible(false);
            lastName.setVisible(false);
            organisationName.setVisible(true);
            webAddressField.setVisible(true);
        }
        else {
            firstName.setVisible(true);
            lastName.setVisible(true);
            organisationName.setVisible(false);
            webAddressField.setVisible(false);

        }
    }


    private void controlLayout () {
        String layout;

        switch (position) {
            case 0:
                layout = "accTypeLayout";
                break;
            case 1:
                layout = "userNameLayout";
                break;
            case 2:
                layout = "optionalLayout";
                break;
            default: layout = null;
        }
        setLayout(position, layout, this.labelText[position]);
    }

    private void btnBack () {
        register.setVisible(false);
        if (this.position > 0) {
            this.position--;
            controlLayout();

            if (this.position == 0) btnBack.setVisible(false);
            else {
                btnBack.setVisible(true);
                btnNext.setVisible(true);
            }
        }
    }

    private void btnNext () {
        if (this.position < 2 && accType != AccountTypes.NONE) {
            if (this.position == 1) {
                if (userName.getText().contains(" ") || passwordField.getText().contains(" ") || emailField.getText().contains(" ")) {
                    NotificationPane.show("Space is not allowed");
                    return;
                }

                if (!Validators.validate(userName, passwordField, verifyPasswordField, emailField)) {
                    return;
                }
                else if (passwordField.getText().compareTo(verifyPasswordField.getText()) != 0) {
                    NotificationPane.show("Password does not match");
                    return;
                }

                Matcher matcher = pattern.matcher(emailField.getText());
                if (!matcher.matches()) {
                    NotificationPane.show("Invalid email address");
                    return;
                }

                String query = String.format("SELECT * FROM USERS WHERE User_Name = '%s'", userName.getText());
                if (JDBC.get(query, User.class.getName()) != null) {
                   NotificationPane.show("Username already exist");
                    return;
                }
            }

            this.position++;
            controlLayout();
            btnBack.setVisible(true);

            if (this.position == 2) {
                btnNext.setVisible(false);
                register.setVisible(true);
            }
        }
    }

    private void register () {
        User user = new User.Builder(accType.toString(), userName.getText(), passwordField.getText())
                .email(emailField.getText())
                .phoneNumber(phoneNumberField.getText())
                .postCode(postCodeField.getText())
                .town(townField.getText())
                .address1(address1Field.getText())
                .address2(address2Field.getText())
                .email(emailField.getText())
                .firstName(firstName.getText())
                .lastName(lastName.getText())
                .organisationName(organisationName.getText())
                .webAddress(webAddressField.getText()).build();

        if (!JDBC.insert(user.getInsertQuery())) {
            NotificationPane.show("Something went wrong");
        }
        else {
            Loader.getInstance().loadMain(root, user, RootController.getInstance());
        }
    }


    @Override
    public void initializeNotificationPane() {
        NotificationPane.createInstance(errorPane, errorMessage);
    }

    @Override
    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
        accType = getType((toggleGroup.getSelectedToggle() != null ?
                toggleGroup.getSelectedToggle().getUserData().toString() : AccountTypes.NONE.toString()));
        System.out.println("account type: " + accType.toString());
        //accType = getType()
        System.out.println("toggle: " + toggleGroup.getSelectedToggle().getUserData().toString());
        setUserInfoVisibility();
    }

    private void setActionListeners () {
        toggleGroup.selectedToggleProperty().addListener(this::changed);

        userName.focusedProperty().addListener(((observableValue, aBoolean, newValue) -> {
            if (!newValue) userName.validate();
        }));

        passwordField.focusedProperty().addListener(((observableValue, aBoolean, newValue) -> {
            if (!newValue) passwordField.validate();
        }));

        btnNext.setOnMousePressed(e -> btnNext());
        btnBack.setOnMousePressed(e -> btnBack());
        btnLogin.setOnAction(e -> {
            Loader.getInstance().loadLogin(root, LoginController.getInstance());
            Destroy();
        });
        register.setOnAction(e -> register());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNotificationPane();
        Circles.createInstance(progressLayout);

        RequiredFieldValidator validator = new RequiredFieldValidator();
        userName.getValidators().add(validator);
        passwordField.getValidators().add(validator);

        setActionListeners();

        if (!JDBC.isConnected()) {
            NotificationPane.show("No network connection");
            JDBC.createConnection();
        }

        this.scenes.put("accTypeLayout", this.accTypeLayout);
        this.scenes.put("userNameLayout", this.userNameLayout);
        this.scenes.put("optionalLayout", this.optionalLayout);

        this.labelText[0] = "Some fancy text about account type";
        this.labelText[1] = "Some fancy text about user names and bla bla bla";
        this.labelText[2] = "Some fancy text about our lovely customer";

        setLayout((byte) 0, "accTypeLayout", this.labelText[0]);

        btnBack.setVisible(false);
        register.setVisible(false);

        first.setSelected(true);

        try {
            Image errorIcon = new Image(new FileInputStream("src/main/Resources/Drawable/error.png"));
            validator.setIcon(new ImageView(errorIcon));
        }
        catch (FileNotFoundException e) {
           LOGGER.log(Level.WARNING, "FileNotFoundException at: {0}; message: {1}\n", new Object[]{LocalTime.now(), e.getMessage()});
        }
    }

    private void Destroy () {
        if (instance != null) {
            LOGGER.log(Level.INFO, "Destroying RegisterController instance at: {0}\n", LocalTime.now());
            instance = null;
        }
    }
}
