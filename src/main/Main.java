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
    public void start(Stage primaryStage) throws Exception{
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


        System.out.println("Please Enter Concert Name: ");
        while (input.hasNextLine()) {
            name = input.nextLine();
            break;
        }

        System.out.println("Please Enter Concert Price: ");
        while (input.hasNextDouble()) {
            price = input.nextDouble();
            break;
        }



        System.out.println("Please Enter your Seat Number: ");
        while (input.hasNext()) {
            seat = input.next();
            break;
        }


        System.out.println("Please Enter Concert ID: ");
        while (input.hasNext()) {
            concertID =input.next();
            break;
        }

        System.out.println("Please Enter Price ID: ");
        while (input.hasNext()) {
            priceID = input.nextInt();
            break;
        }

        System.out.println("Did you got discount? ");
        while (input.hasNextDouble()) {
            rate = input.nextDouble();
            break;
        }

        if (!name.isEmpty() && price != -1 && !concertID.isEmpty() && rate != -1 && !seat.isEmpty() && priceID !=-1) {
            //System.out.println("Error");
        }
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
