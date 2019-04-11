package main.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import main.Main;
import main.Utils.Loader;

import java.net.URL;
import java.util.*;

public class MenuController implements Initializable{

    private String accountType = null;
    private Stack<String> activeController = new Stack<>();

    @FXML
    private AnchorPane root;

    @FXML
    private HBox adminAccPane;

    private static MenuController instance;
    private HashMap <String, String> fxml = new HashMap<>();
    private ArrayList<JFXButton> buttons = new ArrayList<>();
    private Loader loader;


    public MenuController () {
        instance = this;
        loader = Main.getPageLoader();
        Loader.setContent(RootController.getInstance().getContent());
    }


    public static MenuController getInstance() {
        return instance;
    }

    public static String getActiveController () {
        if (!getInstance().activeController.empty())
            return getInstance().activeController.peek();
        else return null;
    }

    @FXML
    private void dashboardEvent (MouseEvent event) {
        loader.loadPage(fxml.get("dashboard"));
        getInstance().activeController.push("dashboard");
    }

    @FXML
    private void bookingEvent (MouseEvent event) {
        loader.loadPage(fxml.get("bookings"));
        getInstance().activeController.push("bookings");
    }

    @FXML
    private void festivalEvent (MouseEvent event) {
        loader.loadPage(fxml.get("festivals"));
        getInstance().activeController.push("festivals");
    }

    @FXML
    private void adminAccEvent (MouseEvent e) {
        System.out.println(accountType);
        loader.loadPage(fxml.get("adminAcc"));
        getInstance().activeController.push("adminAcc");
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

        dashboardEvent(null);
    }
}
