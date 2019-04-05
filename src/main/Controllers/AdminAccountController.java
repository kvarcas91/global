package main.Controllers;

import com.jfoenix.controls.JFXButton;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AdminAccountController implements Initializable {

    @FXML
    private JFXListView<User> listView;

    private ObservableList<User> observableList = FXCollections.observableArrayList();


    private User getFakeData (int id) {
        User.Builder builder = new User.Builder(id, "PUBLIC", "test " + id, "test")
                .email("test@test.test")
                .address1("test1")
                .address2("test2");
        return new Customer(builder, "A girl has no name", "no one");
    }

    static class Cell extends ListCell<User> {
        Image imgDelete = null;
        Image imgEdit = null;
        HBox hBox = new HBox();
        Label userID = new Label();
        Label userNameLabel = new Label();
        Pane pane = new Pane();

        public Cell () {
            super();

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
            getListView().getItems().remove(getItem());
        }

        @Override
        protected void updateItem(User user, boolean empty) {
            super.updateItem(user, empty);
            if (user != null && !empty) {
                userID.setText(String.valueOf(user.getUserID()));
                userNameLabel.setText(user.getUserName());
                setGraphic(hBox);
            }
        }
    }

    @Override
    public void initialize (URL location, ResourceBundle resourceBundle) {
        observableList.add(RootController.getInstance().getUser());
        for (int i = 0; i < 20; i++) {
            observableList.add(getFakeData(i));
        }
        listView.setItems(observableList);
        listView.setCellFactory( param -> new Cell());
    }

}
