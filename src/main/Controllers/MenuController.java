package main.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MenuController implements Initializable{

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton bookingButton, accountButton, festivalsButton, logOutButton;


    private HashMap <String, String> fxml = new HashMap<>();
    private ArrayList<JFXButton> buttons = new ArrayList<>();
    private Loader loader;


    public MenuController () {
        loader = new Loader(RootController.getInstance().getContent());
    }

    @FXML
    private void bookingEvent (ActionEvent event) {
        loader.loadPage(fxml.get("bookings"));
        setButtonColors(bookingButton);
    }

    @FXML
    private void accountEvent (ActionEvent event) {
        loader.loadPage(fxml.get("account"));
        setButtonColors(accountButton);
    }

    @FXML
    private void festivalEvent (ActionEvent event) {
        loader.loadPage(fxml.get("festivals"));
        setButtonColors(festivalsButton);
    }

    @FXML
    private void logOut (ActionEvent event) {
        loader.loadLogin(root);
    }

    private void setButtonColors (JFXButton button) {
        buttons.addAll(Arrays.asList(bookingButton, accountButton, festivalsButton));
        for (JFXButton btn : buttons) {
            if (btn.equals(button)) btn.setStyle("-fx-background-color: white; -fx-text-fill: #2A2E37; -fx-font-weight: 700;");
            else btn.setStyle("-fx-background-color: #2A2E37; -fx-font-weight: 600;");
        }
    }

    public void initialize (URL url, ResourceBundle bundle) {
        fxml.put("festivals", "../UI/festivals.fxml");
        fxml.put("account", "../UI/account.fxml");
        fxml.put("bookings", "../UI/bookings.fxml");
    }
}
