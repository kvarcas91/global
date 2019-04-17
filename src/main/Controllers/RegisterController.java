package main.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import main.Entities.User;
import main.Interfaces.NotificationPane;
import main.Networking.JDBC;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RegisterController implements Initializable, NotificationPane, ChangeListener<Toggle> {


    @FXML
    private BorderPane root;

    @FXML
    private HBox progressLayout, accTypeLayout, errorPane;

    @FXML
    private GridPane userNameLayout, optionalLayout;

    @FXML
    private JFXRadioButton first, second;

    @FXML
    private JFXTextField firstName, lastName, organisationName, userName, address1Field, address2Field, townField, postCodeField,
            emailField, webAddressField, phoneNumberField;

    @FXML
    private JFXPasswordField passwordField, verifyPasswordField;

    @FXML
    private JFXButton register;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private Text registerText, errorMessage;

    @FXML
    private ImageView btnBack, btnNext;

    private User user = null;

    private byte position = 0;
    private HashMap<String, Pane> scenes = new HashMap<>();
    private String[] labelText = new String[3];
    private String accType = null;
    private JDBC database = null;
    private Connection connection = null;


    public RegisterController() {
        database = LoginController.getConnection();
        connection = database.getConnection();
    }

    private void drawCircle() {
        int size;
        progressLayout.getChildren().clear();

        // draw Progress
        for (int i = 0; i < 3; i++) {
            Circle progress;
            if (i <= position) {
                size = 7;
            }
            else {
                size = 3;
            }
            progress = new Circle(0, 0, size);
            progress.setFill(Color.web("#5375da"));
            progress.setStroke(Color.web("#5375da"));

            progressLayout.getChildren().add(progress);
        }
    }

    private void setLayout (byte position, String scene, String labelText) {
        this.position = position;

        if (scene != null) {
            setVisibility(scene);
            drawCircle();
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
        if (accType.equals("PUBLIC")) {
            firstName.setVisible(true);
            lastName.setVisible(true);
            organisationName.setVisible(false);
            webAddressField.setVisible(false);
        }
        else {
            firstName.setVisible(false);
            lastName.setVisible(false);
            organisationName.setVisible(true);
            webAddressField.setVisible(true);
        }
    }

    private void controlLayout () {
        System.out.println(position);
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

    private <T extends TextField> boolean validate (T... widget) {
        if (widget.length != 0) {
            for (T element : widget) {
                if (element.getText().isEmpty()) return false;
            }
            return true;
        }
        else return false;
    }

    @FXML
    private void btnBack (MouseEvent event) {
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

    @FXML
    private void btnNext (MouseEvent event) {
        if (this.position < 2 && accType != null) {
            if (this.position == 1) {
                if (!validate(userName, passwordField, verifyPasswordField)) {
                    setNotificationPane("Some fields are empty", null);
                    return;
                }
                else if (passwordField.getText().compareTo(verifyPasswordField.getText()) != 0) {
                    setNotificationPane("Password does not match", null);
                    return;
                }
                String query = String.format("SELECT * FROM USERS WHERE User_Name = '%s'", userName.getText());
                if (database.get(query, User.class.getName()) != null) {
                    setNotificationPane("username already exists", null);
                    return;
                }
            }

            System.out.println("position: " + position);
            this.position++;
            controlLayout();
            btnBack.setVisible(true);

            if (this.position == 2) {
                btnNext.setVisible(false);
                register.setVisible(true);
            }
        }
    }

    @FXML
    private void login (ActionEvent e) {
        Loader loader = new Loader();
        loader.loadLogin(root);
    }

    @FXML
    private void register (ActionEvent e) {
        user = new User.Builder(accType, userName.getText(), passwordField.getText())
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

        if (!database.insert(user.getQuery())) {
            setNotificationPane("Something went wrong", null);
        }
        else {
            Loader loader = new Loader();
            loader.loadMain(root, user);
        }
    }

    private void createUserObj() {

    }

    private void checkConnection () {
        connection = database.getConnection();
        if (connection == null) {
            setNotificationPane("No Network Connection", null);
        }
    }

    @Override
    public void setNotificationPane(String message, String color) {
        errorPane.setVisible(true);
        errorMessage.setText(message);
        Task task = hideNotificationPane();
        new Thread(task).start();
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
    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
        accType = (toggleGroup.getSelectedToggle() != null ? toggleGroup.getSelectedToggle().getUserData().toString() : null);
        setUserInfoVisibility();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkConnection();
        this.scenes.put("accTypeLayout", this.accTypeLayout);
        this.scenes.put("userNameLayout", this.userNameLayout);
        this.scenes.put("optionalLayout", this.optionalLayout);

        this.labelText[0] = "Some fancy text about account type";
        this.labelText[1] = "Some fancy text about user names and bla bla bla";
        this.labelText[2] = "Some fancy text about our lovely customer";

        setLayout((byte) 0, "accTypeLayout", this.labelText[0]);
        //setLayout((byte) 1, "userNameLayout", this.labelText[1]);

        btnBack.setVisible(false);
        register.setVisible(false);
        toggleGroup.selectedToggleProperty().addListener(this::changed);
        first.setSelected(true);

        RequiredFieldValidator validator = new RequiredFieldValidator();
        userName.getValidators().add(validator);
        passwordField.getValidators().add(validator);

        userName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    userName.validate();
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