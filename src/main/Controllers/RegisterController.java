package main.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import main.Entities.Customer;
import main.Entities.Organization;
import main.Entities.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RegisterController extends Loader implements Initializable, ChangeListener<Toggle> {

    @FXML
    AnchorPane root;

    @FXML
    HBox progressLayout, accTypeLayout;

    @FXML
    GridPane userNameLayout, optionalLayout;

    @FXML
    JFXRadioButton first, second;

    @FXML
    JFXTextField firstName, lastName, organisationName, userName, address1Field, address2Field, townField, postCodeField,
                  emailField, webAddressField, phoneNumberField;

    @FXML
    JFXPasswordField passwordField, verifyPasswordField;

    @FXML
    JFXButton btnBack, btnNext;

    @FXML
    ToggleGroup toggleGroup;

    @FXML
    Label registerLabel;

    enum accountTypes {PUBLIC, ORGANISATION}
    private User user = null;

    private byte position = 0;
    private HashMap<String, Pane> scenes = new HashMap<>();
    private String[] labelText = new String[3];
    private String accType = null;

    private void drawCircle () {
        int size;
        Color color;

        progressLayout.getChildren().clear();

        // draw Progress
        for (int i = 0; i < 3; i++) {
            Circle progress;
            if (i <= position) {
                color = Color.WHITE;
                size = 6;
            }
            else {
                color = Color.TRANSPARENT;
                size = 5;
            }
            progress = new Circle(0, 0, size);
            progress.setFill(color);
            progress.setStroke(Color.WHITE);

            progressLayout.getChildren().add(progress);
            System.out.println("Adding circle");
        }
    }


    private void setLayout (byte position, String scene, String labelText) {
        this.position = position;

        if (scene != null) {
            setVisibility(scene);
            drawCircle();
            registerLabel.setText(labelText);
        }
    }


    private void setVisibility (String scene) {
        this.scenes.forEach((k, v) -> {
            if (scene.equals(k)) v.setVisible(true);
            else v.setVisible(false);
        });
    }


    private void setUserInfoVisibility () {
        if (accType.equals(accountTypes.PUBLIC.toString())) {
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


    public void btnBack (ActionEvent event) {
        if (this.position > 0) {
            this.position--;
            controlLayout();
            btnNext.setText("Next");

            if (this.position == 0) btnBack.setVisible(false);
            else btnBack.setVisible(true);
        }
    }


    public void btnNext (ActionEvent event) {
        if (this.position < 2 && accType != null) {
            if (this.position == 1) {
                if (!validate(userName, passwordField, verifyPasswordField)) {
                    System.out.println("Empty");
                    return;
                }
                else if (passwordField.getText().compareTo(verifyPasswordField.getText()) != 0) {
                    System.out.println("nope");
                    return;
                }
            }

            System.out.println("position: " + position);
            this.position++;
            controlLayout();
            btnBack.setVisible(true);

            if (this.position == 2) {
                btnNext.setText("Register");
            }
        }
        else {
            createUserObj();
            loadMain(root, user);
        }
    }

    private void createUserObj() {
        User.Builder builder = new User.Builder(0, accType, userName.getText(), passwordField.getText())
                .email(emailField.getText())
                .phoneNumber(phoneNumberField.getText())
                .postCode(postCodeField.getText())
                .town(townField.getText())
                .address1(address1Field.getText())
                .address2(address2Field.getText())
                .email(emailField.getText());

        if (accType.equals(accountTypes.PUBLIC.toString())) user = new Customer(builder, firstName.getText(), lastName.getText());
        else user = new Organization(builder, organisationName.getText(), webAddressField.getText());
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


    @Override
    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
        accType = (toggleGroup.getSelectedToggle() != null ? toggleGroup.getSelectedToggle().getUserData().toString() : null);
        setUserInfoVisibility();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        drawCircle();



        this.scenes.put("accTypeLayout", this.accTypeLayout);
        this.scenes.put("userNameLayout", this.userNameLayout);
        this.scenes.put("optionalLayout", this.optionalLayout);

        this.labelText[0] = "Some fancy text about account type";
        this.labelText[1] = "Some fancy text about user names and bla bla bla";
        this.labelText[2] = "Some fancy text about our lovely customer";
        setLayout((byte) 0, "accTypeLayout", this.labelText[0]);
        btnBack.setVisible(false);

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
