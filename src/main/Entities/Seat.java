package main.Entities;
import java.util.Scanner;

public class Seat {

    private int seatnumber;
    private int seatrow;
    private String section;

    public Seat() {

    }

    public void seatBuilder() {
        seatnumber = checkSeat("input seat Number:", 150);
        seatrow = checkSeat("input row number", 15);

    }

    public int checkSeat(String text, int highest) {

        Scanner input = new Scanner(System.in);
        int seatN;
        do {
            System.out.print(text);
            while (!input.hasNextInt()) {
                String checker = input.next();
                System.out.printf("\"%s\" is not a valid number.\n", checker);
            }
            seatN = input.nextInt();
        } while (seatN <= 0 || seatN >= highest);


        return seatN;
    }

}