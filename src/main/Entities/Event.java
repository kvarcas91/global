package main.Entities;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Event extends Entity<Event> {

    private final String timestampFormat = "yyyy-MM-dd";
    private int eventID;
    private String eventName;
    private Timestamp eventDate;
    private String eventLocation;
    private String eventDescription;
    private int eventOrganiser;

    public Event () {}

    public Event(int eventID, String eName, String eDate, String eLocation, String eDescription, int eOrganiser) {
        this.eventID = eventID;
        eventName = eName;
        eventDate = setDate(eDate);
        eventLocation = eLocation;
        eventDescription = eDescription;
        eventOrganiser = eOrganiser;
    }

    public Event(String eName, String eDate, String eLocation, String eDescription, int eOrganiser) {
        eventName = eName;
        eventDate = setDate(eDate);
        eventLocation = eLocation;
        eventDescription = eDescription;
        eventOrganiser = eOrganiser;
    }

    public int getEventID() {
        return eventID;
    }
    public String getEventName() {return eventName;}
    public String getEventDate() {
        return getDate(this.eventDate);
    }
    public String getEventLocation(){return eventLocation;}
    public String getEventDescription() {return eventDescription;}
    public int getEventOrganiser() {return eventOrganiser;}

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventDate(String eventDate) {
       this.eventDate = setDate(eventDate);
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public void setEventOrganiser(int eventOrganiser) {
        this.eventOrganiser = eventOrganiser;
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

    public Timestamp getTimeStamp () {
        return this.eventDate;
    }

    @Override
    public String getInsertQuery() {
        return String.format("INSERT INTO EVENTS VALUES (null, '%s', '%s', '%s', '%s', '%d')",
                getEventName(), getEventDate(), getEventLocation(), getEventDescription(), getEventOrganiser());
    }

    @Override
    public String getUpdateQuery() {
        return String.format("UPDATE EVENTS SET Event_Name = '%s', Event_Date = '%s', Event_Location = '%s', Event_Description = '%s' WHERE EVENT_ID = '%s'",
                getEventName(), getEventDate(), getEventLocation(), getEventDescription(), getEventID());
    }

    @Override
    public void setObject(HashMap<String, String> object) {
        setEventID(Integer.parseInt(object.get("Event_ID")));
        setEventName(object.get("Event_Name"));
        setEventDate(object.get("Event_Date"));
        setEventLocation(object.get("Event_Location"));
        setEventDescription(object.get("Event_Description"));
        setEventOrganiser(Integer.parseInt(object.get("Event_Organiser")));
    }

    @Override
    public Event getObject() {
        return this;
    }

    @Override
    public boolean contains (String s) {
        String searchValue = s.toLowerCase();
        return ((getEventName() != null && getEventName().toLowerCase().contains(searchValue)) ||
                (getEventDescription() != null && getEventDescription().toLowerCase().contains(searchValue)) ||
                (getEventLocation() != null && getEventLocation().toLowerCase().contains(searchValue)));
    }


    @Override
    public String toString () {
        return String.format("ID: %d\nEvent name: %s\nEvent date: %s\nEvent location: %s\nEvent description: %s\nEvent organiser: %d",
                getEventID(), getEventName(), getEventDate(), getEventLocation(), getEventDescription(), getEventOrganiser());
    }
}

