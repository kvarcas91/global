package main.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.Entities.Booking;
import main.Entities.Entity;
import main.Entities.Event;
import main.Entities.TicketType;
import main.Networking.JDBC;
import main.View.NotificationPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateBookingController extends Controller implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(CreateBookingController.class.getName());
    private static CreateBookingController instance;
    private Event event = null;
    private HashMap<Integer, Integer> ticketQuantity = new HashMap<>();

    @FXML private Text eventNameText, eventLocationText, eventDateText;
    @FXML private JFXTextArea eventDescriptionText;
    @FXML private JFXButton makeBooking;
    @FXML private VBox bandLayout, ticketLayout;

    private CreateBookingController () {
        instance = this;
    }

    public static CreateBookingController getInstance() {
        if (instance == null) {
            synchronized (CreateBookingController.class) {
                if (instance == null) {
                    return new CreateBookingController();
                }
            }
        }
        else LOGGER.log(Level.WARNING, "Tried to create additional instance at: {0}. Skipping and adding just pane\n",
                LocalTime.now());
        return instance;
    }

    public void setEvent (Event mEvent) {
        this.event = mEvent;
    }

    private void bookEvent () {
        if (!ticketQuantity.isEmpty()) {
            ArrayList<Booking> bookings = new ArrayList<>();


            for (HashMap.Entry<Integer, Integer> map : ticketQuantity.entrySet()) {
               if (map.getValue() > 0) {
                   Booking booking = new Booking(RootController.getInstance().getUser().getUserID(), event.getEventID(), map.getKey(), map.getValue());
                   bookings.add(booking);
               }
            }

            if (!bookings.isEmpty()) {
                for (Booking booking : bookings) {
                    JDBC.insert(booking.getInsertQuery());
                }

                NotificationPane.show("Booking has been created", "green");
                RootController.refreshNotificationCount();
            }
            else NotificationPane.show("You must book at least one ticket");
        }
        else {
            NotificationPane.show("You must book at least one ticket");
        }
    }

    private void setBands () {
        String query = String.format("SELECT BANDS.Band_Name FROM BANDS, EVENT_BANDS WHERE EVENT_BANDS.Event_ID = '%s' AND EVENT_BANDS.Band_ID = BANDS.Band_ID",
                event.getEventID());

        bandLayout.getChildren().clear();
        Label BAND = new Label("Bands");
        BAND.setStyle("-fx-font-size: 14px; -fx-font-weight: 700;");

        bandLayout.getChildren().add(BAND);

        ArrayList<String> bands = JDBC.getValues(query, 1);
        for (String s : bands) {
            Label band = new Label(s);
            bandLayout.getChildren().add(band);
        }
    }

    private void setTickets () {
        String query = String.format("SELECT * FROM TICKET_TYPES WHERE Event_ID = '%s'", event.getEventID());

        ArrayList<Entity> entities = JDBC.getAll(query, TicketType.class.getName());
        ArrayList<String> slots = new ArrayList<>();
        TicketType temp;

        for (Entity entity : entities) {
            temp = (TicketType) entity;

            slots.clear();
            String bookedTicketQuery = String.format("SELECT SUM(Quantity) FROM BOOKING WHERE Event_ID = '%s' AND Ticket_Type = '%s'",
                    event.getEventID(), temp.getID());

            slots = JDBC.getValues(bookedTicketQuery, 1);

            int ID = temp.getID();
            int slot = temp.getSlot();
            int bookedSlots;

            if (!slots.isEmpty() && slots.get(0) != null) {
                bookedSlots = Integer.valueOf(slots.get(0));
            }
            else bookedSlots = 0;

            int availableTickets = slot - bookedSlots;

            HBox ticketBox = new HBox();

            ticketBox.setAlignment(Pos.CENTER_RIGHT);
            ticketBox.setSpacing(20);

            HBox textBox = new HBox();
            textBox.prefWidth(100);

            DecimalFormat df = new DecimalFormat("#.00");

            Text ticketText = new Text(String.format("%s (£ %s)", temp.getName(), df.format(temp.getPrice())));
            textBox.getChildren().add(ticketText);

            Image imgRemove, imgAdd;
            ImageView remove = null, add = null;

            JFXTextField quantity = new JFXTextField("0");
            quantity.setPrefWidth(20);
            quantity.setEditable(false);

            try {
                imgRemove = new Image(new FileInputStream("src/main/Resources/drawable/remove.png"));
                remove = new ImageView(imgRemove);
                remove.setFitHeight(24);
                remove.setFitWidth(24);

                imgAdd = new Image(new FileInputStream("src/main/Resources/drawable/add.png"));
                add = new ImageView(imgAdd);
                add.setFitHeight(24);
                add.setFitWidth(24);

                add.setOnMousePressed(e -> {

                    System.out.println("old value: " + quantity.getText());
                    System.out.println("Available tickets: " + availableTickets);
                    int num = Integer.valueOf(quantity.getText());

                    if ((availableTickets - (num + 1)) < 0) {
                        NotificationPane.show("Maximum ticket number has been reached");
                        return;
                    }

                    num++;

                    System.out.println("new value: " + num);
                    quantity.setText(String.valueOf(num));

                    ticketQuantity.put(ID, Integer.valueOf(quantity.getText()));
                });

                remove.setOnMousePressed(e -> {
                    System.out.println("old value: " + quantity.getText());
                    System.out.println("Available tickets: " + availableTickets);
                    int num = Integer.valueOf(quantity.getText());

                    if (!((num - 1) < 0)) {
                        num--;
                    }

                    System.out.println("new value: " + num);
                    quantity.setText(String.valueOf(num));

                    ticketQuantity.put(ID, Integer.valueOf(quantity.getText()));
                });
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            ticketBox.getChildren().addAll(textBox, remove, quantity, add);
            ticketLayout.getChildren().add(ticketBox);
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventNameText.setText(event.getEventName());
        eventLocationText.setText(event.getEventLocation());
        eventDateText.setText(event.getEventDate());
        eventDescriptionText.setText(event.getEventDescription());
        setBands();
        setTickets();
        makeBooking.setOnAction(e -> bookEvent());
    }
}
