package main.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import main.Entities.User;


import java.awt.*;
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
    private Loader loader = null;

    @FXML
    private JFXTextField firstName, lastName, organisationName, townField, postCodeField, userName, address1Field, address2Field,
                    emailField, phoneNumberField, webAddressField;

    @FXML
    private JFXPasswordField password;

    @FXML
    private ImageView btnBack;

    public AccountController () {
        loader = new Loader(RootController.getInstance().getContent());
    }


    @FXML
    private void commitChanges (ActionEvent event) {
        user.setFirstName(firstName.getText());
    }


    public void setAccount (User user, String path) {
        if (path != null) {
            this.user = user;
            btnBack.setVisible(true);
            this.path = path;
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
            textField.setText(text);
        }
    }

    private void setAccountTypeVisibility () {
        if (user.getAccountType().equals("PUBLIC")) {
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

    @FXML
    private void back (MouseEvent e) {
        loader.loadPage(String.valueOf(path));
    }

    public void initialize (URL url, ResourceBundle bundle) {
        user = RootController.getInstance().getUser();
        System.out.println("Account controller initialize. Printing user: ");
        System.out.println(user.toString());
        setAccount(user, null);
    }
}
