package main.Controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.Entities.*;
import main.Main;
import main.Networking.JDBC;
import main.Utils.Loader;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RootController implements Initializable{


    private User user;
    private static RootController instance;
    private Loader loader = null;

    @FXML
    private BorderPane content;

    @FXML
    private Text userNameField;

    @FXML
    private HBox accountBox, topPane;

    @FXML
    private TextField searchField;


    public RootController () {
        Main.setPageLoader(this.content);
        loader = Main.getPageLoader();
        System.out.println("root constructor");
        instance = this;
    }

    public static RootController getInstance() {
        return instance;
    }


    public void setUser(User user) {
        if (user != null) {
            String query = String.format("SELECT * FROM USERS WHERE User_Name = '%s' AND User_Password = '%s'",
                    user.getUserName(), user.getUserPassword());
            JDBC database = Main.getDatabase();
            this.user = (User) database.get(query, User.class.getName());
            userNameField.setText(user.getUserName());
            MenuController.getInstance().setAccountType(user.getAccountType());
        }
    }

    public User getUser() {
        return this.user;
    }

    @FXML
    private void searchEvent (MouseEvent e) {
        String activeController = MenuController.getActiveController();
        if (activeController != null) {
            switch (activeController) {
                case "adminAcc":
                    System.out.println("adminAcc controller");
                    AdminAccountController.search(searchField.getText());
                    break;
                case "dashboard":

                    //break;
                case "bookings":

                    //break;
                case "festivals":

                    break;
            }
        }
    }


    public BorderPane getContent() {
        return this.content;
    }

    private void test (String table, String className) {
        System.out.println("-----------------------------------");
        System.out.println(String.format("ALL %s", table));
        JDBC database = Main.getDatabase();
        String query = String.format("SELECT * FROM %s", table);
        ArrayList<Object> obj = database.getAll(query, className);
        for (Object object : obj) {
            System.out.println(object.toString());
            System.out.println("***");
        }
        System.out.println("------------------------------------");

    }

    public void initialize (URL url, ResourceBundle bundle) {

        ContextMenu menu = new ContextMenu();
        MenuItem accountItem = new MenuItem("My Account");
        accountItem.setOnAction(e -> loader.loadPage("../UI/account.fxml"));
        menu.getItems().add(accountItem);

        accountBox.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown()) {
                    menu.show(topPane, event.getScreenX()-65, event.getScreenY()+20);
                }
            }
        });

        searchField.setOnAction(e -> searchEvent(null));
        searchField.textProperty().addListener((observable, oldVal, newVal) -> searchEvent(null));

        /**
         * FOR TESTING PURPOSE
         */
        Booking booking = new Booking(3, 4, 13, 2);
        //JDBC database = Main.getDatabase();
        //database.insert(booking.getQuery());

        /*
        test("USERS", User.class.getName());

        test("BANDS", Bands.class.getName());

        test("EVENTS", Event.class.getName());

        test("BOOKING", Booking.class.getName());

        test("TICKET_TYPES", TicketType.class.getName());


         */
    }
}