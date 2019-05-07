package main.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import main.Entities.Bands;
import main.Entities.Event;
import main.Entities.TicketType;
import main.Networking.JDBC;
import main.Utils.Loader;
import main.Utils.WriteLog;
import main.View.Circles;
import main.View.NotificationPane;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddFestivalsController extends Controller implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(AddFestivalsController.class.getName());
    private static AddFestivalsController instance = null;

    @FXML private JFXTextField ticketName, ticketSlots, ticketPrice, bandName, bandAgent, eventName, eventLocation;
    @FXML private JFXTextArea eventDescription;
    @FXML private DatePicker eventDate;
    @FXML private JFXButton addEvent;
    @FXML private HBox progressLayout;
    @FXML private GridPane eventInfo;
    @FXML private BorderPane ticketInfo, bandInfo;
    @FXML private ImageView navBack, navNext, addTicket, addBand;
    @FXML private TilePane ticketTile, bandTile;

    private byte position = 0;
    private HashMap<String, Pane> scenes = new HashMap<>();
    private ArrayList<TicketType> tickets = new ArrayList<>();
    private ArrayList<Bands> bands = new ArrayList<>();
    private Event event;

    private AddFestivalsController() {
        instance = this;
        WriteLog.addHandler(LOGGER);
        LOGGER.log(Level.INFO, "Creating addFestivalsController instance from constructor at: {0}\n", LocalTime.now());
    }


    public static AddFestivalsController getInstance() {
        if (instance == null) {
            synchronized (FestivalController.class) {
                if (instance == null) {
                    return new AddFestivalsController();
                }
            }
        }
        else LOGGER.log(Level.WARNING, "Tried to create additional instance at: {0}\n", LocalTime.now());
        return instance;
    }

    private void setLayout (String scene) {

        if (scene != null) {
            setVisibility(scene);
            Circles.draw(position);
        }
    }

    private void next () {
        navBack.setVisible(true);
        if (position < 2) {
            position++;
            if (position == 2) {
                navNext.setVisible(false);
            }

            switch (position) {
                case 1:
                    setLayout("ticketInfo");
                    break;
                case 2:
                    setLayout("bandInfo");
                    break;
            }

        }
    }

    private void back () {
        navNext.setVisible(true);
        if (position > 0) {
            position--;

            if (position == 0) {
                navBack.setVisible(false);
            }
            switch (position) {
                case 0:
                    setLayout("eventInfo");
                    break;
                case 1:
                    setLayout("ticketInfo");
                    break;
            }
        }
    }

    private void addTicketEvent () {
        System.out.println("addTicketEvent");
        // TODO isCorp??? ask Piotr
        if (ticketName.getText().isEmpty() || ticketSlots.getText().isEmpty() || ticketPrice.getText().isEmpty()) {
            NotificationPane.show("You need to fill all fields");
            return;
        }

        if (!ticketSlots.getText().matches("^[0-9]+$")) {
            NotificationPane.show("Ticket Slots can be just a number");
            return;
        }

        if (!ticketPrice.getText().matches("([0-9]*)\\.([0-9]*)")) {
            NotificationPane.show("Ticket price can be just a number");
            return;
        }

        if (tickets.isEmpty() || bands.isEmpty()) {
            NotificationPane.show("You need to add tickets and bands first");
            return;
        }

        TicketType ticket = new TicketType(ticketName.getText(), Integer.parseInt(ticketSlots.getText()), Double.parseDouble(ticketPrice.getText()),
                false);

        if (JDBC.insert(ticket.getInsertQuery())) {
            String query = String.format("SELECT * FROM TICKET_TYPES WHERE Type_Name = '%s' AND Type_Slots = '%d' AND Type_Price = '%f'",
                    ticket.getName(), ticket.getSlot(), ticket.getPrice());
            ticket.setID(JDBC.getID(query, "Type_ID"));
            tickets.add(ticket);
            setTicketView();
        }
        else NotificationPane.show("Cannot add ticket");


    }

    private void setTicketView () {

        System.out.println("Setting ticket view");
        for (TicketType ticket : tickets) {
            HBox box = new HBox();
            box.setSpacing(5);
            box.setPadding(new Insets(5, 5, 5, 5));
            box.setStyle("-fx-border-radius: 20px;\n" +
                    "    -fx-background-radius: 10px;\n" +
                    "    -fx-font-size: 14px;\n" +
                    "    -fx-font-weight: 700;\n" +
                    "    -fx-border-size: 10px;\n" +
                    "    -fx-border-color: black;");

            ImageView cancelView = new ImageView();
            try {
                Image cancelImg = new Image(new FileInputStream("src/main/Resources/Drawable/cancel.png"));
                cancelView.setImage(cancelImg);
                cancelView.setFitWidth(18);
                cancelView.setFitHeight(18);
                box.getChildren().add(cancelView);
            } catch (FileNotFoundException e) {

            }
            Label ticketName = new Label(ticket.getName());
            box.getChildren().add(ticketName);
            ticketTile.getChildren().add(box);

            box.setOnMousePressed(e -> {
                ticketTile.getChildren().remove(box);
                JDBC.delete("TICKET_TYPES", "Type_ID", ticket.getID());
                tickets.remove(ticket);
            });
        }

        ticketPrice.clear();
        this.ticketName.clear();
        ticketSlots.clear();
    }

    private void addBandEvent () {
        System.out.println("addBandEvent");
       if (bandName.getText().isEmpty() || bandAgent.getText().isEmpty()) {
           NotificationPane.show("You need to fill all fields");
           return;
       }

       Bands band = new Bands(bandName.getText(), bandAgent.getText());

        if (JDBC.insert(band.getInsertQuery())) {
            String query = String.format("SELECT * FROM BANDS WHERE Band_Name = '%s' AND Band_Agent = '%s'", band.getName(), band.getAgent());
            band.setID(JDBC.getID(query, "Band_ID"));
            bands.add(band);
            setBandView();
        }
        else NotificationPane.show("Cannot add band");
    }

    private void setBandView () {

        System.out.println("Setting band view");
        for (Bands band : bands) {
            HBox box = new HBox();
            box.setSpacing(5);
            box.setPadding(new Insets(5, 5, 5, 5));
            box.setStyle("-fx-border-radius: 20px;\n" +
                    "    -fx-background-radius: 10px;\n" +
                    "    -fx-font-size: 14px;\n" +
                    "    -fx-font-weight: 700;\n" +
                    "    -fx-border-size: 10px;\n" +
                    "    -fx-border-color: black;");

            ImageView cancelView = new ImageView();
            try {
                Image cancelImg = new Image(new FileInputStream("src/main/Resources/Drawable/cancel.png"));
                cancelView.setImage(cancelImg);
                cancelView.setFitWidth(18);
                cancelView.setFitHeight(18);
                box.getChildren().add(cancelView);
            } catch (FileNotFoundException e) {

            }

            Label bandLabel = new Label(band.getName());
            box.getChildren().add(bandLabel);
            bandTile.getChildren().add(box);

            box.setOnMousePressed(e -> {
                bandTile.getChildren().remove(box);
                bands.remove(band);
                JDBC.delete("BANDS", "Band_ID", band.getID());
            });

        }

        bandName.clear();
        bandAgent.clear();
    }

    private void createEvent () {

        if (eventDate.getValue() == null || eventName.getText().isEmpty() || eventLocation.getText().isEmpty() || eventDescription.getText().isEmpty()) {
            NotificationPane.show("You need to fill all fields");
            return;
        }
        String date = eventDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        event = new Event(eventName.getText(), date, eventLocation.getText(), eventDescription.getText(), RootController.getInstance().getUser().getUserID());

        if (tickets.isEmpty() || bands.isEmpty()) {
            NotificationPane.show("You must add ticket types and bands");
            return;
        }

        if (JDBC.insert(event.getInsertQuery())) {
            String query = String.format("SELECT * FROM EVENTS WHERE Event_Name = '%s' AND Event_Date = '%s' AND Event_Location = '%s' AND Event_Description = '%s' AND Event_Organiser = '%s'",
                    event.getEventName(), event.getEventDate(), event.getEventLocation(), event.getEventDescription(), event.getEventOrganiser());
            event.setEventID(JDBC.getID(query, "Event_ID"));

            for (TicketType ticket : tickets) {
                query = String.format("INSERT INTO EVENT_TICKETS VALUES ('%d', '%d');", event.getEventID(), ticket.getID());
                JDBC.insert(query);
            }


            for (Bands band : bands) {
                query = String.format("INSERT INTO EVENT_TICKETS VALUES ('%d', '%d');", event.getEventID(), band.getID());
                JDBC.insert(query);
            }

            NotificationPane.show("Event has been added", "green");
            // TODO loader
            //Loader.getInstance().loadPage("..UI/festivals.fxml", FestivalController.getInstance());
        }
        else NotificationPane.show("Cannot add event");
    }

    private void setVisibility (String scene) {
        this.scenes.forEach((k, v) -> {
            System.out.println(String.format("Visibility: scene: %s; k: %s;", scene, k));
            if (scene.equals(k)) v.setVisible(true);
            else v.setVisible(false);
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (Circles.getInstance() == null)  Circles.createInstance(progressLayout);
        else Circles.injectLayout(progressLayout);

        scenes.put("eventInfo", eventInfo);
        scenes.put("ticketInfo", ticketInfo);
        scenes.put("bandInfo", bandInfo);

        navBack.setOnMousePressed(e -> back());
        navBack.setVisible(false);
        navNext.setOnMousePressed(e -> next());
        addTicket.setOnMousePressed(e -> addTicketEvent());
        addBand.setOnMousePressed(e -> addBandEvent());
        addEvent.setOnAction(e -> createEvent());

        setLayout("eventInfo");
    }
}


