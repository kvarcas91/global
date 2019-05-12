package main.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.Entities.Booking;
import main.Entities.Entity;
import main.Networking.JDBC;

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificationController extends Controller implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(NotificationController.class.getName());
    private static NotificationController instance;
    private AccountTypes type;
    private ArrayList<Booking> bookings = new ArrayList<>();
    private ArrayList<Booking> clonedBookings = new ArrayList<>();

    @FXML private VBox container;

    private NotificationController () {
        instance = this;
    }

    public static NotificationController getInstance() {
        if (instance == null) {
            synchronized (NotificationController.class) {
                if (instance == null) {
                    return new NotificationController();
                }
            }
        }
        else {
            LOGGER.log(Level.INFO, "Tried to create NotificationController instance at {0}\n", LocalTime.now());
        }
        return instance;
    }

    private void getBookings () {
        String query;

        if (type == AccountTypes.ROOT || type == AccountTypes.ADMIN) {
            query = "SELECT * FROM BOOKING WHERE Notify = '0'";
        }
        else {
            query = String.format("SELECT * FROM BOOKING WHERE (Notify = '1' OR Notify = '2') AND User_ID = '%s'",
                    RootController.getInstance().getUser().getUserID());
        }

        ArrayList<Entity> entities = JDBC.getAll(query, Booking.class.getName());
        Booking temp;
        if (!bookings.isEmpty()) {
            bookings.clear();
            clonedBookings.clear();
        }

        for (Entity entity : entities) {
            temp = (Booking) entity;
            bookings.add(temp);
        }
    }

    private void confirm (Booking booking) {
        booking.setIsConfirmed(1);
        JDBC.update(booking.getUpdateQuery());
        RootController.refreshNotificationCount();

        bookings.remove(booking);
        clonedBookings.remove(booking);

        setView();
    }

    private void setView () {

        String message;
        container.getChildren().clear();

        if (!clonedBookings.isEmpty()) {
            for (Booking booking : clonedBookings) {

                System.out.println(booking);

                if (type == AccountTypes.ADMIN || type == AccountTypes.ROOT) {
                    message = String.format("booking iD: %s", booking.getBookingID());
                }
                else {
                    message = String.format("Your booking to '%s' event has been confirmed", booking.getEventID());
                }
                System.out.println(message);
                System.out.println();

                HBox box = new HBox();
                box.setSpacing(10);

                Text textContainer = new Text(message);

                box.getChildren().add(textContainer);
                if (type == AccountTypes.ADMIN || type == AccountTypes.ROOT) {
                    setAdminControl(box, booking);
                }
                else  {
                    setUserControl(box, booking);
                }

                container.getChildren().add(box);
            }
        }
    }

    private void setAdminControl (HBox box, Booking booking) {
        box.setStyle("-fx-background-color: white;");

        JFXButton confirm = new JFXButton("Confirm");
        JFXButton reject = new JFXButton("Reject");

        confirm.setOnAction(e -> confirm(booking));

        box.getChildren().addAll(reject, confirm);
    }

    private void setUserControl (HBox box, Booking booking) {
        System.out.println("setting user control with notify value: " + booking.getNotify());
        if (booking.getNotify() == 1) {
            box.setStyle("-fx-background-color: white");
            booking.setIsConfirmed(2);
            JDBC.update(booking.getUpdateQuery());
            RootController.refreshNotificationCount();
        }

        JFXButton remove = new JFXButton();
        // TODO onclick event

        box.getChildren().add(remove);
    }

    private void sort () {
        // TODO sorting stuff based on isConfirmed
    }

    /*
    private void setView () {
        String query;

        container.getChildren().clear();

        if (type == AccountTypes.ROOT || type == AccountTypes.ADMIN)
            query = "SELECT * FROM BOOKING WHERE Notify = '0'";
        else query = String.format("SELECT * FROM BOOKING WHERE (Notify = '1' OR Notify = '2') AND User_ID = '%s'",
                RootController.getInstance().getUser().getUserID());

        String message;

        ArrayList<Entity> entities = JDBC.getAll(query, Booking.class.getName());
        for (Entity entity : entities) {
            booking = (Booking) entity;

            ArrayList<String> eventName = JDBC.getValues(String.format(
                    "SELECT Event_Name FROM EVENTS WHERE Event_ID = '%s'", booking.getEventID()), 1);
            JFXButton confirm = null, reject = null;

            if (!(type == AccountTypes.ROOT || type == AccountTypes.ADMIN)) {

                if (booking.getNotify() != 2) {
                    booking.setIsConfirmed(2);
                    JDBC.update(booking.getUpdateQuery());
                }

                message = String.format("Your booking to %s event has been confirmed", eventName.get(0));
            }
            else {
                ArrayList<String> userName = JDBC.getValues(String.format(
                        "SELECT User_Name FROM USERS WHERE User_ID = '%s'", booking.getUserID()), 1);

                message = String.format("%s wants to book %s ticket to %s event", userName.get(0), booking.getQuantity(), eventName.get(0));
                reject = new JFXButton("Reject");
                confirm = new JFXButton("Confirm");
            }

            HBox box = new HBox();
            //HBox text = new HBox();

            //HBox controls = new HBox();

            box.setSpacing(10);
            Text messageContainer = new Text(message);
            box.getChildren().add(messageContainer);

            if (type == AccountTypes.ROOT || type == AccountTypes.ADMIN) {
                confirm.setOnAction(e -> confirm(booking));

                box.getChildren().addAll(reject, confirm);

            }

            container.getChildren().add(box);

        }

        if (!entities.isEmpty()) RootController.refreshNotificationCount();
    }



     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        type = getType(RootController.getInstance().getUser().getAccountType());
        getBookings();
        clonedBookings.addAll(bookings);

        setView();
    }
}
