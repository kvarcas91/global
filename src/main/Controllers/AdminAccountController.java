package main.Controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import main.Entities.User;
import main.Interfaces.NotificationPane;
import main.Main;
import main.Networking.JDBC;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AdminAccountController implements Initializable, NotificationPane {
    @FXML
    private HBox notificationPane;

    @FXML
    private Text errorMessage;

    @FXML
    private TilePane tilePane;

    private JDBC database;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<User> clonedUsers = new ArrayList<>();
    private static AdminAccountController instance;

    public AdminAccountController () {
        database = Main.getDatabase();
        instance = this;
    }

    public static AdminAccountController getInstance() {
        return instance;
    }

    private void editItem (User user) {

        BorderPane borderPane = RootController.getInstance().getContent();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../UI/account.fxml"));
        try {
            Pane pane = loader.load();
            AccountController controller = loader.getController();
            controller.setAccount(user, "../UI/adminAccount.fxml");
            borderPane.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void removeItem (User user) {
        int id = user.getUserID();
        if (database.delete("USERS", "User_ID", id)) {
            setNotificationPane("User has been removed", "green");
            clonedUsers.remove(user);
            setTile(clonedUsers);
        }
        else {
            System.out.println("Error");
        }
    }

    @Override
    public void setNotificationPane(String message, String color) {
        String style = String.format("-fx-background-color: %s;", color);
        if (color != null) {
            this.notificationPane.setStyle(style);
        }
        this.notificationPane.setVisible(true);
        this.errorMessage.setText(message);
        Task task = hideNotificationPane();
        new Thread(task).start();
    }

    @Override
    public Task hideNotificationPane() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                Thread.sleep(2000);
                notificationPane.setVisible(false);
                return true;
            }
        };
    }


    private void setTile (ArrayList<User> mUsers) {
        tilePane.getChildren().clear();

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

    public static void search (String s) {
        System.out.println(s);
        AdminAccountController.getInstance().clonedUsers.clear();
        for (User user : AdminAccountController.getInstance().users) {
            if (user.contains(s))
                AdminAccountController.getInstance().clonedUsers.add(user);
        }

        try {
            AdminAccountController.getInstance().setTile(AdminAccountController.getInstance().clonedUsers);
        }
        catch (NullPointerException e) {
        }

    }

    private void print(User user) {
        System.out.println(user.toString());
    }

    @Override
    public void initialize (URL location, ResourceBundle resourceBundle) {

        String query = "SELECT * FROM USERS";
        ArrayList<Object> objects = database.getAll(query, User.class.getName());
        for (Object obj : objects) {
            users.add((User) obj);
        }
        clonedUsers.addAll(users);
        setTile(clonedUsers);
    }


}
