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
import java.time.LocalDate;
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
    @FXML private JFXButton addEvent, mEvents;
    @FXML private HBox progressLayout;
    @FXML private GridPane eventInfo;
    @FXML private BorderPane ticketInfo, bandInfo;
    @FXML private ImageView navBack, navNext, addTicket, addBand;
    @FXML private TilePane ticketTile, bandTile;

    private byte position = 0;
    private boolean editable = false;
    private HashMap<String, Pane> scenes = new HashMap<>();
    private ArrayList<TicketType> tickets = new ArrayList<>();
    private ArrayList<TicketType> oldTickets = new ArrayList<>();

    private ArrayList<Bands> bands = new ArrayList<>();
    private ArrayList<Bands> oldBands = new ArrayList<>();

    private ArrayList<String> ID = new ArrayList<>();
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
        if (ticketName.getText().isEmpty() || ticketSlots.getText().isEmpty() || ticketPrice.getText().isEmpty()) {
            NotificationPane.show("You need to fill all fields");
            return;
        }

        if (!ticketSlots.getText().matches("^[0-9]+$")) {
            NotificationPane.show("Ticket Slots can be just a number");
            return;
        }

        if (!(ticketPrice.getText().matches("([0-9]*)\\.([0-9]*)") || ticketPrice.getText().matches("^[0-9]+$"))) {
            NotificationPane.show("Ticket price can be just a number");
            return;
        }



        TicketType ticket = new TicketType(ticketName.getText(), Integer.parseInt(ticketSlots.getText()), Double.parseDouble(ticketPrice.getText()),
                false);

        tickets.add(ticket);
        setTicketView();

    }

    private void setTicketView () {

        System.out.println("Setting ticket view");
        ticketTile.getChildren().clear();

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
                e.printStackTrace();
            }
            Label ticketName = new Label(ticket.getName());
            box.getChildren().add(ticketName);
            ticketTile.getChildren().add(box);

            box.setOnMousePressed(e -> {
                ticketTile.getChildren().remove(box);
                tickets.remove(ticket);
            });
        }

        ticketPrice.clear();
        this.ticketName.clear();
        ticketSlots.clear();

        this.ticketName.requestFocus();
    }

    private void addBandEvent () {
        System.out.println("addBandEvent");
       if (bandName.getText().isEmpty() || bandAgent.getText().isEmpty()) {
           NotificationPane.show("You need to fill all fields");
           return;
       }

       Bands band = new Bands(bandName.getText(), bandAgent.getText());

       bands.add(band);
       setBandView();

    }

    private void setBandView () {

        System.out.println("Setting band view");
        bandTile.getChildren().clear();

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
                e.printStackTrace();
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
        if (!editable) {
            event = new Event(eventName.getText(), date, eventLocation.getText(), eventDescription.getText(), RootController.getInstance().getUser().getUserID());
        }
        else {
            event.setEventName(eventName.getText());
            event.setEventLocation(eventLocation.getText());
            event.setEventDescription(eventDescription.getText());
            event.setEventDate(date);
        }

        if (tickets.isEmpty() || bands.isEmpty()) {
            NotificationPane.show("You must add ticket types and bands");
            return;
        }

        if (!editable) {
            if (JDBC.insert(event.getInsertQuery())) {
                String query = String.format("SELECT Event_ID FROM EVENTS WHERE Event_Name = '%s' AND Event_Date = '%s' AND Event_Location = '%s' AND Event_Description = '%s' AND Event_Organiser = '%s'",
                        event.getEventName(), event.getEventDate(), event.getEventLocation(), event.getEventDescription(), event.getEventOrganiser());

                ID = JDBC.getValues(query, 1);
                event.setEventID(Integer.valueOf(ID.get(ID.size() - 1)));
                ID.clear();

                NotificationPane.show("Event has been added", "green");
            } else NotificationPane.show("Cannot add event");

        }
        else {
            if(JDBC.update(event.getUpdateQuery())) {
                NotificationPane.show("Event has been updated", "green");
            }
            else NotificationPane.show("Cannot update event");
        }

        for (Bands band : bands) {
            if (!oldBands.contains(band)) {
                if (JDBC.insert(band.getInsertQuery())) {

                    String query = String.format("SELECT Band_ID FROM BANDS WHERE Band_Name = '%s' AND Band_Agent = '%s'", band.getName(), band.getAgent());

                    ID = JDBC.getValues(query, 1);
                    band.setID(Integer.valueOf(ID.get(ID.size() - 1)));

                    ID.clear();

                    query = String.format("INSERT INTO EVENT_BANDS VALUES ('%d', '%d');", event.getEventID(), band.getID());
                    JDBC.insert(query);
                }
                else NotificationPane.show("Cannot add band");
            }
            else {
                oldBands.remove(band);
            }
        }

        if (!oldBands.isEmpty()) {
            for (Bands band : oldBands) {
                JDBC.delete("EVENT_BANDS", "Band_ID", band.getID());
                JDBC.delete("BANDS", "Band_ID", band.getID());
            }
            oldBands.clear();
        }

        for (TicketType ticket : tickets) {
            if (!oldTickets.contains(ticket)) {
                ticket.setEventID(event.getEventID());

                if (JDBC.insert(ticket.getInsertQuery())) {
                    String query = String.format("SELECT Type_ID FROM TICKET_TYPES WHERE Type_Name = '%s' AND Type_Slots = '%d' AND Type_Price = '%f'",
                            ticket.getName(), ticket.getSlot(), ticket.getPrice());

                    ID = JDBC.getValues(query, 1);
                    ticket.setID(Integer.valueOf(ID.get(ID.size() - 1)));
                    ID.clear();

                    System.out.println("Ticket: \n" + ticket);
                } else NotificationPane.show("Cannot add ticket");
            }
            else {
                oldTickets.remove(ticket);
            }
        }

        if (!oldTickets.isEmpty()) {
            for (TicketType ticket : oldTickets) {
                JDBC.delete("TICKET_TYPES", "Type_ID", ticket.getID());
            }
            oldTickets.clear();
        }

        tickets.clear();
        bands.clear();
        position = 0;
        editable = false;
        instance = null;

        Loader.getInstance().loadPage("../UI/festivals.fxml", FestivalController.getInstance());

    }

    private void setVisibility (String scene) {
        this.scenes.forEach((k, v) -> {
            System.out.println(String.format("Visibility: scene: %s; k: %s;", scene, k));
            if (scene.equals(k)) v.setVisible(true);
            else v.setVisible(false);
        });
    }

    protected void editFestival (Event event, ArrayList<Bands> bands, ArrayList<TicketType> tickets) {
        editable = true;

        if (!this.tickets.isEmpty()) this.tickets.clear();
        if (!this.bands.isEmpty()) this.bands.clear();

        this.tickets.addAll(tickets);
        this.oldTickets.addAll(tickets);

        this.bands.addAll(bands);
        this.oldBands.addAll(bands);
        this.event = event;

        setTicketView();
        setBandView();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(event.getEventDate(), formatter);

        eventDate.setValue(localDate);
        eventDescription.setText(event.getEventDescription());
        eventLocation.setText(event.getEventLocation());
        eventName.setText(event.getEventName());
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
        mEvents.setOnAction(e -> Loader.getInstance().loadPage("../UI/festivals.fxml", FestivalController.getInstance()));

        setLayout("eventInfo");
    }
}


