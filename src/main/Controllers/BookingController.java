package main.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Entities.Booking;
import main.Entities.Entity;
import main.Entities.Event;
import main.Entities.User;
import main.Networking.JDBC;
import main.Utils.WriteLog;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingController extends Controller implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(BookingController.class.getName());
    private static BookingController instance = null;

    @FXML
    private TableView<Booking> Bookings;
    @FXML
    private TableColumn<Booking, Integer> bookingID, concertID, bookingPrice, bookingDate;
    @FXML
    private JFXButton allbookings, previousbookings, futurebookings;
    private User user;


    private BookingController() {
        instance = this;
        WriteLog.addHandler(LOGGER);
        LOGGER.log(Level.INFO, "Creating BookingController instance from constructor at: {0}\n", LocalTime.now());
    }

    public static BookingController getInstance() {
        if (instance == null) {
            synchronized (BookingController.class) {
                if (instance == null) {
                    return new BookingController();
                }
            }
        } else LOGGER.log(Level.WARNING, "Tried to create additional instance at: {0}\n", LocalTime.now());
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        user = RootController.getInstance().getUser();


        bookingID.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("viewName"));
        concertID.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("viewDate"));
        bookingPrice.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("viewLocation"));
        bookingDate.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("quantity"));

        loadTable();

        allbookings.setOnAction(e -> loadTable());
        previousbookings.setOnAction(e -> loadpasttable());
        futurebookings.setOnAction(e -> loadfuturetable());


    }


    private void loadTable() {

        Date date= new Date();

        Timestamp ts = new Timestamp(date.getTime());

        ArrayList<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM BOOKING WHERE User_ID = '" + user.getUserID() + "';";
        Booking temp;
        ArrayList<Entity> objects = JDBC.getAll(query, Booking.class.getName());
        for (Entity obj : objects) {
            temp = (Booking) obj;
            bookings.add(temp);

        }

        ArrayList<Event> events = new ArrayList<>();
        String query1 = "SELECT * FROM EVENTS;";
        Event temp1;
        ArrayList<Entity> objects1 = JDBC.getAll(query1, Event.class.getName());
        for (Entity obj : objects1) {
            temp1 = (Event) obj;
            events.add(temp1);

        }

        for (int i = 0; i < bookings.size(); i++) {
            int i1 = -1;
            do {
                i1++;
            }
            while (bookings.get(i).getEventID() != events.get(i1).getEventID());

            bookings.get(i).setViewDate(events.get(i1).getEventDate());
            bookings.get(i).setViewName(events.get(i1).getEventName());
            bookings.get(i).setViewLocation(events.get(i1).getEventLocation());

        }
        ObservableList<Booking> allbookingslist = FXCollections.observableArrayList(bookings);
        Bookings.setItems(allbookingslist);

        System.out.println("asdasdasdasdas");

    }

    private void loadpasttable() {

        Date date= new Date();

        Timestamp ts = new Timestamp(date.getTime());

        ArrayList<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM BOOKING WHERE User_ID = '" + user.getUserID() + "';";
        Booking temp;
        ArrayList<Entity> objects = JDBC.getAll(query, Booking.class.getName());
        for (Entity obj : objects) {
            temp = (Booking) obj;
            bookings.add(temp);

        }

        ArrayList<Event> events = new ArrayList<>();
        String query1 = "SELECT * FROM EVENTS;";
        Event temp1;
        ArrayList<Entity> objects1 = JDBC.getAll(query1, Event.class.getName());
        for (Entity obj : objects1) {
            temp1 = (Event) obj;
            events.add(temp1);

        }

        for (int i = 0; i < bookings.size(); i++) {
            int i1 = -1;
            do {
                i1++;
            }
            while (bookings.get(i).getEventID() != events.get(i1).getEventID());

            bookings.get(i).setViewDate(events.get(i1).getEventDate());
            bookings.get(i).setViewName(events.get(i1).getEventName());
            bookings.get(i).setViewLocation(events.get(i1).getEventLocation());

        }

        for (int i = 0; i <bookings.size(); i++) {
            if(bookings.get(i).getViewDateTS().after(ts))
            {
                bookings.remove(i);
                i--;
            }
        }

        ObservableList<Booking> allbookingslist = FXCollections.observableArrayList(bookings);
        Bookings.setItems(allbookingslist);

    }

    private void loadfuturetable() {

        Date date= new Date();

        Timestamp ts = new Timestamp(date.getTime());

        ArrayList<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM BOOKING WHERE User_ID = '" + user.getUserID() + "';";
        Booking temp;
        ArrayList<Entity> objects = JDBC.getAll(query, Booking.class.getName());
        for (Entity obj : objects) {
            temp = (Booking) obj;
            bookings.add(temp);

        }

        ArrayList<Event> events = new ArrayList<>();
        String query1 = "SELECT * FROM EVENTS;";
        Event temp1;
        ArrayList<Entity> objects1 = JDBC.getAll(query1, Event.class.getName());
        for (Entity obj : objects1) {
            temp1 = (Event) obj;
            events.add(temp1);

        }

        for (int i = 0; i < bookings.size(); i++) {
            int i1 = -1;
            do {
                i1++;
            }
            while (bookings.get(i).getEventID() != events.get(i1).getEventID());

            bookings.get(i).setViewDate(events.get(i1).getEventDate());
            bookings.get(i).setViewName(events.get(i1).getEventName());
            bookings.get(i).setViewLocation(events.get(i1).getEventLocation());

        }

        for (int i = 0; i <bookings.size(); i++) {
            if(bookings.get(i).getViewDateTS().before(ts))
            {
                bookings.remove(i);
                i--;
            }
        }

        ObservableList<Booking> allbookingslist = FXCollections.observableArrayList(bookings);
        Bookings.setItems(allbookingslist);

    }


    public static void Destroy () {}
}
