package main.Entities;

public class Price {
    private String concertName;
    private double concertPrice;
    private String concertID;
    private String rowGroup;
    private int priceID;
    private double corporatePriceRate;

    public void setPrice(double price) {

        this.concertPrice = price;
    }

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
}
