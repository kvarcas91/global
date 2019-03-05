package main.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Controller {

    @FXML

     JFXButton button;

    @FXML
    void pressMe (ActionEvent event) {
        System.out.println("done");
    }

}
