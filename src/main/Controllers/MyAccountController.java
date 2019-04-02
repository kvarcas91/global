package main.Controllers;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;


public class MyAccountController implements Initializable {


    @FXML
    private Label userType;

    @FXML
    private JFXTextField userName;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXTextField firstName;

    @FXML
    private JFXTextField lastName;

    @FXML
    private JFXTextField address1;

    @FXML
    private JFXTextField address2;

    @FXML
    private JFXTextField postCode;

    @FXML
    private JFXTextField town;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField phoneNumber;

    @FXML
    private JFXButton applyButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        RequiredFieldValidator validator = new RequiredFieldValidator();
        userName.getValidators().add(validator);
        password.getValidators().add(validator);
        lastName.getValidators().add(validator);
        address1.getValidators().add(validator);
        address2.getValidators().add(validator);
        town.getValidators().add(validator);
        postCode.getValidators().add(validator);
        email.getValidators().add(validator);
        phoneNumber.getValidators().add(validator);

        userName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    userName.validate();
                }
            }
        });

        password.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!newValue) password.validate();
            }
        });

        firstName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!newValue) firstName.validate();
            }
        });

        lastName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!newValue) lastName.validate();
            }
        });

        address1.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!newValue) address1.validate();
            }
        });

        address2.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!newValue) address2.validate();
            }
        });

        town.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!newValue) town.validate();
            }
        });


        postCode.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!newValue) postCode.validate();
            }
        });

        email.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!newValue) email.validate();
            }
        });

        phoneNumber.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!newValue) phoneNumber.validate();
            }
        });
        try {
            Image errorIcon = new Image(new FileInputStream("src/main/Resources/Drawable/error.png"));
            validator.setIcon(new ImageView(errorIcon));
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        setUserType(LoginController.getInstance().userName());
    }

    public void setUserType(String user){
        this.userName.setText(user);
    }


    @FXML
    public void setApplyButton (ActionEvent event){

            System.out.println("User Name is: " + userName.getText());
            System.out.println("User Password is: " + password.getText());
            System.out.println("User First Name is: " + firstName.getText());
            System.out.println("User Last Name is: " + lastName.getText());
            System.out.println("User Address 1 is: " + address1.getText());
            System.out.println("User Address 2 is: " + address2.getText());
            System.out.println("User Town is: " + town.getText());
            System.out.println("User Post Code is: " + postCode.getText());
            System.out.println("User Email Address is: " + email.getText());
            System.out.println("User Phone Number is: " + phoneNumber.getText());
            System.out.println("INFORMATION BEEN UPDATED");
    }
}



