package main.Controllers;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import main.Entities.Customer;
import main.Entities.User;
import main.Interfaces.NotificationPane;
import main.Networking.JDBC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AdminAccountController implements Initializable {

    @FXML
    private JFXListView<User> listView;

    @FXML
    private HBox errorPane;

    @FXML
    private Text errorMessage;

    private JDBC database = null;
    private Connection connection = null;
    private ObservableList<User> observableList = FXCollections.observableArrayList();

    public AdminAccountController () {
        database = new JDBC();
        connection = database.getConnection();
    }


    private User getFakeData (int id) {
        User.Builder builder = new User.Builder(id, "PUBLIC", "test " + id, "test")
                .email("test@test.test")
                .address1("test1")
                .address2("test2");
        return new Customer(builder, "A girl has no name", "no one");
    }

    static class Cell extends ListCell<User> implements NotificationPane {

        private HBox hBox = new HBox();
        private Label userID = new Label();
        private Label userNameLabel = new Label();
        private Pane pane = new Pane();
        private JDBC database = null;
        private HBox notificationPane = null;
        private Text messageText = null;


        public Cell (JDBC database, HBox notificationPane, Text message) {
            super();
            this.database = database;
            this.notificationPane = notificationPane;
            this.messageText = message;
            Image imgDelete = null, imgEdit = null;

            try {
                imgDelete = new Image(new FileInputStream("src/main/Resources/Drawable/delete.png"));
                imgEdit = new Image(new FileInputStream("src/main/Resources/Drawable/edit.png"));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            ImageView img1 = new ImageView(imgEdit);
            ImageView img2 = new ImageView(imgDelete);

            hBox.getChildren().addAll(userID, userNameLabel, pane, img1, img2);
            hBox.setHgrow(pane, Priority.ALWAYS);
            img2.setOnMousePressed(e -> removeItem());
            //img2.setOnMousePressed( e -> getListView().getItems().remove(getItem()));
            img1.setOnMousePressed(e -> editItem());
        }

        private void editItem () {
            User user = getListView().getItems().get(getIndex());
            System.out.println(user.toString());
            BorderPane borderPane = RootController.getInstance().getContent();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../UI/account.fxml"));
            try {
                Pane pane = loader.load();
                AccountController controller = loader.getController();
                controller.setAccount(user, "../UI/adminAccount.fxml");
                borderPane.setCenter(pane);
            } catch (IOException e) {
                return;
            }
        }

        private void removeItem () {
            int id = Integer.parseInt(userID.getText());
            if (database.delete("USERS", "User_ID", id)) {
                getListView().getItems().remove(getItem());
                setNotificationPane("User has been removed", "green");
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
            this.messageText.setText(message);
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

        @Override
        protected void updateItem(User user, boolean empty) {
            super.updateItem(user, empty);
            setText(null);
            setGraphic(null);

            if (user != null && !empty) {
                userID.setText(String.valueOf(user.getUserID()));
                userNameLabel.setText(user.getUserName());
                setGraphic(hBox);
            }
        }
    }

    @Override
    public void initialize (URL location, ResourceBundle resourceBundle) {


        ArrayList<User> users = database.getAllUsers();

        observableList.addAll(users);


        listView.setItems(observableList);
        listView.setCellFactory( param -> new Cell(database, errorPane, errorMessage));
    }

}
