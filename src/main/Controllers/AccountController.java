package main.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.Entities.User;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountController implements Initializable {

    /**
     *          ########################################################
     *          #############   How to create more pages   #############
     *          ########################################################
     *  Loader loader = new Loader(RootController.getInstance().getContent());
     *  loader.loadPage(fxml file);  // fxml file is a String of that file location. i.e.: ../UI/account.fxml
     *          ########################################################
     */

    private User user = null;
    private String path = null;

    @FXML
    private JFXTextField firstName, lastName, organisationName, townField, postCodeField, userName, address1Field, address2Field,
                    emailField, phoneNumberField, webAddressField;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton btnBack;



    @FXML
    private void commitChanges (ActionEvent event) {

    }


    public void setAccount (User user, String path) {
        if (path != null) {
            this.user = user;
            btnBack.setVisible(true);
            this.path = path;
        }

        setAccountTypeVisibility();

        userName.setText(user.getUserName());
        password.setText(user.getUserPassword());
        address1Field.setText(user.getAddress1());
        address2Field.setText(user.getAddress2());
        townField.setText(user.getTown());
        postCodeField.setText(user.getPostCode());
        emailField.setText(user.getEmail());
        phoneNumberField.setText(user.getPhoneNumber());

        if (user.getAccountType().equals("PUBLIC")) {
            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
        }
        else {
            organisationName.setText(user.getOrganisationName());
            webAddressField.setText(user.getWebAddress());
        }
    }

    private void setAccountTypeVisibility () {
        if (user.getAccountType().equals("PUBLIC")) {
            organisationName.setVisible(false);
            webAddressField.setVisible(false);
        }
        else {
            firstName.setVisible(false);
            lastName.setVisible(false);
        }
    }

    @FXML
    private void back (ActionEvent e) {
        Loader loader = new Loader(RootController.getInstance().getContent());
        loader.loadPage(String.valueOf(path));
    }

    public void initialize (URL url, ResourceBundle bundle) {
        user = RootController.getInstance().getUser();
        setAccount(user, null);
    }
}
