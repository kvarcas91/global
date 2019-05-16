package main.Entities;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Invoice {

    private String eventName;
    private String bookingDate;
    private String eventDate;
    private int ticketQuantity;
    private int ticketTypeID;
    private double ticketPrice;
    private final String timestampFormat = "yyyy-MM-dd";

    public Invoice(String eventName,String bookingDate, String eventDate, int ticketQuantity, int ticketTypeID, double ticketPrice) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.bookingDate = bookingDate;
        this.ticketQuantity = ticketQuantity;
        this.ticketTypeID = ticketTypeID;
        this.ticketPrice = ticketPrice;

    }

    private String getDate(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(timestampFormat);
        Date date = new Date();
        date.setTime(timestamp.getTime());
        return dateFormat.format(date);
    }

    private Timestamp setDate(String date) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(timestampFormat);
        try {
            calendar.setTime(dateFormat.parse(date));
            return new Timestamp(calendar.getTimeInMillis());
        } catch (ParseException e) {
            return null;
        }
    }

    public int getTicketTypeID() {
        return ticketTypeID;
    }

    public String getEventName() {
        return eventName;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public Timestamp getBookingDateString() {
        return setDate(bookingDate);
    }

    public String getEventDate() {
        return eventDate;
    }

    public Timestamp getEventDateString() {
        return setDate(eventDate);
    }

    public int getTicketQuantity() {
        return ticketQuantity;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketTypeID(int ticketTypeID) {
        this.ticketTypeID = ticketTypeID;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setBookingDateString(Timestamp bookingDate) {
        this.bookingDate = getDate(bookingDate);
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventDateString(Timestamp eventDate) {
        this.eventDate = getDate(eventDate);
    }

    public void setTicketQuantity(int ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}







