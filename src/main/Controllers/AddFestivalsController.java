package main.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import java.net.URL;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import main.Entities.Bands;
import main.Entities.Event;
import main.Entities.TicketType;
import main.Networking.JDBC;
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
    @FXML private ImageView btnBack;
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
        // TODO check if this stage is completed and only then allow user to go to the next stage. Implement some sort of verification
        if (position < 2) {
            position++;
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
        if (position > 0) {
            position--;
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
        // TODO verify textfields. if they are not empty, if they contain only numbers for slot and double for price
        // TODO isCorp??? ask Piotr
        TicketType ticket = new TicketType(ticketName.getText(), Integer.parseInt(ticketSlots.getText()), Double.parseDouble(ticketPrice.getText()),
                false);

        if (JDBC.insert(ticket.getInsertQuery())) {
            // TODO insert into EVENT_TICKETS somehow
            setTicketView(ticket);
        }
        else NotificationPane.show("Cannot add ticket");
    }

    private void setTicketView (TicketType ticket) {
        tickets.add(ticket);
        System.out.println("Setting ticket view");
        HBox box = new HBox();
        Label ticketName = new Label(ticket.getName());
        box.getChildren().add(ticketName);
        ticketTile.getChildren().add(box);

        box.setOnMousePressed(e -> {
            ticketTile.getChildren().remove(box);
            // TODO remove from TICKET_TYPES from database
        });

        ticketPrice.clear();
        this.ticketName.clear();
        ticketSlots.clear();
    }

    private void addBandEvent () {
        System.out.println("addBandEvent");
        // TODO verify textfields. if they are not empty etc.
       Bands band = new Bands(bandName.getText(), bandAgent.getText());

        if (JDBC.insert(band.getInsertQuery())) {
            setBandView(band);
        }
        else NotificationPane.show("Cannot add band");
    }

    private void setBandView (Bands band) {
        bands.add(band);
        System.out.println("Setting band view");
        HBox box = new HBox();
        Label bandLabel = new Label(band.getName());
        box.getChildren().add(bandLabel);
        bandTile.getChildren().add(box);

        box.setOnMousePressed(e -> {
            bandTile.getChildren().remove(box);
            // TODO remove from BANDS from database
        });

        bandName.clear();
        bandAgent.clear();
    }

    private void createEvent () {
        // TODO verify if textfields are not empty etc.

        String date = eventDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        event = new Event(eventName.getText(), date, eventLocation.getText(), eventDescription.getText(), RootController.getInstance().getUser().getUserID());
        if (JDBC.insert(event.getInsertQuery())) {
            NotificationPane.show("Event has been added", "green");
            // TODO go back to events page
            // TODO insert into EVENT_TICKETS and EVENT_BANDS. check arrayLists for insert material.
            // TODO ask Piotr how to get event, band and ticketID after insertion because it is autoincremented
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

        // TODO in addFestival.fxml addEvent button doesn't look nice. Make it on the bottom in the center

        if (Circles.getInstance() == null)  Circles.createInstance(progressLayout);
        else Circles.injectLayout(progressLayout);

        scenes.put("eventInfo", eventInfo);
        scenes.put("ticketInfo", ticketInfo);
        scenes.put("bandInfo", bandInfo);

        navBack.setOnMousePressed(e -> back());
        navNext.setOnMousePressed(e -> next());
        addTicket.setOnMousePressed(e -> addTicketEvent());
        addBand.setOnMousePressed(e -> addBandEvent());
        addEvent.setOnAction(e -> createEvent());

        setLayout("eventInfo");
    }
}


