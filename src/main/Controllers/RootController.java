package main.Controllers;


import com.mysql.cj.util.TestUtils;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Entities.*;
import main.Main;
import main.Networking.JDBC;

import java.net.URL;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RootController implements Initializable {


    private String accountType = null;
    private User user;
    private static RootController instance;
    private Loader loader = null;

    @FXML
    private BorderPane content;

    @FXML
    private Text userNameField;

    @FXML
    HBox accountBox;

    @FXML
    private HBox topPane;

    public RootController () {
        setLoader();
        System.out.println("root constructor");
        instance = this;
    }

    public static RootController getInstance() {
        return instance;
    }

    public void setLoader () {
        loader = new Loader(this.getContent());
    }

    public void setUser(User user) {
        System.out.println("setting user");
        if (user != null) {
            String query = String.format("SELECT * FROM USERS WHERE User_Name = '%s' AND User_Password = '%s'",
                    user.getUserName(), user.getUserPassword());
            JDBC jdbc = LoginController.getConnection();
            this.user = (User) jdbc.get(query, User.class.getName());
            //this.user = user;
            userNameField.setText(user.getUserName());
            MenuController.getInstance().setAccountType(user.getAccountType());
        }
    }

    public User getUser() {
        return this.user;
    }

    public void setErrorPane (boolean visible, String message) {
        if (visible) {
            HBox box = new HBox();
            box.setPadding(new Insets(5, 5, 5, 5));
            box.setAlignment(Pos.CENTER);
            box.setStyle("-fx-background-color: red;");

            Text errorMessage = new Text(message);
            errorMessage.setStyle("-fx-fill: white; -fx-font-weight: bolder;");

            box.getChildren().add(errorMessage);
            content.setBottom(box);

        }
        else {
            content.setBottom(null);
        }

    }



    public BorderPane getContent() {
        return this.content;
    }

    private void test (String table, String className) {
        System.out.println("-----------------------------------");
        System.out.println(String.format("ALL %s", table));
        JDBC database = LoginController.getConnection();
        String query = String.format("SELECT * FROM %s", table);
        ArrayList<Object> obj = database.getAll(query, className);
        for (Object object : obj) {
            System.out.println(object.toString());
        }
        System.out.println("------------------------------------");

    }

    public void initialize (URL url, ResourceBundle bundle) {
        System.out.println("root INIT");
        setLoader();
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

        Booking booking = new Booking();
        //TicketType type = new TicketType("A", 4, 45, false);
        //JDBC database = LoginController.getConnection();
        //database.insert(type.getQuery());


        test("USERS", User.class.getName());

        test("BANDS", Bands.class.getName());

        test("EVENTS", Event.class.getName());

        test("BOOKING", Booking.class.getName());

        test("TICKET_TYPES", TicketType.class.getName());
        
    }
}