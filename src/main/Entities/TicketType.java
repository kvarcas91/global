package main.Entities;
import main.Interfaces.Dao;
import java.util.HashMap;

public class TicketType implements Dao<TicketType> {

    private int ID = -1;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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
    public String getQuery() {
        return String.format("INSERT INTO TICKET_TYPES VALUES (null, '%s', %d, '%f', '%d')",
                getName(), getSlot(), getPrice(), isCorp());
    }

    @Override
    public void setObject(HashMap<String, String> object) {
        setID(Integer.parseInt(object.get("Type_ID ")));
        setName(object.get("Type_Name"));
        setSlot(Integer.parseInt(object.get("Type_Slots")));
        setPrice(Double.valueOf(object.get("Type_Price")));
        setCorp(Integer.valueOf(object.get("Type_Is_Corp")));
    }

    @Override
    public TicketType getObject() {
        return this;
    }

    @Override
    public String toString () {
        return String.format("ID: %d;\nName: %s;\nSlot: %s;\nPrice: %f;\nIs Corp: %b;",
                getID(), getName(), getSlot(), getPrice(), isCorp());
    }
}