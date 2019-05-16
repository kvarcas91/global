package main.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.Entities.Booking;
import main.Entities.Entity;
import main.Networking.JDBC;
import main.View.NotificationPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        NotificationPane.show("Booking has been confirmed", "green");

        setView();
    }

    private void reject (Booking booking) {
        JDBC.delete("BOOKING", "Booking_ID", booking.getBookingID());
        RootController.refreshNotificationCount();
        bookings.remove(booking);
        clonedBookings.remove(booking);
        NotificationPane.show("Booking has been rejected", "green");

        setView();
    }

    private void setView () {

        String message;
        container.getChildren().clear();

        if (!clonedBookings.isEmpty()) {
            for (Booking booking : clonedBookings) {

                System.out.println(booking);
                ArrayList<String> eventName = JDBC.getValues(String.format(
                        "SELECT Event_Name FROM EVENTS WHERE Event_ID = '%s'", booking.getEventID()), 1);

                if (type == AccountTypes.ADMIN || type == AccountTypes.ROOT) {
                    message = String.format("User %s wants to book %s ticket to '%s' event",
                            booking.getUserID(), booking.getQuantity(), eventName.get(0));
                }
                else {
                    message = String.format("Your booking to '%s' event has been confirmed", eventName.get(0));
                }
                System.out.println(message);
                System.out.println();

                HBox row = new HBox();
                HBox textBox = new HBox();
                row.setSpacing(10);

                Text textContainer = new Text(message);

                textBox.getChildren().add(textContainer);

                row.getChildren().add(textBox);
                textBox.setAlignment(Pos.CENTER_LEFT);



                if (type == AccountTypes.ADMIN || type == AccountTypes.ROOT) {
                    setAdminControl(row, booking);
                }
                else  {
                    setUserControl(row, booking);
                }


                container.getChildren().add(row);
            }
        }
        else {
            Label label = new Label("Empty");
            label.setStyle("-fx-font-size: 22px; -fx-text-fill: grey; -fx-font-weight: 500;");
            container.getChildren().add(label);
            container.setAlignment(Pos.CENTER);
        }
    }

    private void setAdminControl (HBox box, Booking booking) {
        HBox controlBox = new HBox();
        controlBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(controlBox, Priority.ALWAYS);

        box.setStyle("-fx-background-color: white;");

        JFXButton confirm = new JFXButton("Confirm");

        Image rejectImg = null;
        try {
            rejectImg = new Image(new FileInputStream("src/main/Resources/Drawable/delete.png"));
        }
        catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "FileNotFoundException. Message: {0}; at: {1}\n", new Object[]{e.getMessage(), LocalTime.now()});
        }
        ImageView reject = new ImageView(rejectImg);
        reject.setFitWidth(24);
        reject.setFitHeight(24);

        reject.setOnMousePressed(e -> reject(booking));
        confirm.setOnAction(e -> confirm(booking));
        controlBox.getChildren().addAll(reject, confirm);

        box.getChildren().add(controlBox);
    }

    private void setUserControl (HBox box, Booking booking) {
        HBox controlBox = new HBox();
        controlBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(controlBox, Priority.ALWAYS);

        if (booking.getNotify() == 1) {
            box.setStyle("-fx-background-color: white");
            booking.setIsConfirmed(2);
            JDBC.update(booking.getUpdateQuery());
            RootController.refreshNotificationCount();
        }

        Image rejectImg = null;
        try {
            rejectImg = new Image(new FileInputStream("src/main/Resources/Drawable/delete.png"));
        }
        catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "FileNotFoundException. Message: {0}; at: {1}\n", new Object[]{e.getMessage(), LocalTime.now()});
        }
        ImageView reject = new ImageView(rejectImg);
        reject.setFitWidth(24);
        reject.setFitHeight(24);
        controlBox.getChildren().addAll(reject);

        reject.setOnMousePressed(e -> {
            booking.setIsConfirmed(3);
            JDBC.update(booking.getUpdateQuery());
            bookings.remove(booking);
            clonedBookings.remove(booking);

            setView();
        });

        box.getChildren().add(controlBox);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        type = getType(RootController.getInstance().getUser().getAccountType());
        getBookings();
        clonedBookings.addAll(bookings);

        setView();
    }
}
