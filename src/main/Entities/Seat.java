package main.Entities;
import java.util.Scanner;

public class Seat {

    private int seatnumber;
    private int seatrow;
    private String section;

    public Seat() {

    }

    public void seatBuilderSeats() {
        seatnumber = checkSeat("input seat Number:", 150);
        seatrow = checkSeat("input row number", 15);
    }

    public void seatBuilderSections() {
        section = checkSection("Enter the section you are in");
    }

    public void seatBuilderSectionSeats() {
        seatnumber = checkSeat("input seat Number:", 150);
        seatrow = checkSeat("input row number", 15);
        section = checkSection("Enter the section you are in");
    }

    public void mainSeatBuilder() {
        Scanner input = new Scanner(System.in);
        int seatType;

        do {
            System.out.println("Choose the seating type");
            while (!input.hasNextInt()) {
                String checker = input.next();
                System.out.printf("\"%s\" is not a valid number.\n", checker);
            }
            seatType = input.nextInt();
        }while (seatType >4 || seatType <0);

            switch (seatType) {
                case 1:
                    seatBuilderSeats();
                case 2:
                    seatBuilderSections();
                case 3:
                    seatBuilderSectionSeats();
            }
    }





    private int checkSeat(String text, int highest) {

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

    private String checkSection(String text) {
        Scanner input = new Scanner(System.in);
        System.out.println(text);
        String name = input.nextLine();
        while(!name.matches("[a-zA-Z]+")){
            System.out.println(text);
            name = input.nextLine();
        }
        return name;
    }

    public int getSeatnumber() {
        return seatnumber;
    }

    public int getSeatrow() {
        return seatrow;
    }

    public String getSection() {
        return section;
    }

    public void setSeatnumber(int seatnumber) {
        this.seatnumber = seatnumber;
    }

    public void setSeatrow(int seatrow) {
        this.seatrow = seatrow;
    }

    public void setSection(String section) {
        this.section = section;
    }
}