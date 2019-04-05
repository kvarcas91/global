package main.Controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.Entities.Customer;
import main.Entities.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

    @FXML
    BorderPane root;

    @FXML
    private HBox errorPane;

    @FXML
    private Text errorMessage;

    @FXML
    private JFXTextField userTextField;

    @FXML
    private JFXPasswordField passwordField;

    private User user = null;
    private Loader loader;

    public LoginController() {
        loader = new Loader();
    }

    @FXML
    private void register (ActionEvent e) {
        loader.loadRegister(root);
    }

    @FXML
    private void login (ActionEvent e) {
        if (userTextField.getText().equals(user.getUserName()) && passwordField.getText().equals(user.getUserPassword())) {
            loader.loadMain(root, user);
        }
        else  {
            setError("Incorrect username or password");
            Task task = hideErrorPane();
            new Thread(task).start();
        }
    }

    public void setError (String message) {
        if (message != null) {
            errorPane.setVisible(true);
            errorMessage.setText(message);
        }
    }

    private Task hideErrorPane () {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                Thread.sleep(2000);
                errorPane.setVisible(false);
                return true;
            }
        };

    }

    private void rootUser () {
        User.Builder builder = new User.Builder(0, "ADMIN", "root", "root")
                .email("root@root.co.uk")
                .address1("Hogwarts")
                .address2("DumbleDoor Office")
                .town("not Luton")
                .postCode("GL HF")
                .phoneNumber("123456789");
        user = new Customer(builder, "Harry", "Potter");
    }


    @Override
    public void initialize (URL url, ResourceBundle bundle) {
        rootUser();
        RequiredFieldValidator validator = new RequiredFieldValidator();
        userTextField.getValidators().add(validator);
        passwordField.getValidators().add(validator);

        userTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    userTextField.validate();
                }
            }
        });


        passwordField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!newValue) passwordField.validate();
            }
        });

        try {
            Image errorIcon = new Image(new FileInputStream("src/main/Resources/Drawable/error.png"));
            validator.setIcon(new ImageView(errorIcon));
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
