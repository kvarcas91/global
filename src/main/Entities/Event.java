package main.Entities;

import java.util.ArrayList;

public class Event {
    private String eventName,eventDate,eventLocation,eventDescription,eventOrganiser;

    private ArrayList<Band>   bands        = new ArrayList<>();

    private ArrayList<Ticket>   tickets      = new ArrayList<>();

    public Event(String eName, String eDate, String eLocation, String eDescription, String eOrganiser)
    {
        eventName           = eName;
        eventDate           = eDate;
        eventLocation       = eLocation;
        eventDescription    = eDescription;
        eventOrganiser      = eOrganiser;
    }

    public String getEventName() {return eventName;}
    public String getEventDate() {return eventDate;}
    public String getEventLocation(){return eventLocation;}
    public String getEventDescription() {return eventDescription;}
    public String getEventOrganiser() {return eventOrganiser;}

    public class Band{
        private String bandName, bandAgent;

        public Band(String bName, String bAgent){
            bandName        = bName;
            bandAgent       = bAgent;
        }
        public String getBandName(){return bandName;}
        public String getBandAgent(){return bandAgent;}

    }
    public class Ticket{
        private String   ticketName;
        private Integer  ticketsNumber;
        private Float    ticketPrice;
        private Boolean  isTicketCorp;

        public Ticket(String tName, int tNumber, float tPrice, boolean tIsCorp){
            ticketName      = tName;
            ticketsNumber   = tNumber;
            ticketPrice     = tPrice;
            isTicketCorp    = tIsCorp;
        }

        public String getTicketName() {return ticketName;}
        public Integer getTicketsNumber() {return ticketsNumber;}
        public Float getTicketPrice() {return ticketPrice;}
        public Boolean getTicketCorp() {return isTicketCorp;}
    }

    public void AddBand(String bName, String bAgent){
        bands.add(new Band(bName,bAgent));
    }

    public void AddTicket(String tName, int tNumber, float tPrice, boolean tIsCorp){
        tickets.add(new Ticket(tName,tNumber,tPrice,tIsCorp));
    }
    public void ShowEvent(){
        System.out.println(getEventName());
        System.out.println(getEventDate());
        System.out.println(getEventLocation());
        System.out.println(getEventDescription());
    }

    public void ShowBands(){
        for (int i = 0; i < bands.size(); i++) {
            System.out.println(bands.get(i).getBandName());
            System.out.println(bands.get(i).getBandAgent());
        }
    }
    public void ShowTickets(){
        for (int i = 0; i < tickets.size(); i++) {
            System.out.println(tickets.get(i).getTicketName());
            System.out.println(tickets.get(i).getTicketsNumber());
            System.out.println(tickets.get(i).getTicketPrice());
        }
    }
}
