package main.Entities;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;


public class Booking {

    //Mandatory
    private String bookingID;
    private String userID;
    private String concertID;
    private String bookingPrice;
    private String bookingDate;

    public Booking(String b) {
        StringTokenizer st = new StringTokenizer(b,"|");

        this.setBookingID(st.nextToken());
        this.setUserID(st.nextToken());
        this.setConcertID(st.nextToken());
        this.setBookingPrice(st.nextToken());
        this.setBookingDate(st.nextToken());

    }

    /*    public Booking() {
            this.setBookingID(validateIDInput("Please enter booking ID"));
            this.setUserID(validateIDInput("Please enter user ID"));
            this.setConcertID(validateIDInput("Please enter concert ID"));
            this.setBookingPrice(validatePriceInput("Please enter booking Price"));

        }
    */
    public void viewPreviousBookings() {



    }

    private int validateIDInput (String text) {
        Scanner input = new Scanner(System.in);
        int number;
        do {
            System.out.print(text);
            while (!input.hasNextInt()) {
                String checker = input.next();
                System.out.printf("\"%s\" is not a valid number.\n", checker);
            }
            number = input.nextInt();
        } while (number <= 0);
        return number;

    }

    private double validatePriceInput (String text) {
        Scanner input = new Scanner(System.in);
        double number;
        do {
            System.out.print(text);
            while (!input.hasNextDouble()) {
                String checker = input.next();
                System.out.printf("\"%s\" is not a valid price.\n", checker);
            }
            number = input.nextDouble();
        } while (number <= 0);
        return number;

    }

    public String toString () {
        return String.format("bookingID: %s\n" +
                        "userID: %s\n" +
                        "concertID: %s\n" +
                        "bookingPrice: %s\n" +
                        "bookingDate: %s\n",
                this.bookingID, this.userID, this.concertID, this.bookingPrice, this.bookingDate);
    }


    public String getBookingID() {
        return bookingID;
    }

    public String getUserID() {
        return userID;
    }

    public String getConcertID() {
        return concertID;
    }

    public String getBookingPrice() {
        return bookingPrice;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setConcertID(String concertID) {
        this.concertID = concertID;
    }

    public void setBookingPrice(String bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }
}

