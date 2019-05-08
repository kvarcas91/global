package main.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import main.Utils.Loader;
import main.Utils.WriteLog;
import main.View.NotificationPane;

import java.net.URL;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuController implements Initializable{

    private static final Logger LOGGER = Logger.getLogger(MenuController.class.getName());
    private Stack<String> activeController = new Stack<>();

    @FXML
    private AnchorPane root;

    @FXML
    private HBox adminAccPane;

    private static MenuController instance = null;
    private HashMap <String, String> fxml = new HashMap<>();
    private Loader loader;


    public MenuController () {
        instance = this;
        loader = Loader.getInstance();
        WriteLog.addHandler(LOGGER);
        LOGGER.log(Level.INFO, "Creating MenuController instance from constructor at: {0}\n", LocalTime.now());
    }


    public static MenuController getInstance() {
        if (instance == null) {
            synchronized (MenuController.class) {
                if (instance == null) {
                    return new MenuController();
                }
            }
        }
        else LOGGER.log(Level.INFO, "Tried to create MenuController instance at {0}\n", LocalTime.now());
        return instance;
    }

    protected static void pushController (String s) {
        Objects.requireNonNull(s);
        getInstance().activeController.push(s);
    }

    protected static String getActiveController () {
        if (!getInstance().activeController.empty())
            return getInstance().activeController.peek();
        else return null;
    }

    @FXML
    private void dashboardEvent (MouseEvent event){
        if (!activeController.peek().equals("dashboard")) {
            loader.loadPage(fxml.get("dashboard"), DashboardController.getInstance());
            activeController.push("dashboard");
        }
    }

    @FXML
    private void bookingEvent (MouseEvent event) {
        loader.loadPage(fxml.get("bookings"), BookingController.getInstance());
        activeController.push("bookings");
    }

    @FXML
    private void festivalEvent (MouseEvent event) {
        loader.loadPage(fxml.get("festivals"), FestivalController.getInstance());
        activeController.push("festivals");
    }

    @FXML
    private void adminAccEvent (MouseEvent e) {
        loader.loadPage(fxml.get("adminAcc"), AdminAccountController.getInstance());
        activeController.push("adminAcc");
    }

    @FXML
    private void logOut (MouseEvent event) {
        RootController.Destroy();
        Destroy();
        loader.loadLogin(root, LoginController.getInstance());
    }

    @FXML
    private void invoiceEvent (MouseEvent event) {
        loader.loadPage(fxml.get("invoice"), InvoiceController.getInstance());
        activeController.push("invoice");
    }


    public void setAccountType (String accountType) {
        if (accountType.equals("ADMIN")) adminAccPane.setVisible(true);
        else adminAccPane.setVisible(false);
    }

    public void initialize (URL url, ResourceBundle bundle) {
        fxml.put("festivals", "../UI/festivals.fxml");
        fxml.put("account", "../UI/account.fxml");
        fxml.put("bookings", "../UI/bookings.fxml");
        fxml.put("adminAcc", "../UI/adminAccount.fxml");
        fxml.put("dashboard", "../UI/dashboard.fxml");
        fxml.put("addFestival", "../UI/addFestivals.fxml");
        fxml.put("invoice", "../UI/invoice.fxml");
        activeController.push("dashboard");
    }

    private void Destroy () {
        if (instance != null) {
            LOGGER.log(Level.INFO, "Destroying MenuController instance at: {0}\n", LocalTime.now());
            instance = null;
        }
    }
}
