package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Entities.Customer;
import main.Entities.Organization;
import main.Entities.User;
import java.util.Scanner;


public class Main extends Application {

    private enum accountType {PUBLIC, GENERAL, ORGANISATION}



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("UI/Login/login_ui.fxml"));
        primaryStage.setTitle("Global music");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        //launch(args);
        accountType userAccountType;


        /**
         * @Create user without optional parameters (returns User):
         *      @User user = new User.Builder(userID, accountType, userName, userPassword).build();
         *
         *      OR
         *
         * @Create user with optional parameters (returns User):
         *      @User user = new User.Builder(userID, accountType, userName, userPassword)
         *                              .email(String)
         *                              .address1(String)
         *                              .address2(String)
         *                              .town(String)
         *                              .postCode(String)
         *                              .phoneNumber(String)
         *                              .build();
         *
         * @Create user.builder (returns User.Builder):
         *      @User.Builder builder = new User.Builder(userID, accountType, userName, userPassword);
         *      DO NOT call build() method as it will convert it to USER object
         *
         * @Create customer (returns Customer):
         *      REQUIREMENTS:
         *      @Customer (User.Builder(), title, firstName, lastName)
         *
         *      @Customer customer = new Customer (new User.Builder(userID, accountType, userName, userPassword), title, firstName, lastName);
         *
         *      OR
         *
         *      @CREATE User with builder
         *      @Customer customer = new Customer(new User.Builder(user), title, firstName, lastName);
         *
         *      OR
         *
         *      @CREATE user builder
         *      @Customer customer = new Customer(builder, title, firstName, lastName);
         *
         * Create organiser (returns organiser):
         *      REQUIREMENTS:
         *      @Organiser (User.Builder(), title, firstName, lastName, webAddress)
         *
         *      @Organiser organiser = new Organization (new User.Builder(params), title, firstName, lastName, webAddress);
         *
         *      OR
         *
         *      @CREATE User with builder
         *      @Organiser organiser = new Organization(new User.Builder(user), title, firstName, lastName, webAddress);
         *
         *      OR
         *
         *      @CREATE user builder
         *      @Organiser organiser = new Organization(builder, title, firstName, lastName, webAddress);
         *
         */



        String accType, webAddress = null;

        checkAccountType: {
            while (true) {
                accType = getInput("Account type (public, general, organisation): ");

                for (Enum e : accountType.values()) {
                    if (accType.toUpperCase().equals(String.valueOf(e))) {
                        userAccountType = accountType.valueOf(accType.toUpperCase());
                        break checkAccountType;
                    }
                }
            }
        }

        String userName = getInput("Enter your userName: ");
        String password = getInput("Enter your password: ");


        String title = getOptionalInput("Enter your title: ");
        String firstName = getInput("your first name: ");
        String lastName = getInput("your last name: ");

        System.out.println("\nNext questions will be optional\n");
        String address1 = getOptionalInput("Address line 1: ");
        String address2 = getOptionalInput("Address line 2: ");
        String postCode = getOptionalInput("post code: ");
        String town = getOptionalInput("town: ");
        String email = getOptionalInput("email: ");
        String phoneNumber = getOptionalInput("phone number: ");


        User.Builder builder = new User.Builder(0, String.valueOf(userAccountType), userName, password);

        builder.address1(address1)
                .address2(address2)
                .town(town)
                .postCode(postCode)
                .email(email)
                .phoneNumber(phoneNumber);

        if (userAccountType == accountType.ORGANISATION) {
            webAddress = getInput("Your web address: ");
            Organization user = new Organization(builder, title, firstName, lastName, webAddress);
            System.out.println(user.toString());
        }
        else {
            Customer user = new Customer(builder, title, firstName, lastName);
            System.out.println(user.toString());
        }



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
    }

}
