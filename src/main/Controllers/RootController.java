package main.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.Entities.*;
import main.Interfaces.Notifications;
import main.Networking.JDBC;
import main.Utils.Test.Test;
import main.Utils.Loader;
import main.Utils.WriteLog;
import main.View.NotificationPane;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RootController extends Controller implements Initializable, Notifications {

    private static final Logger LOGGER = Logger.getLogger(RootController.class.getName());
    private User user;
    private AccountTypes type;
    private static RootController instance = null;

    @FXML private BorderPane content;
    @FXML private Text userNameField;
    @FXML private Label notificationCount;
    @FXML private HBox accountBox, topPane;
    @FXML private TextField searchField;
    @FXML private ImageView btnSearch, btnNotification;
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
        type = getType(this.user.getAccountType());

        userNameField.setText(user.getUserName());
        MenuController.getInstance().setAccountType(user.getAccountType());

        AccountTypes type = getType(user.getAccountType());

        if (type == AccountTypes.ADMIN || type == AccountTypes.ROOT) {
            String notifQuery = "SELECT count(*) FROM BOOKING WHERE Notify = '0'";
            updateNotificationCount(JDBC.getCount(notifQuery));
        }
        else {
            String notifQuery = String.format("SELECT count(*) FROM BOOKING WHERE Notify = '1' AND User_ID = '%s'", user.getUserID());

            updateNotificationCount(JDBC.getCount(notifQuery));
        }
    }


    public User getUser() {
        return this.user;
    }

    private void searchEvent () {
        String activeController = MenuController.getActiveController();
        System.out.println(activeController);
        if (activeController != null) {
            switch (activeController) {
                case "adminAcc":
                    System.out.println("adminAcc controller");
                    AdminAccountController.search(searchField.getText());
                    break;
                case "dashboard":

                    break;
                case "bookings":

                    break;
                case "festivals":
                    FestivalController.search(searchField.getText());
                    break;
            }
        }
    }

    protected static void updateNotificationCount (int number) {
         if (number > 0) {
             instance.notificationCount.setVisible(true);
             if (number > 9) getInstance().notificationCount.setText("9+");
             else getInstance().notificationCount.setText(String.valueOf(number));
         }
         else {
             instance.notificationCount.setVisible(false);
         }
    }

    protected static void refreshNotificationCount () {
         String notifQuery;
         if (instance.type == AccountTypes.ROOT || instance.type == AccountTypes.ADMIN) {
             notifQuery = "SELECT count(*) FROM BOOKING WHERE Notify = '0'";
         }
         else {
             notifQuery = String.format("SELECT count(*) FROM BOOKING WHERE Notify = '1' AND User_ID = '%s'", getInstance().getUser().getUserID());
         }

        updateNotificationCount(JDBC.getCount(notifQuery));
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

        btnNotification.setOnMousePressed(e -> {
            Loader.getInstance().loadPage("../UI/notifications.fxml", NotificationController.getInstance());
            MenuController.pushController("notifications");
        });
    }

    @Override
    public void initializeNotificationPane() {
        NotificationPane.createInstance(notificationPane, notificationMessage);
    }

    public void initialize (URL url, ResourceBundle bundle) {

        setActionListeners();
        Loader.createInstanceWithPane(content);
        initializeNotificationPane();

        Test.printTable("BOOKING", Booking.class.getName());
        Test.printTable("EVENTS", Event.class.getName());
        Test.printTable("USERS", User.class.getName());
        Test.printTable("BANDS", Bands.class.getName());
        Test.printTable("TICKET_TYPES", TicketType.class.getName());

        Loader.getInstance().loadPage("../UI/dashboard.fxml", DashboardController.getInstance());
    }

    public static void Destroy () {
        if (instance != null) {
            LOGGER.log(Level.INFO, "Destroying RootController instance at: {0}\n", LocalTime.now());
            instance = null;
        }
    }
}