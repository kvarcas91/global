package main.Controllers;


import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.Entities.User;
import java.net.URL;
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
            this.user = user;
            userNameField.setText(user.getUserName());
            System.out.println(this.user.toString());
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
            box.setStyle("-fx-background-color: black;");

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
    }
}