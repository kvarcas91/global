package main.Controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.Entities.*;
import main.Networking.JDBC;
import main.Utils.WriteLog;
import main.View.NotificationPane;

import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvoiceController extends Controller implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(InvoiceController.class.getName());
    private static InvoiceController instance = null;
    @FXML private Label SummaryE, SummaryT, SummaryP;
    @FXML private VBox maincontainer;
    @FXML private DatePicker datepicker;
    @FXML private JFXComboBox<Integer> comboyear ;
    @FXML private JFXComboBox<String> combomonth;
    private User user;
    private final String timestampFormat = "yyyy-MM-dd";


    private InvoiceController() {
        instance = this;
        WriteLog.addHandler(LOGGER);
        LOGGER.log(Level.INFO, "Creating InvoiceController instance from constructor at: {0}\n", LocalTime.now());
    }

    public static InvoiceController getInstance() {
        if (instance == null) {
            synchronized (InvoiceController.class) {
                if (instance == null) {
                    return new InvoiceController();
                }
            }
        } else LOGGER.log(Level.WARNING, "Tried to create additional instance at: {0}\n", LocalTime.now());
        return instance;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("inti Invoice");
        System.out.println("Active controller: " + MenuController.getActiveController());
        user = RootController.getInstance().getUser();
        if (user != null) {
            Date date = new Date();

            Timestamp ts = new Timestamp(date.getTime());

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(ts.getTime());

            ArrayList<Integer> year = new ArrayList<>();
            int yeartemp = cal.get(Calendar.YEAR);
            for (int i = 0; i < 5; i++) {

                year.add(yeartemp);
                System.out.println(yeartemp);
                yeartemp--;
            }
            ObservableList<Integer> Oyear = FXCollections.observableArrayList(year);
            comboyear.setItems(Oyear);

            ArrayList<String> month = new ArrayList<>();
            month.add("January");
            month.add("February");
            month.add("March");
            month.add("April");
            month.add("May");
            month.add("June");
            month.add("July");
            month.add("August");
            month.add("September");
            month.add("October");
            month.add("November");
            month.add("December");
            ObservableList<String> Omonth = FXCollections.observableArrayList(month);
            combomonth.setItems(Omonth);

            comboyear.setValue(cal.get(Calendar.YEAR));
            combomonth.setValue(numberToMonth(cal.get(Calendar.MONTH) - 1));
            actioncombomonth();


            comboyear.setOnAction(e -> actionComboYear(cal));
            combomonth.setOnAction(e -> actioncombomonth());
        }
    }

    private void actionComboYear(Calendar cal) {
        System.out.println("invoice: actionComboYear");
        if (combomonth.getValue()!= null)
        {
            actioncombomonth();
        }
    }

    private void actioncombomonth () {
        System.out.println("invoice: actionComboMonth");
        ArrayList<Invoice> invoices = pullData();
        ArrayList<Invoice> displayInvoices = new ArrayList<>();
        Timestamp selectedDate = setDate(Integer.toString(comboyear.getValue()) + "-" + (Integer.parseInt(monthToNumber(combomonth.getValue()))-1) + "-26");
        Timestamp selectedDateRange = setDate(Integer.toString(comboyear.getValue()) + "-" + monthToNumber(combomonth.getValue()) + "-26");
        for (int i = 0; i<invoices.size(); i++) {
            if (invoices.get(i).getEventDateString().after(selectedDate) && invoices.get(i).getEventDateString().before(selectedDateRange)) {
                displayInvoices.add(invoices.get(i));
            }
        }

        if (displayInvoices.size() != 0) {
            createView(displayInvoices);
        }
        else {
            NotificationPane.show("You don't have any Invoice for the selected month");
            maincontainer.getChildren().clear();
            SummaryE.setText(null);
            SummaryP.setText(null);
            SummaryT.setText(null);

        }
    }

    private String monthToNumber(String month) {
        System.out.println("invoice: monthToNumber");
        String monthNumber = "";

        switch(month) {
            case "January":
                monthNumber = "1";
                break;
            case "February":
                monthNumber = "2";
                break;
            case "March":
                monthNumber = "3";
                break;
            case "April":
                monthNumber = "4";
                break;
            case "May":
                monthNumber = "5";
                break;
            case "June":
                monthNumber = "6";
                break;
            case "July":
                monthNumber = "7";
                break;
            case "August":
                monthNumber = "8";
                break;
            case "September":
                monthNumber = "9";
                break;
            case "October":
                monthNumber = "10";
                break;
            case "November":
                monthNumber = "11";
                break;
            case "December":
                monthNumber = "12";
                break;
        }
        System.out.println("actuib monthtonumber execcuted");
        return monthNumber;

    }

    private String numberToMonth(int month) {
        System.out.println("invoice: numberToMonth");
        String monthNumber = "";

        switch(month) {
            case 0:
                monthNumber = "January";
                break;
            case 1:
                monthNumber = "February";
                break;
            case 2:
                monthNumber = "March";
                break;
            case 3:
                monthNumber = "April";
                break;
            case 4:
                monthNumber = "May";
                break;
            case 5:
                monthNumber = "June";
                break;
            case 6:
                monthNumber = "July";
                break;
            case 7:
                monthNumber = "August";
                break;
            case 8:
                monthNumber = "September";
                break;
            case 9:
                monthNumber = "October";
                break;
            case 10:
                monthNumber = "November";
                break;
            case 11:
                monthNumber = "December";
                break;
        }

        return monthNumber;
    }

    private ArrayList<Invoice> pullData() {
        System.out.println("invoice: pullData");
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

    private String getDate (Timestamp timestamp) {
        System.out.println("invoice: getData");
        SimpleDateFormat dateFormat = new SimpleDateFormat(timestampFormat);
        Date date = new Date();
        date.setTime(timestamp.getTime());
        return dateFormat.format(date);
    }

    private Timestamp setDate (String date) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(timestampFormat);
        try {
            calendar.setTime(dateFormat.parse(date));
            return new Timestamp(calendar.getTimeInMillis());
        }
        catch (ParseException e) {
            return null;
        }
    }

    private void createView(ArrayList<Invoice> Invoices) {
        System.out.println("invoice: createView");
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

            totalPrice = totalPrice + Invoices.get(i).getTicketPrice() * Invoices.get(i).getTicketQuantity();
            totalTickets = totalTickets + Invoices.get(i).getTicketQuantity();

            Invoice.getChildren().add(EventName);
            Invoice.getChildren().add(EventDate);
            Invoice.getChildren().add(BookingDate);
            Invoice.getChildren().add(EventTicketQuantity);
            Invoice.getChildren().add(EventTicketPrice);
            Container.getChildren().add(Invoice);
        }
        SummaryE.setText("Total Events Booked: " + Integer.toString(Invoices.size()));
        SummaryT.setText("Total tickets Bought: " + Integer.toString(totalTickets));
        SummaryP.setText("Total Price: " + Double.toString(totalPrice));
        maincontainer.getChildren().add(Container);
    }

    private double pullTicketinfo(int TicketTypeID) {
        System.out.println("invoice: pullTicketInfo");
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


}