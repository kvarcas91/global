package main.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.layout.AnchorPane;

public class MenuController {

    @FXML
    JFXButton bookingButton, accountButton, festivalsButton;

    private ArrayList<JFXButton> buttons = new ArrayList<>();

    public void bookingEvent (ActionEvent event) {
        System.out.println("Bookings");
        setButtonColors(bookingButton);
    }

    public void accountEvent (ActionEvent event) throws IOException {
       Stage home = new Stage();
       Parent root = FXMLLoader.load(getClass().getResource("../UI/AccountProfile.fxml"));
       Scene scene = new Scene(root);
       home.setScene(scene);
       home.show();


        System.out.println("Welcome");

        setButtonColors(accountButton);

        //AnchorPane pane = FXMLLoader.load(getClass().getResource("../UI/AccountProfile.fxml"));
        //pane.getChildren().setAll(pane);
    }

    public void festivalEvent (ActionEvent event) {
        System.out.println("Festivals");
        setButtonColors(festivalsButton);
    }

    private void setButtonColors (JFXButton button) {
        buttons.addAll(Arrays.asList(bookingButton, accountButton, festivalsButton));
        for (JFXButton btn : buttons) {
            if (btn.equals(button)) btn.setStyle("-fx-background-color: grey;");
            else btn.setStyle("-fx-background-color: #2A2E37;");
        }
    }
}
