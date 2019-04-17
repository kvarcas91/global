package main.Entities;

import java.util.HashMap;

public class Price extends Entity<Price> {
    private String concertName = null;
    private double concertPrice = -1;
    private String concertID = null;
    private String rowGroup = null;
    private int priceID = -1;
    private double corporatePriceRate = -1;

    public void setPrice(double price) {this.concertPrice = price;}
    public void setConcert(String concertID) { this.concertID = concertID; }
    public void setRow(String row) { this.rowGroup = row; }
    public void setPriceid(int priceID) {  this.priceID = priceID; }
    public void setRate(double rate) { this.corporatePriceRate = rate; }
    public void setConcertName(String name) {this.concertName = name; }

    public void finalPrice (String name, double rate, int priceID, String row, String concertID, double price) {
        this.concertName = name;
        this.concertPrice = price;
        this.corporatePriceRate = rate;
        this.rowGroup = row;
        this.priceID = priceID;
        this.concertID = concertID;
    }

    public double getPrice() { return this.concertPrice; }
    public String getConcertID() {  return this.concertID;  }
    public String getSeatRow() { return this.rowGroup; }
    public int getPriceID() {  return this.priceID;  }
    public double getRate() {return this.corporatePriceRate;  }
    public String getName() { return this.concertName; }






    // WHERE to put this?
    // HERE ->
    public String toString () {
        return String.format(super.toString() + "Concert Name: %s\n" +
                        "Concert Price: %s\n" +
                        "Seat: %s\n" +
                        "Discount: %s\n" +
                        "ConcertID: %s\n" +
                        "PriceID: %s\n", getName(), getPrice(), getSeatRow(),
                getRate(),getConcertID(),getPriceID());
    }

    @Override
    public String getQuery() {
        return String.format("SELECT * FROM BANDS ");
    }

    @Override
    public void setObject(HashMap<String, String> object) {

    }

    @Override
    public Price getObject() {
        return this;
    }
}

