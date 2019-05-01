package main.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.Entities.User;
import main.Networking.JDBC;
import main.Utils.Loader;
import main.Utils.WriteLog;
import main.View.NotificationPane;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountController extends Controller implements Initializable {


    private static final Logger LOGGER = Logger.getLogger(AccountController.class.getName());
    private User user = null;
    private String path = null;
    private static AccountController instance = null;
    private Controller parentController = null;

    @FXML private JFXTextField firstName, lastName, organisationName, townField, postCodeField, userName, address1Field,
            address2Field, emailField, phoneNumberField, webAddressField;

    @FXML private JFXPasswordField password;
    @FXML private JFXButton btnSave;
    @FXML private ImageView btnBack;
    @FXML private HBox notificationPane;
    @FXML private Text notificationMessage;

    private AccountController () {
        instance = this;
        WriteLog.addHandler(LOGGER);
        LOGGER.log(Level.INFO, "Creating AccountController instance from constructor at: {0}\n", LocalTime.now());
    }

    public static AccountController getInstance() {
        if (instance == null) {
            synchronized (AccountController.class) {
                if (instance == null) {
                    return new AccountController();
                }
            }
        }
        else LOGGER.log(Level.WARNING, "Tried to create additional instance at: {0}. Skipping and adding just pane\n",
                LocalTime.now());
        return instance;
    }

    private void commitChanges () {
        user.setFirstName(firstName.getText());
        user.setLastName(lastName.getText());
        JDBC.update(user.getUpdateQuery());
    }


    protected void setAccount (User user, String path, Controller parentController) {
        if (path != null && parentController != null) {
            this.user = user;
            btnBack.setVisible(true);
            this.path = path;
            this.parentController = parentController;
            AdminAccountController.Destroy();
        }

        setAccountTypeVisibility();

        setText(userName, user.getUserName());
        setText(password, user.getUserPassword());
        setText(address1Field, user.getAddress1());
        setText(address2Field, user.getAddress2());
        setText(townField, user.getTown());
        setText(postCodeField, user.getPostCode());
        setText(emailField, user.getEmail());
        setText(phoneNumberField, user.getPhoneNumber());

        if (user.getAccountType().equals("PUBLIC")) {
            setText(firstName, user.getFirstName());
            setText(lastName, user.getLastName());
        }
        else {
            setText(organisationName, user.getOrganisationName());
            setText(webAddressField, user.getWebAddress());
        }
    }

    private void setText(TextField textField, String text) {
        if (text != null){
            if (!text.equals("null"))
                textField.setText(text);
        }
    }

    private void setAccountTypeVisibility () {
        if (user.getAccountType().equals(AccountTypes.PUBLIC.toString())) {
            organisationName.setVisible(false);
            webAddressField.setVisible(false);
            firstName.setVisible(true);
            lastName.setVisible(true);
        }
        else {
            organisationName.setVisible(true);
            webAddressField.setVisible(true);
            firstName.setVisible(false);
            lastName.setVisible(false);
        }
    }

    private void setActionListeners () {
        btnBack.setOnMousePressed(e -> Loader.getInstance().loadPage(path, parentController));
        btnSave.setOnAction(e -> commitChanges());
    }

    public void initialize (URL url, ResourceBundle bundle) {
        user = RootController.getInstance().getUser();

        System.out.println("AdminController has instance: " + AdminAccountController.hasInstance());

        setAccount(user, null, null);

        setActionListeners();
    }

    public static void Destroy () {
        if (instance != null) {
            LOGGER.log(Level.INFO, "Destroying AccountController instance at: {0}\n", LocalTime.now());
            instance = null;
        }
    }
}
