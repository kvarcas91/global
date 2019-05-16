package main.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.Entities.*;
import main.Networking.JDBC;
import main.Utils.WriteLog;

import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingController extends Controller implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(BookingController.class.getName());
    private static BookingController instance = null;

    @FXML private VBox maincontainer;
    @FXML private JFXButton allbookings, previousbookings, futurebookings;
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
        Date date= new Date();

        Timestamp ts = new Timestamp(date.getTime());


        loadAll();

        allbookings.setOnAction(e -> loadAll());
        previousbookings.setOnAction(e -> loadPasttable(ts));
        futurebookings.setOnAction(e -> loadFuturetable(ts));


    }

    private void loadPasttable(Timestamp ts) {
        ArrayList<Invoice> invoices = pullData();
        for(int i = 0; i<invoices.size(); i++){
            if(invoices.get(i).getEventDateString().after(ts)) {
                invoices.remove(i);
                i--;
            }
        }
        createView(invoices);


    }

    private void loadFuturetable(Timestamp ts) {
        ArrayList<Invoice> invoices = pullData();
        for(int i = 0; i<invoices.size(); i++){
            if(invoices.get(i).getEventDateString().before(ts)) {
                invoices.remove(i);
                i--;
            }
        }
        createView(invoices);


    }

    private void loadAll() {
        ArrayList<Invoice> invoices = pullData();
        createView(invoices);

    }

    private ArrayList<Invoice> pullData() {
        ArrayList<Invoice> invoices = new ArrayList<>();
        Date date = new Date();

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

        for (int i =0; i < bookings.size(); i++) {
            invoices.add(new Invoice(bookings.get(i).getViewName(),bookings.get(i).getBookingDate(),
                    bookings.get(i).getViewDate(),bookings.get(i).getQuantity(),bookings.get(i).getTicketTypeID(),pullTicketinfo(bookings.get(i).getTicketTypeID())));

            System.out.println();
        }



        return (invoices);


    }

    private void createView(ArrayList<Invoice> Invoices) {
        maincontainer.getChildren().clear();


        VBox Container = new VBox();
        Container.setSpacing(3);
        int totalTickets = 0;
        double totalPrice = 0;
        for (int i = 0; i < Invoices.size(); i++) {

            VBox Invoice = new VBox();
            Invoice.setStyle("-fx-background-color:grey; -fx-border-color:black");
            Label EventName = new Label();
            EventName.setText(Invoices.get(i).getEventName());

            Label EventDate = new Label();
            EventDate.setText("Event Date: " + (Invoices.get(i).getEventDate()));

            Label BookingDate = new Label();
            BookingDate.setText("Booking Date: " + (Invoices.get(i).getBookingDate()));

            Label EventTicketQuantity = new Label();
            EventTicketQuantity.setText("Tickets: " + Integer.toString(Invoices.get(i).getTicketQuantity()));

            Label EventTicketPrice = new Label();
            EventTicketPrice.setText("Price per Ticket: " + Double.toString(Invoices.get(i).getTicketPrice()) + "£");

            Label TotalPrice = new Label();
            TotalPrice.setText("Total: " + Double.toString(Invoices.get(i).getTicketQuantity() * Invoices.get(i).getTicketPrice()) + "£");

            Invoice.getChildren().add(EventName);
            Invoice.getChildren().add(EventDate);
            Invoice.getChildren().add(BookingDate);
            Invoice.getChildren().add(EventTicketQuantity);
            Invoice.getChildren().add(EventTicketPrice);
            Container.getChildren().add(Invoice);

        }





        maincontainer.getChildren().add(Container);
    }

    private double pullTicketinfo(int TicketTypeID) {
        ArrayList<TicketType> ticket = new ArrayList<>();
        String query = "SELECT * FROM TICKET_TYPES WHERE Type_ID = '" + TicketTypeID + "';";
        TicketType temp;
        ArrayList<Entity> objects = JDBC.getAll(query, TicketType.class.getName());
        for (Entity obj : objects) {
            temp = (TicketType) obj;
            ticket.add(temp);

        }
        System.out.println("pullticket excecuted");
        return ticket.get(0).getPrice();
    }

    public static void Destroy () {}
}
