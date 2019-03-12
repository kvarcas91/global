package main.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class LoginController {

    @FXML
    JFXTextField userTextField;

    @FXML
    void pressMe (ActionEvent event) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Input required");
        userTextField.getValidators().add(validator);



    }

}
