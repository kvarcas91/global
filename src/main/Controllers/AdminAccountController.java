package main.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import main.Entities.Customer;
import main.Entities.User;

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
        return new Customer(builder, "Eduardas", "Slutas");
    }

    static class Cell extends ListCell<User> {
        String btnStyle =   "-fx-text-fill: black;\n" +
                            "-fx-border-radius: 10px;\n" +
                            "-fx-background-radius: 10px;\n" +
                            "-fx-border-size: 1px;\n" +
                            "-fx-border-color: red;";
        HBox hBox = new HBox();
        Label userID = new Label();
        Label userNameLabel = new Label();
        JFXButton edit = new JFXButton("Edit");
        JFXButton delete = new JFXButton("Delete");
        Pane pane = new Pane();

        public Cell () {
            super();
            delete.setStyle(btnStyle);
            hBox.getChildren().addAll(userID, userNameLabel, pane, edit, delete);
            hBox.setHgrow(pane, Priority.ALWAYS);

            delete.setOnAction(e -> removeItem());
            edit.setOnAction(e -> editItem());
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
