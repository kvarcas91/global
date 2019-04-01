package main.Controllers;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;


public class MyAccountController implements Initializable {


    @FXML
    private Label userType;

    @FXML
    private Button apply;

    @FXML
    private TextField userName;

    @FXML
    private TextField password;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField address1;

    @FXML
    private TextField address2;

    @FXML
    private TextField town;

    @FXML
    private TextField postCode;

    @FXML
    private TextField email;

    @FXML
    private TextField phoneNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUserType(LoginController.getInstance().userName());
    }

    public void setUserType(String user){
        this.userName.setText(user);
    }


/*
    public boolean updateMyAccount(user user) {
        String update "UPDATE MYACCOUNT SET USERNAME=?,PASSWORD=?,WHERE USERTYPE=? ";
        PreparedStatement statement = connection.prepareStatement(update);
        statement.setString(1, user.getUserName());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getFirstName());
        statement.setString(4, user.getLastName());
        statement.setString(5, user.getAddress1());
        statement.setString(6, user.getAddress2());
        statement.setString(7, user.getTown());
        statement.setString(8, user.getPostCode());
        statement.setString(9, user.getEmail());
        statement.setString(10, user.getPhoneNumber());
        statement.execute();
        int result = statement.executeUpdate();
        return(result>0);

    }catch (SQLException exception)

    {
        Logger.getLogger(DatabaseHandler.class.getName().log(Level.SEVERE, null, exception));
    }
    return false;

      private void updateProfile(){
        MyAccountController.Update user = new MyAccountController.Update(userName.getText(), password.getText(),
                firstName.getText(),lastName.getText(),address1.getText(),address2.getText(),
                town.getText(),postCode.getText(),email.getText(),phoneNumber.getText(), true);
                }
    */


    @FXML
    public void createApply (ActionEvent event){

        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(1));
        pt.setOnFinished(event1 ->
                System.out.println("User Name is: " + userName.getText()));
                System.out.println("User Password is: " + password.getText());
                System.out.println("User First Name is: " + firstName.getText());
                System.out.println("User Last Name is: " + lastName.getText());
                System.out.println("User Address 1 is: " + address1.getText());
                System.out.println("User Address 2 is: " + address2.getText());
                System.out.println("User Town is: " + town.getText());
                System.out.println("User Post Code is: " + postCode.getText());
                System.out.println("User Email Address is: " + email.getText());
                System.out.println("User Phone Number is: " + phoneNumber.getText());
                System.out.println("INFORMATION BEEN UPDATED");

        if(userName.getText().isEmpty()) {
            System.out.println("ERROR, PLEASE ENTER YOUR USER NAME");
            return;
        }
        if(password.getText().isEmpty()) {
            System.out.println("ERROR, PLEASE ENTER YOUR PASSWORD");
            return;
        }
        if(firstName.getText().isEmpty()) {
            System.out.println("ERROR, PLEASE ENTER YOUR FIRST NAME");
            return;
        }
        if(lastName.getText().isEmpty()) {
            System.out.println("ERROR, PLEASE ENTER YOUR LAST NAME");
            return;
        }
        if(address1.getText().isEmpty()) {
            System.out.println("ERROR, PLEASE ENTER YOUR ADDRESS1");
            return;
        }
        if(address2.getText().isEmpty()) {
            System.out.println("ERROR, PLEASE ENTER YOUR ADDRESS2");
            return;
        }
        if(town.getText().isEmpty()) {
            System.out.println("ERROR, PLEASE ENTER YOUR TOWN");
            return;
        }
        if(postCode.getText().isEmpty()) {
            System.out.println("ERROR, PLEASE ENTER YOUR POST CODE");
            return;
        }
        if(email.getText().isEmpty()) {
            System.out.println("ERROR, PLEASE ENTER YOUR EMAIL ADDRESS");
            return;
        }
        if(phoneNumber.getText().isEmpty()) {
            System.out.println("ERROR, PLEASE ENTER YOUR PHONE NUMBER");
            return;
        }
        pt.play();
    }
}



