package main.Entities;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

public class Booking {

    //Mandatory
    private int bookingID;
    private int userID;
    private int concertID;
    private double bookingPrice;


    public Booking() {
    }

    public void createBooking() {
        Scanner input = new Scanner(System.in);




        bookingID = validateIDInput("Please enter bookingID");
        userID = validateIDInput("Please enter userID");
        concertID = validateIDInput("Please enter concertID");
        bookingPrice = validatePriceInput("Please enter the concert price");

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
        return String.format("bookingID: %d\n" +
                        "userID: %s\n" +
                        "concertID: %s\n" +
                        "bookingPrice: %s\n",
                bookingID, userID, concertID, bookingPrice);
    }


    public int getBookingID() {
        return bookingID;
    }

    public int getUserID() {
        return userID;
    }

    public int getConcertID() {
        return concertID;
    }

    public double getBookingPrice() {
        return bookingPrice;
    }



    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setConcertID(int concertID) {
        this.concertID = concertID;
    }

    public void setBookingPrice(double bookingPrice) {
        this.bookingPrice = bookingPrice;
    }


}
