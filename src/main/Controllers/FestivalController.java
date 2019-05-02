package main.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Entities.Entity;
import main.Entities.Event;
import main.Entities.User;
import main.Networking.JDBC;
import main.Utils.Loader;
import main.Utils.Test.Test;
import main.Utils.WriteLog;
import main.View.NotificationPane;

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
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


    private FestivalController () {
        instance = this;
        WriteLog.addHandler(LOGGER);
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

    private void addFestivals() {
        Loader.getInstance().loadPage("../UI/addFestivals.fxml", addFestivalsController.getInstance());
    }


    private ArrayList<Event> getEvents (String id) {
        String query;
        if (id != null) {
            query = String.format("SELECT * FROM EVENTS INNER JOIN USERS ON EVENTS.Event_Organiser = USERS.User_ID WHERE USERS.User_ID = '%s'", id);
        }
        else {
            query = "SELECT * FROM EVENTS";
        }
        ArrayList<Entity> mList = JDBC.getAll(query, Event.class.getName());
        Event temp;
        ArrayList<Event> mEvents = new ArrayList<>();

        for (Entity e : mList) {
            temp = (Event) e;
            mEvents.add(temp);
        }

        for (Event e : mEvents) {
            System.out.println(e);
        }

        return mEvents;
    }

    private void deleteEventAction (Event event) {
        //TODO check if it has any dependency
        if (user.getUserID() == event.getEventOrganiser()) {
            if (JDBC.delete("EVENTS", "Event_ID", event.getEventID()))
                NotificationPane.show("Event has been deleted", "green");
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

    private void createEventView (String id) {

        ArrayList<Event> events = getEvents(id);

        container.getChildren().clear();

        if (!events.isEmpty()) {
            for (Event event : events) {
                VBox mainBox = new VBox();
                mainBox.setStyle("-fx-background-color: #FFFFFF");
                mainBox.setPadding(new Insets(5, 5, 5, 5));

                Label eventName = new Label(event.getEventName());
                eventName.setStyle("-fx-font-size: 14px; -fx-font-weight: 700;");

                HBox box = new HBox();
                Label eventDate = new Label(event.getEventDate());
                eventDate.setPadding(new Insets(0, 20, 0, 5));
                Label eventLocation = new Label(event.getEventLocation());

                box.getChildren().add(eventDate);
                box.getChildren().add(eventLocation);

                mainBox.getChildren().add(eventName);
                mainBox.getChildren().add(box);
                container.getChildren().add(mainBox);

                mainBox.setOnMousePressed(e -> setMenu(e, event));
            }

        }
        else {
            NotificationPane.show("No festivals", "green");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = RootController.getInstance().getUser();
        type = getType(user.getAccountType());
        System.out.println("user tpe: " + user.getAccountType());
        System.out.println("type: " + type.toString());

        if (AccountTypes.ORGANISER == type || AccountTypes.ROOT == type || AccountTypes.ADMIN == type) {
            manageFestivals.setVisible(true);
            btnAdd.setOnAction(e -> addFestivals());
            mEvents.setOnAction(e -> createEventView(String.valueOf(user.getUserID())));
        }

        createEventView(null);

    }

    public static void Destroy () {
        if (instance != null) {
            LOGGER.log(Level.INFO, "Destroying FestivalController at: {0}\n", LocalTime.now());
            instance = null;
        }
    }

}