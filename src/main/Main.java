package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.Entities.Customer;
import main.Entities.Event;
import main.Entities.Organization;
import main.Entities.User;
import main.Networking.JDBC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class Main extends Application {

    private enum accountType {PUBLIC, GENERAL, ORGANISATION}



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("UI/Login/login_ui.fxml"));
        primaryStage.setTitle("Global music");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

        /*Event aevent;
        aevent = new Event("Event Name","6 6 666","Luton","Super Event","Leo");

        aevent.ShowEvent();

        aevent.AddBand("Mona Lisa","Da Wino Ci");
        aevent.AddBand("Bolek i lolek","Reksio");

        aevent.ShowBands();

        JDBC conec;
        String query = "Select * FROM ITEM WHERE wiek = 21";
        conec = new JDBC(query, 4);
*/


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
