package main.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MenuController implements Initializable{

    private String accountType = null;

    @FXML
    private AnchorPane root;

    @FXML
    private HBox adminAccPane;

    private static MenuController instance;
    private HashMap <String, String> fxml = new HashMap<>();
    private ArrayList<JFXButton> buttons = new ArrayList<>();
    private Loader loader;


    public MenuController () {
        loader = new Loader(RootController.getInstance().getContent());
        instance = this;
    }

    public static MenuController getInstance() {
        return instance;
    }

    @FXML
    private void dashboardEvent (MouseEvent event) {
        loader.loadPage(fxml.get("dashboard"));
    }

    @FXML
    private void bookingEvent (MouseEvent event) {
        loader.loadPage(fxml.get("bookings"));
    }

    @FXML
    private void festivalEvent (MouseEvent event) {
        loader.loadPage(fxml.get("festivals"));
    }

    @FXML
    private void adminAccEvent (MouseEvent e) {
        System.out.println(accountType);
        loader.loadPage("../UI/adminAccount.fxml");
    }

    @FXML
    private void logOut (MouseEvent event) {
        loader.loadLogin(root);
    }


    public void setAccountType (String accountType) {
        this.accountType = accountType;
        if (accountType.equals("ADMIN")) adminAccPane.setVisible(true);
        else adminAccPane.setVisible(false);
    }

    public void initialize (URL url, ResourceBundle bundle) {
        fxml.put("festivals", "../UI/festivals.fxml");
        fxml.put("account", "../UI/account.fxml");
        fxml.put("bookings", "../UI/bookings.fxml");
        fxml.put("adminAcc", "../UI/adminAccount.fxml");
        fxml.put("dashboard", "../UI/dashboard.fxml");
    }
}
