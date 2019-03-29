package main.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuController extends Loader{

    @FXML
    AnchorPane root;

    @FXML
    JFXButton bookingButton, accountButton, festivalsButton, logOutButton;


    private ArrayList<JFXButton> buttons = new ArrayList<>();

    public void bookingEvent (ActionEvent event) {
        System.out.println("Bookings");
        setButtonColors(bookingButton);
    }

    public void accountEvent (ActionEvent event) {
        System.out.println("Account");
        setButtonColors(accountButton);
    }

    public void festivalEvent (ActionEvent event) {
        System.out.println("Festivals");
        setButtonColors(festivalsButton);
    }

    public void logOut (ActionEvent event) {
        super.loadLogin(root);
    }

    private void setButtonColors (JFXButton button) {
        buttons.addAll(Arrays.asList(bookingButton, accountButton, festivalsButton));
        for (JFXButton btn : buttons) {
            if (btn.equals(button)) btn.setStyle("-fx-background-color: grey;");
            else btn.setStyle("-fx-background-color: #2A2E37;");
        }
    }
}
