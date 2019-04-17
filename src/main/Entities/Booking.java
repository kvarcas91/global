package main.Entities;

import main.Interfaces.Dao;

import java.util.HashMap;

public class Booking extends Entity<Booking>{

    private int bookingID;
    private int userID;
    private int eventID;
    private int ticketTypeID;
    private int quantity;
    private String bookingDate;
    private int isCOnfirmed = 0;

   public Booking () {}

   public Booking (int bookingID, int userID, int eventID, int ticketTypeID, int quantity, String bookingDate, boolean isCOnfirmed) {
       this.bookingID = bookingID;
       this.userID = userID;
       this.eventID = eventID;
       this.ticketTypeID = ticketTypeID;
       this.quantity = quantity;
       this.bookingDate = bookingDate;
       this.isCOnfirmed = setConfirmedBool(isCOnfirmed);
   }

    public Booking (int userID, int eventID, int ticketTypeID, int quantity) {
        this.userID = userID;
        this.eventID = eventID;
        this.ticketTypeID = ticketTypeID;
        this.quantity = quantity;
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

    public int getIsCOnfirmed() {
        return isCOnfirmed;
    }

    public void setIsConfirmed(int isCOnfirmed) {
        this.isCOnfirmed = isCOnfirmed;
    }

    private int setConfirmedBool (boolean value) {
       return value ? 1 : 0;
    }

    @Override
    public String getQuery() {
        return String.format("INSERT INTO BOOKING VALUES (null, '%d', '%d', '%d', '%d', now(), %d)",
                getUserID(), getEventID(), getTicketTypeID(), getQuantity(), getIsCOnfirmed());
    }

    @Override
    public void setObject(HashMap<String, String> object) {
        setBookingID(Integer.parseInt(object.get("Booking_ID")));
        setUserID(Integer.parseInt(object.get("User_ID")));
        setEventID(Integer.parseInt(object.get("Event_ID")));
        setTicketTypeID(Integer.parseInt(object.get("Ticket_Type")));
        setQuantity(Integer.parseInt(object.get("Quantity")));
        setBookingDate(object.get("Book_Date"));
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
                getBookingID(), getUserID(), getEventID(), getTicketTypeID(), getQuantity(), getBookingDate(), getIsCOnfirmed());
    }
}

