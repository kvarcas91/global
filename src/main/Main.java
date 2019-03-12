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
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
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
