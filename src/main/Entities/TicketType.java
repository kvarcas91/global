package main.Entities;

import java.util.HashMap;

public class TicketType extends Entity<TicketType>{

    private int ID ;
    private int eventID;
    private String name;
    private int slot;
    private double price;
    private int isCorp = 0;

    public TicketType () { }

    public TicketType (int ID, String name, int slot, double price, boolean isCorp) {
        this.ID = ID;
        this.name = name;
        this.slot = slot;
        this.price = price;
        this.isCorp = setCorpBool(isCorp);
    }

    public TicketType (String name, int slot, double price, boolean isCorp) {
        this.name = name;
        this.slot = slot;
        this.price = price;
        this.isCorp = setCorpBool(isCorp);
    }

    public TicketType (String name, int slot, double price, boolean isCorp, int eventID) {
        this.name = name;
        this.slot = slot;
        this.price = price;
        this.isCorp = setCorpBool(isCorp);
        this.eventID = eventID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int isCorp() {
        return this.isCorp;
    }

    public void setCorp(int corp) {
        this.isCorp = corp;
    }

    private int setCorpBool (boolean value) {
        return value ? 1 : 0;
    }

    @Override
    public String getInsertQuery() {
        return String.format("INSERT INTO TICKET_TYPES VALUES (null, '%s', %d, '%f', '%d', '%d')",
                getName(), getSlot(), getPrice(), isCorp(), getEventID());
    }

    @Override
    public String getUpdateQuery() {
        return String.format("UPDATE TICKET_TYPES SET Type_Name = '%s', Type_Slots = '%s', Type_Price = '%s', Type_Is_Corp = '%s', " +
                        "Event_ID = '%s' WHERE Type_ID = '%s'",
                getName(), getSlot(), getPrice(), isCorp(), getEventID(), getID());
    }

    @Override
    public void setObject(HashMap<String, String> object) {
        setID(Integer.parseInt(object.get("Type_ID")));
        setName(object.get("Type_Name"));
        setSlot(Integer.parseInt(object.get("Type_Slots")));
        setPrice(Double.valueOf(object.get("Type_Price")));
        setCorp(Integer.valueOf(object.get("Type_Is_Corp")));
        setEventID(Integer.valueOf(object.get("Event_ID")));
    }

    @Override
    public TicketType getObject() {
        return this;
    }

    @Override
    public String toString () {
        return String.format("ID: %d;\nName: %s;\nSlot: %s;\nPrice: %f;\nIs Corp: %b;\n event id: %d",
                getID(), getName(), getSlot(), getPrice(), isCorp(), getEventID());
    }
}
