package main.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import main.Entities.Entity;
import main.Entities.User;
import main.Networking.JDBC;
import main.Utils.Loader;
import main.Utils.WriteLog;
import main.View.NotificationPane;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AdminAccountController extends Controller implements Initializable{

    // TODO proper cloneable

    private static final Logger LOGGER = Logger.getLogger(AdminAccountController.class.getName());

    @FXML private HBox notificationPane;
    @FXML private Text errorMessage;
    @FXML private TilePane tilePane;

    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<User> clonedUsers = new ArrayList<>();
    private static AdminAccountController instance;

    private AdminAccountController () {
        instance = this;
        WriteLog.addHandler(LOGGER);
        LOGGER.log(Level.INFO, "Creating AdminAccountController instance from constructor at: {0}\n", LocalTime.now());
    }

    protected static AdminAccountController getInstance() {
        if (instance == null) {
            synchronized (AdminAccountController.class) {
                if (instance == null) {
                    return new AdminAccountController();
                }
            }
        }
        else LOGGER.log(Level.INFO, "Tried to create AdminAccountController instance at {0}\n", LocalTime.now());
        return instance;
    }

    protected static boolean hasInstance () {
        LOGGER.log(Level.INFO, "Checking AdminAccountController instance: {0}\n", (instance != null));
        return instance != null;
    }

    private void editItem (User user) {

        LOGGER.log(Level.INFO, "EditItem method with user: {0}\n", user);

        AccountController.Destroy();
        AccountController account = AccountController.getInstance();
        Loader.getInstance().loadPage("../UI/account.fxml", account);
        AccountController.getInstance().setAccount(user, "../UI/adminAccount.fxml", AdminAccountController.getInstance());
    }


    private void removeItem (User user) {
        LOGGER.log(Level.INFO, "RemoveItem method with user: {0}\n", user);
        int id = user.getUserID();
        if (JDBC.delete("USERS", "User_ID", id)) {
            NotificationPane.show("User has been removed", "green");
            clonedUsers.remove(user);
            setTile(clonedUsers);
        }
        else {
            System.out.println("Error");
        }
    }


    private void setTile (ArrayList<User> mUsers) {
        tilePane.getChildren().clear();

        LOGGER.log(Level.INFO, "Setting tileView at: {0}\n", LocalTime.now());

        if (mUsers.size() > 0) {
            for (User user : mUsers) {
                if (user != null) {

                    VBox tile = new VBox();
                    tile.setAlignment(Pos.TOP_CENTER);

                    VBox userInfo = new VBox();
                    userInfo.setSpacing(10);
                    userInfo.setAlignment(Pos.TOP_CENTER);

                    HBox controls = new HBox();
                    controls.setAlignment(Pos.CENTER);
                    controls.setSpacing(5);

                    Image delete = null;
                    Image edit = null;
                    ImageView accountType, btnEdit, btnDelete;
                    Separator separator = new Separator();
                    Text userNameText = new Text();


                    try {
                        delete = new Image(new FileInputStream("src/main/Resources/Drawable/delete.png"));

                        edit = new Image(new FileInputStream("src/main/Resources/Drawable/edit.png"));
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    accountType = new ImageView();

                    btnEdit = new ImageView(edit);
                    btnDelete = new ImageView(delete);

                    btnEdit.setFitHeight(20);
                    btnEdit.setFitWidth(20);

                    btnDelete.setFitHeight(20);
                    btnDelete.setFitWidth(20);

                    controls.getChildren().addAll(btnEdit, btnDelete);
                    userInfo.getChildren().addAll(accountType, userNameText);
                    tile.getChildren().addAll(userInfo, separator, controls);

                    tile.setMargin(accountType, new Insets(10, 10, 10, 10));
                    tile.setMargin(separator, new Insets(0, 10, 10, 0));

                    try {
                        if (user.getAccountType().equals("PUBLIC")) {
                            accountType.setImage(new Image(new FileInputStream("src/main/Resources/Drawable/customer.png")));
                        } else {
                            accountType.setImage(new Image(new FileInputStream("src/main/Resources/Drawable/organization.png")));
                        }
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    userNameText.setText(user.getUserName());

                    tilePane.getChildren().add(tile);

                    userInfo.setOnMousePressed(event -> print(user));
                    btnEdit.setOnMousePressed(event -> editItem(user));
                    btnDelete.setOnMousePressed(event -> removeItem(user));
                }
            }
        }
    }

    protected static void search (String s) {
        LOGGER.log(Level.INFO, "Searched value: {0} at: {1}", new Object[]{s, LocalTime.now()});
        clonedUsers.clear();
        for (User user : users) {
            if (user.contains(s))
                clonedUsers.add(user);
        }

        try {
            AdminAccountController.getInstance().setTile(clonedUsers);
        }
        catch (NullPointerException e) {
            LOGGER.log(Level.WARNING, "NullPointerException at: {0}; Message: {1}\n", new Object[]{LocalTime.now(), e.getMessage()});
        }

    }

    private void print(User user) {
        System.out.println(user.toString());
    }

    @Override
    public void initialize (URL location, ResourceBundle resourceBundle) {

        String query = "SELECT * FROM USERS";
        if (!users.isEmpty()) users.clear();

        User temp;
        ArrayList<Entity> objects = JDBC.getAll(query, User.class.getName());
        for (Entity obj : objects) {
            temp = (User) obj;
            users.add(temp);
        }

        clonedUsers.clear();
        clonedUsers.addAll(users);
        LOGGER.log(Level.INFO, "init method. Users size: {0}; clonedUsers size: {1}\n", new Object[]{users.size(), clonedUsers.size()});
        setTile(clonedUsers);
    }

    public static void Destroy () {
        if (instance != null) {
            LOGGER.log(Level.INFO, "Destroying AdminAccountController instance at: {0}\n", LocalTime.now());
            instance = null;
        }
    }
}
