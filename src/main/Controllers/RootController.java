package main.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.Entities.*;
import main.Interfaces.Notifications;
import main.Networking.JDBC;
import main.Utils.Loader;
import main.Utils.WriteLog;
import main.View.NotificationPane;

import java.net.URL;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RootController extends Controller implements Initializable, Notifications {

    private static final Logger LOGGER = Logger.getLogger(RootController.class.getName());
    private User user = null;
    private static RootController instance = null;

    @FXML private BorderPane content;
    @FXML private Text userNameField;
    @FXML private HBox accountBox, topPane;
    @FXML private TextField searchField;
    @FXML private ImageView btnSearch;
    @FXML private HBox notificationPane;
    @FXML private Text notificationMessage;


     private RootController () {
         instance = this;
         WriteLog.addHandler(LOGGER);
         LOGGER.log(Level.INFO, "Creating RootController instance from constructor at: {0}\n", LocalTime.now());
    }

    public static RootController getInstance() {
        if (instance == null) {
            synchronized (RootController.class) {
                if (instance == null) {
                    return new RootController();
                }
            }
        }
        else LOGGER.log(Level.INFO, "Tried to create RootController instance at {0}\n", LocalTime.now());
        return instance;
    }

    // User cannot be null
    public void setUser(User user) {
        Objects.requireNonNull(user, "User cannot be null");
        String query = String.format("SELECT * FROM USERS WHERE User_Name = '%s' AND User_Password = '%s'",
                user.getUserName(), user.getUserPassword());
        this.user = (User) JDBC.get(query, User.class.getName());
        userNameField.setText(user.getUserName());
        System.out.println("MenuController instance is null: " + (MenuController.getInstance() == null));
       MenuController.getInstance().setAccountType(user.getAccountType());
    }


    public User getUser() {
        return this.user;
    }

    private void searchEvent () {
        String activeController = MenuController.getActiveController();
        if (activeController != null) {
            switch (activeController) {
                case "adminAcc":
                    System.out.println("adminAcc controller");
                    AdminAccountController.search(searchField.getText());
                    break;
                case "dashboard":

                    //break;
                case "bookings":

                    //break;
                case "festivals":

                    break;
            }
        }
    }

    private void test (String table, String className) {
        System.out.println("-----------------------------------");
        System.out.println(String.format("ALL %s", table));
        //JDBC database = Main.getDatabase();
        String query = String.format("SELECT * FROM %s", table);
        //ArrayList<Object> obj = database.getAll(query, className);
        //for (Object object : obj) {
          //  System.out.println(object.toString());
            //System.out.println("***");
        //}
        System.out.println("------------------------------------");

    }

    private void setActionListeners () {
        ContextMenu menu = new ContextMenu();
        MenuItem accountItem = new MenuItem("My Account");
        menu.getItems().add(accountItem);

        accountItem.setOnAction(e -> {
            Loader.getInstance().loadPage("../UI/account.fxml", AccountController.getInstance());
            MenuController.pushController("account");
        });

        accountBox.setOnMousePressed(e -> {
            if (e.isPrimaryButtonDown()) menu.show(topPane, e.getScreenX()-65, e.getSceneY()+100);
        });

        btnSearch.setOnMousePressed(e -> searchEvent());
        searchField.textProperty().addListener((observable, oldVal, newVal) -> searchEvent());
    }

    @Override
    public void initializeNotificationPane() {
        NotificationPane.createInstance(notificationPane, notificationMessage);
    }

    public void initialize (URL url, ResourceBundle bundle) {


        setActionListeners();
        Loader.createInstanceWithPane(content);
        initializeNotificationPane();
        Loader.getInstance().loadPage("../UI/dashboard.fxml", DashboardController.getInstance());
    }

    public static void Destroy () {
        if (instance != null) {
            LOGGER.log(Level.INFO, "Destroying RootController instance at: {0}\n", LocalTime.now());
            instance = null;
        }
    }
}