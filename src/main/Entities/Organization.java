package main.Entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Organization extends User {

    private String webAddress = null;
    private String organisationName = null;

    public Organization () {}

    public Organization(Builder builder, String organisationName, String webAddress) {
        super(builder);
        this.organisationName = organisationName;
        this.webAddress = webAddress;
    }

    public Organization (Builder builder) {
        super(builder);
    }

    public String getWebAddress() {
        return webAddress;
    }

    public String getOrganisationName () {return this.organisationName;}

    @Override
    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    @Override
    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
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
    public Organization getUser(Connection connection, int id) {

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
                setOrganisationName(resultSet.getString("Org_Name"));
                setWebAddress(resultSet.getString("Org_Webadress"));
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

    private String buildQuery () {
        return String.format("INSERT INTO USERS VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                getUserID(), getAccountType(), getUserName(), getUserPassword(), getTown(), getPostCode(), getAddress1(),
                getEmail(), getPhoneNumber(), getFirstName(), getLastName(), getOrganisationName(), getWebAddress());
    }


    @Override
    public String toString () {
        return String.format(super.toString() + "webAddress: %s\n" +
                                                "Organization name: %s\n", getWebAddress(), getOrganisationName());
    }

}
