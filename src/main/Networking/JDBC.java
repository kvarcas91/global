package main.Networking;

import main.Entities.Customer;
import main.Entities.Organization;
import main.Entities.User;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBC {

    private java.sql.Connection connection;

    private String connURL = "jdbc:mysql://(host=remotemysql.com,port=3306)/QEvOAGN7uq?user=QEvOAGN7uq&password=RRDlZZcPcm";

    public JDBC () {

    }

    public java.sql.Connection getConnection () {
        if (connection == null) {
            try {
                System.out.println("Creating connection");
                connection = DriverManager.getConnection(connURL);
            }
            catch (SQLException e) {
                return null;
            }
        }
        return connection;
    }

    public int getCount (String quesry) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(quesry);
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                resultSet.close();
                statement.close();
                return id;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean delete (String table, String columnID, int id) {
        connection = getConnection();
        String query = String.format("DELETE FROM %s WHERE %s = '%d'", table, columnID, id);
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                statement.execute(query);
                statement.close();
                return true;
            }
            catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public ArrayList<User> getAllUsers () {
        System.out.println("GetAllUsers");
        connection = getConnection();
        ArrayList<User> users = new ArrayList<>();
        User user = null;
        String query = "SELECT * FROM USERS";
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    System.out.println("Looping through resultSet");
                    if (resultSet.getString("Account_Type").equals("PUBLIC")) {
                        user = new Customer();
                        System.out.println("Added Customer");
                    }
                    else {
                        user = new Organization();
                    }
                    user = user.getUser(connection, resultSet.getInt("User_ID"));
                    users.add(user);
                    System.out.println("Added Organisation");
                }
                resultSet.close();
                statement.close();
                return users;
            }
            catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    

    public User verifyLogin (String userName, String password) {
        System.out.println("Verify login");
        String query = String.format("SELECT User_ID, Account_Type FROM USERS WHERE User_Name = '%s' AND User_Password = '%s'",
                userName, password);
        connection = getConnection();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    int id = resultSet.getInt("User_ID");
                    String accType = resultSet.getString("Account_Type");
                    User user = null;
                    if (accType.equals("PUBLIC")) {
                        user = new Customer();
                        user = user.getUser(connection, id);
                    }
                    else {
                        user = new Organization();
                        user = user.getUser(connection, id);
                    }
                    resultSet.close();
                    statement.close();
                    return user;
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        else {
            return null;
        }
        return null;
    }
}
