package main.Entities;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Booking extends Entity<Booking>{

    private final String timestampFormat = "yyyy-MM-dd";
    private int bookingID;
    private int userID;
    private int eventID;
    private int ticketTypeID;
    private int quantity;
    private String bookingDate;
    private int notify = 0;
    private String message = "";
    private String viewName;
    private Timestamp viewDate;
    private String viewLocation;
    private String viewStatus;

   public Booking () {}

   public Booking (int bookingID, int userID, int eventID, int ticketTypeID, int quantity, String bookingDate, boolean notify) {
       this.bookingID = bookingID;
       this.userID = userID;
       this.eventID = eventID;
       this.ticketTypeID = ticketTypeID;
       this.quantity = quantity;
       this.bookingDate = bookingDate;
       this.notify = setConfirmedBool(notify);
   }

    public Booking (int userID, int eventID, int ticketTypeID, int quantity) {
        this.userID = userID;
        this.eventID = eventID;
        this.ticketTypeID = ticketTypeID;
        this.quantity = quantity;
    }

    public Booking (int userID, int eventID) {
        this.userID = userID;
        this.eventID = eventID;
    }

    public Timestamp getViewDateTS() {
        return viewDate;
    }

    public String getViewDate() {
        return getDate(viewDate);
    }

    public void setViewDate(String eventdate) {
        this.viewDate = setDate(eventdate);
    }

    public String getViewLocation() {
        return viewLocation;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewDate(Timestamp viewDate) {
        this.viewDate = viewDate;
    }

    public void setViewLocation(String viewLocation) {
        this.viewLocation = viewLocation;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    private String getDate (Timestamp timestamp) {
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

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getTicketTypeID() {
        return ticketTypeID;
    }

    public void setTicketTypeID(int ticketTypeID) {
        this.ticketTypeID = ticketTypeID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBookingID() {
        return bookingID;
    }

    public int getUserID() {
        return userID;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getNotify() {
        return notify;
    }

    public void setIsConfirmed(int isCOnfirmed) {
        this.notify = isCOnfirmed;
    }

    private int setConfirmedBool (boolean value) {
       return value ? 1 : 0;
    }

    @Override
    public String getInsertQuery() {
        return String.format("INSERT INTO BOOKING VALUES (null, '%d', '%d', '%d', '%d', now(), %d)",
                getUserID(), getEventID(), getTicketTypeID(), getQuantity(), getNotify());
    }

    @Override
    public String getUpdateQuery() {
        return String.format("UPDATE BOOKING SET Quantity = '%s', Book_Date = '%s', Notify = '%s' WHERE Booking_ID = '%s'",
                getQuantity(), getBookingDate(), getNotify(), getBookingID());
    }

    @Override
    public void setObject(HashMap<String, String> object) {
        setBookingID(Integer.parseInt(object.get("Booking_ID")));
        setUserID(Integer.parseInt(object.get("User_ID")));
        setEventID(Integer.parseInt(object.get("Event_ID")));
        setTicketTypeID(Integer.parseInt(object.get("Ticket_Type")));
        setQuantity(Integer.parseInt(object.get("Quantity")));
        setBookingDate(object.get("Book_Date"));
        setIsConfirmed(Integer.valueOf(object.get("Notify")));
    }

    @Override
    public Booking getObject () {
       return this;
    }

    @Override
    public String toString () {
        return String.format("bookingID: %d\n" +
                        "userID: %d\n" +
                        "eventID: %d\n" +
                        "ticket type ID: %d\n" +
                        "quantity: %d\n" +
                        "bookingDate: %s\n" +
                        "confirmed: %d\n",
                getBookingID(), getUserID(), getEventID(), getTicketTypeID(), getQuantity(), getBookingDate(), getNotify());
    }
}

