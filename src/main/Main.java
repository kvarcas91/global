package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Entities.Customer;
import main.Entities.Organization;
import main.Entities.Price;
import main.Entities.User;

import java.util.Scanner;


public class Main extends Application {

    private enum accountType {PUBLIC, GENERAL, ORGANISATION}


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("UI/Login/login_ui.fxml"));
        primaryStage.setTitle("Global music");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        /*launch(args);
    }

    private static String getInput (String question) {
        Scanner input = new Scanner(System.in);
        String userInput = null;

        while (userInput == null) {
            System.out.print(question);
            userInput = input.next();
            userInput = (userInput.replace(" ", "").isEmpty() || userInput.length() < 4 ? null : userInput);
        }
        return userInput;
    }

    private static String getOptionalInput (String question) {
        Scanner input = new Scanner(System.in);
        System.out.println(question);
        String userInput = input.nextLine();

        userInput = (userInput.isEmpty() ? null : userInput);
        return userInput;

   */


        Scanner input = new Scanner(System.in);

        Price pricePlace = new Price();

        double price = pricePlace.getPrice();
        String concertID = pricePlace.getConcertID();
        String seat = pricePlace.getSeatRow();
        int priceID = pricePlace.getPriceID();
        double rate = pricePlace.getRate();
        String name = pricePlace.getName();

        while (name == null) {
            System.out.print("Please enter concert name:");
            name = (input.next());
            name = (name.replace(" ", "").isEmpty() || name.length() < 4 ? null : name);
            break;

        }
        do {
            System.out.print("Please enter concert price:");
            while (!input.hasNextDouble()) {
                double checker = input.nextDouble();
                System.out.printf("\"%s\" is not a valid number.\n", checker);
            }
            price = input.nextInt();
        } while (price <= 0);


        while (seat == null) {
            System.out.print("Please enter your seat place:");
            seat = (input.next());
            seat = (seat.replace(" ", "").isEmpty() || seat.length() < 4 ? null : seat);
            break;

        }

        while (concertID == null) {
            System.out.print("Please enter concert id:");
            concertID = (input.next());
            concertID = (concertID.replace(" ", "").isEmpty() || concertID.length() < 4 ? null : concertID);
            break;

        }

        do {
            System.out.print("Please enter price id:");
            while (!input.hasNextInt()) {
                int checker = input.nextInt();
                System.out.printf("\"%s\" is not a valid number.\n", checker);
            }
            priceID = input.nextInt();
        } while (priceID <= 0);

        do {
            System.out.print("Please enter amount of discount:");
            while (!input.hasNextDouble()) {
                double checker = input.nextDouble();
                System.out.printf("\"%s\" is not a valid number.\n", checker);
            }
            rate = input.nextDouble();
        } while (rate <= 0);

        pricePlace.finalPrice(name, rate, priceID, seat, concertID, price);


        System.out.println("Concert name is:  " + name);
        System.out.println("Concert price is:  " + price + " £ ");
        System.out.println("Your seat place is in : " + seat);
        System.out.println("Concert ID is: " + concertID);
        System.out.println("Price ID is: " + priceID);
        System.out.println("Discount is: " + rate + " %");
        System.out.println("Final price is : " + price / rate + " £ ");
        }
}

