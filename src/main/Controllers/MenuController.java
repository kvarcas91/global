package main.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuController {


    public void bookingEvent (ActionEvent event) {
        System.out.println("Bookings");
    }

    public void accountEvent (ActionEvent event) {
        System.out.println("Account");
    }

    public void festivalEvent (ActionEvent event) {
        System.out.println("Festivals");
    }
}
