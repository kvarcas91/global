package main.Entities;


import main.Networking.JDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer extends User {

    private String firstName = null;
    private String lastName = null;

    public Customer () {}

    public Customer (Builder builder, String firstName, String lastName) {
        super(builder);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer (Builder builder){
        super(builder);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }



    @Override
    public boolean insertUser (Connection connection) {
        String query = buildQuery();
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Customer getUser(Connection connection, int id) {

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS WHERE USER_ID = " + id);
            if (resultSet.next()) {
                setUserID(id);
                setUserName(resultSet.getString("User_Name"));
                setAccountType(resultSet.getString("Account_Type"));
                setUserPassword(resultSet.getString("User_Password"));
                setTown(resultSet.getString("User_City"));
                setPostCode(resultSet.getString("User_Post_Code"));
                setAddress1(resultSet.getString("User_Street"));
                setAddress2("This field is fucking empty.. WHY???????");
                setEmail(resultSet.getString("User_Email"));
                setPhoneNumber(resultSet.getString("User_Phone_No"));
                setFirstName(resultSet.getString("User_Name"));
                setLastName(resultSet.getString("User_Last_Name"));
            }
            else {
                return null;
            }
            resultSet.close();
            statement.close();
            return this;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString () {
        System.out.println("-----------------------------");
        System.out.println("-------CUSTOMER OBJECT-------");
        System.out.println("-----------------------------");
        return String.format(super.toString() + "first name: %s\n" +
                                                "last name: %s\n", getFirstName(), getLastName());
    }



    private String buildQuery () {
        return String.format("INSERT INTO USERS VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                getUserID(), getAccountType(), getUserName(), getUserPassword(), getTown(), getPostCode(), getAddress1(),
                getEmail(), getPhoneNumber(), getFirstName(), getLastName(), getOrganisationName(), getWebAddress());
    }
}