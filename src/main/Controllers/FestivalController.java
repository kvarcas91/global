package main.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import main.Entities.Booking;
import main.Entities.Entity;
import main.Entities.Event;
import main.Entities.User;
import main.Networking.JDBC;
import main.Utils.Loader;
import main.Utils.WriteLog;
import main.View.NotificationPane;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FestivalController extends Controller implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(FestivalController.class.getName());
    private static FestivalController instance = null;

    @FXML private JFXListView listView;
    @FXML private JFXButton btnAdd, mEvents;
    @FXML private HBox manageFestivals;
    @FXML private VBox container;

    private User user = null;
    ContextMenu menu = null;
    AccountTypes type;
    private static ArrayList<Event> events = new ArrayList<>();
    private static ArrayList<Event> clonedEvents = new ArrayList<>();
    private ArrayList<String> templates = new ArrayList<>();


    private FestivalController () {
        instance = this;
        WriteLog.addHandler(LOGGER);
        setTemplates();
        LOGGER.log(Level.INFO, "Creating FestivalController instance from constructor at: {0}\n", LocalTime.now());
    }

    public static FestivalController getInstance() {
        if (instance == null) {
            synchronized (FestivalController.class) {
                if (instance == null) {
                    return new FestivalController();
                }
            }
        }
        else LOGGER.log(Level.WARNING, "Tried to create additional instance at: {0}\n", LocalTime.now());

        return instance;
    }

    protected static void search (String s) {
        System.out.println("search for events: " + s);
        clonedEvents.clear();
        for (Event event : events) {
            if (event.contains(s)) {
                clonedEvents.add(event);
            }
        }

        getInstance().createEventView();

    }

    private void addFestivals() {
        Loader.getInstance().loadPage("../UI/addFestivals.fxml", AddFestivalsController.getInstance());
    }


    private ArrayList<Event> getEvents () {

        String query = "SELECT * FROM EVENTS";


        ArrayList<Entity> mList = JDBC.getAll(query, Event.class.getName());
        Event temp;
        ArrayList<Event> mEvents = new ArrayList<>();

        Timestamp currentDate = new Timestamp(System.currentTimeMillis());

        for (Entity e : mList) {
            temp = (Event) e;
            if (temp.getTimeStamp().after(currentDate)) {
                System.out.println(temp);
                mEvents.add(temp);
            }
        }

        return mEvents;
    }

    private void deleteEventAction (Event event) {

        if (user.getUserID() == event.getEventOrganiser() || AccountTypes.ROOT == type) {
            JDBC.delete("BOOKING", "Event_ID", event.getEventID());
            ArrayList<String> ID = JDBC.getValue(String.format(
                    "SELECT Type_ID FROM TICKET_TYPES WHERE Event_ID = '%s'", event.getEventID()), 1);

            if (ID != null) {
                for (String id : ID) {
                    JDBC.delete("BOOKING", "Ticket_Type", Integer.valueOf(id));
                }
                ID.clear();
            }

            ID = JDBC.getValue(String.format("SELECT Band_ID FROM EVENT_BANDS WHERE Event_ID = '%s'", event.getEventID()), 1);

            JDBC.delete("EVENT_BANDS", "Event_ID", event.getEventID());

            if (ID != null) {
                for (String id : ID) {
                    JDBC.delete("BANDS", "Band_ID", Integer.valueOf(id));
                }
                ID.clear();
            }
            JDBC.delete("TICKET_TYPES", "Event_ID", event.getEventID());

            if (JDBC.delete("EVENTS", "Event_ID", event.getEventID())) {
                NotificationPane.show("Event has been deleted", "green");

                events.remove(event);
                clonedEvents.remove(event);

                createEventView();
            }

            else NotificationPane.show("Something went wrong");
        }
        else {
            NotificationPane.show("You cannot delete this event");
        }
    }

    private void setMenu (MouseEvent e, Event event) {

        if (menu != null) menu.hide();

        menu = new ContextMenu();
        MenuItem bookEvent = new MenuItem("Book");
        menu.getItems().add(bookEvent);

        bookEvent.setOnAction(ev -> {
            Loader.getInstance().loadPage("../UI/createBooking.fxml", CreateBookingController.getInstance());
            CreateBookingController.setEvent(event);
        });

        if (type == AccountTypes.ADMIN || type == AccountTypes.ROOT || type == AccountTypes.ORGANISER) {

            MenuItem editEvent = new MenuItem("Edit");
            MenuItem deleteEvent = new MenuItem("Remove");
            menu.getItems().add(editEvent);
            menu.getItems().add(deleteEvent);

            // TODO prepare something for edit
            editEvent.setOnAction(ev -> System.out.println("edit\n" + event));

            deleteEvent.setOnAction(ev -> deleteEventAction(event));
        }

        if (e.isPrimaryButtonDown()) menu.show(container, e.getScreenX()+20, e.getSceneY()+100);

    }

    private void createEventView () {


        Random random = new Random();

        container.getChildren().clear();

        if (!clonedEvents.isEmpty()) {
            for (Event event : clonedEvents) {

                VBox mainBox = new VBox();
                mainBox.setAlignment(Pos.CENTER_LEFT);

                StackPane stack = new StackPane();
                HBox imageBox = new HBox();
                imageBox.setAlignment(Pos.CENTER_RIGHT);
                stack.setStyle("-fx-background-color: black");

                try {
                    int pos = random.nextInt(templates.size()-1);
                    Image image = new Image(new FileInputStream(templates.get(pos)));
                    ImageView imageView = new ImageView(image);
                    imageBox.getChildren().add(imageView);
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                stack.getChildren().add(imageBox);

                mainBox.setStyle("-fx-background-color: transparent");
                mainBox.setPadding(new Insets(5, 5, 5, 5));

                Label eventName = new Label(event.getEventName());
                eventName.setStyle("-fx-font-size: 22px; -fx-font-weight: 700; -fx-text-fill: #FFFFFF;");

                HBox box = new HBox();

                Label eventDate = new Label(event.getEventDate());
                eventDate.setPadding(new Insets(0, 20, 0, 5));
                Label eventLocation = new Label(event.getEventLocation());

                eventLocation.setStyle("-fx-text-fill: #FFFFFF");
                eventDate.setStyle("-fx-text-fill: #FFFFFF");
                box.getChildren().add(eventDate);
                box.getChildren().add(eventLocation);

                String totalSlotQuery = String.format("SELECT SUM(Type_Slots) FROM TICKET_TYPES WHERE Event_ID = '%s'", event.getEventID());
                String bookedTicketQuery = String.format("SELECT SUM(Quantity) FROM BOOKING WHERE Event_ID = '%s'", event.getEventID());

                ArrayList<String> ID = JDBC.getValue(bookedTicketQuery, 1);

                String bookedQuantity;
                if (ID != null) {
                    if (ID.get(0) != null) bookedQuantity = ID.get(0);
                    else bookedQuantity = "0";
                    ID.clear();
                }
                else bookedQuantity = "0";

                ID = JDBC.getValue(totalSlotQuery, 1);

                String totalQuantity;
                if (ID != null) {
                    if (ID.get(0) != null) totalQuantity = ID.get(0);
                    else totalQuantity = "?";
                    ID.clear();
                }
                else totalQuantity = "?";

                Label slot = new Label(String.format("%s / %s",bookedQuantity, totalQuantity));
                slot.setStyle("-fx-text-fill: #FFFFFF");

                mainBox.getChildren().add(eventName);
                mainBox.getChildren().add(box);
                mainBox.getChildren().add(slot);

                stack.getChildren().add(mainBox);

                container.getChildren().add(stack);

                mainBox.setOnMousePressed(e -> setMenu(e, event));
            }

        }
        else {
            NotificationPane.show("No festivals", "green");
        }
    }


    private void setTemplates () {

        templates.add("src/main/Resources/drawable/festival_templates/template_1.png");
        templates.add("src/main/Resources/drawable/festival_templates/template_2.png");
        templates.add("src/main/Resources/drawable/festival_templates/template_3.png");
        templates.add("src/main/Resources/drawable/festival_templates/template_4.png");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = RootController.getInstance().getUser();
        type = getType(user.getAccountType());

        if (!events.isEmpty()) events.clear();
        if (!clonedEvents.isEmpty()) clonedEvents.clear();

        if (AccountTypes.ORGANISER == type || AccountTypes.ROOT == type || AccountTypes.ADMIN == type) {
            manageFestivals.setVisible(true);
            btnAdd.setOnAction(e -> addFestivals());

            mEvents.setOnAction(e -> createEventView());
        }

        events = getEvents();
        clonedEvents.addAll(events);

        createEventView();
    }

    public static void Destroy () {
        if (instance != null) {
            LOGGER.log(Level.INFO, "Destroying FestivalController at: {0}\n", LocalTime.now());
            instance = null;
        }
    }
}