package main.Networking;

import main.Entities.User;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class JDBC {

    private java.sql.Connection connection;


    public JDBC () {

    }

    public java.sql.Connection getConnection () {
        String connURL = "jdbc:mysql://(host=remotemysql.com,port=3306)/QEvOAGN7uq?user=QEvOAGN7uq&password=RRDlZZcPcm";
        if (connection == null) {

            try {
                System.out.println("Creating connection");
                DriverManager.setLoginTimeout(5);
                connection = DriverManager.getConnection(connURL);
            }
            catch (SQLException e) {
                return null;
            }
        }
        return connection;
    }

    public Thread checkNetworkConnection () {
        return new Thread() {
            @Override
            public void run () {
                // check internet connection
            }
        };
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

    public boolean edit (String querry, String className) {
        /**
         *          ########################################################
         *          #############   DOCUMENTATION   #############
         *          ########################################################
         * I do not know If I should put instead of querry, only User user?
         * I think it cannot be User user because of updating everything
         * I create different String query's which will be executed if any update
         * I am not sure if that's the correct way of writing code, so please do not judge me, mercy, help
         * P.S I included things which I think some of cannot be changed! So please pay attention of each.
         * Also, check if in sql table names of columns matches.
         * And It needs to check also if the information is been changed or no?
         *          ########################################################
         */
        connection = getConnection();
        String user = String.format("UPDATE USER SET userName=?, password=?, firstName=?, lastName=?," +
                "address1=?, address2=?, town=?, postCode=?, email=?, phoneNumber=?, organisationName=?," +
                "webAddress=?", querry, className);

        String bands = String.format("UPDATE BANDS SET bandID=?, bandName=?, bandAgent=?", querry, className);

        String bookings = String.format("UPDATE BOOKINGS SET bookingID=?, userID=?, eventID=?, ticketType=?" +
                "quantity=?, bookDate=?", querry, className);

        String events = String.format("UPDATE EVENTS SET eventName=?, eventID=?, eventLocation=?, eventDescription=?," +
                "eventOrganiser=?", querry, className);

        String price = String.format("UPDATE PRICE SET concertName=?, concertPrice=?, seatRow=?, rate=?, concertID=?," +
                "priceID=?", querry, className);

        String seat = String.format("UPDATE SEAT SET seatNumber=?, seatRow=?, seatSection=?", querry, className);

        String ticket = String.format("UPDATE TICKET SET typeID=?, typeName=?, typeSlots=?, typePrice=?," +
                "typeIsCorp=?", querry, className);

        if(connection!= null){
            try {
                Statement statement = connection.createStatement();
                statement.execute(user);
                statement.execute(bands);
                statement.execute(bookings);
                statement.execute(events);
                statement.execute(price);
                statement.execute(seat);
                statement.execute(ticket);
                statement.close();
                return true;
            }
            catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public Object get (String query, String className) {
        connection = getConnection();
        Class<?> mClass;
        Object newObject, mInstance;
        Method objectMethod;

        if (connection != null) {
            HashMap<String, String> object = new HashMap<>();

            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                if (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        object.put(metaData.getColumnName(i), resultSet.getString(metaData.getColumnName(i)));
                    }

                    try {
                        mClass = Class.forName(className);
                        mInstance = mClass.getConstructor().newInstance();

                        objectMethod = mClass.getMethod("setObject", HashMap.class);
                        objectMethod.invoke(mInstance, object);
                        objectMethod = mClass.getMethod("getObject");

                        newObject =  objectMethod.invoke(mInstance);
                        return newObject;
                    }
                    catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                            InstantiationException | InvocationTargetException e) {
                        e.printStackTrace();
                        return  null;
                    }
                }
                else {
                    System.out.println("No results for given query");
                    return null;
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public ArrayList<Object> getAll (String query, String className) {
        connection = getConnection();

        Class<?> mClass;
        Method objectMethod;
        Object newObject, mInstance;

        if (connection != null) {
            HashMap<String, String> object = new HashMap<>();
            ArrayList<Object> objects = new ArrayList<>();

            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        object.put(metaData.getColumnName(i), resultSet.getString(metaData.getColumnName(i)));
                    }

                    try {
                        mClass = Class.forName(className);
                        mInstance = mClass.getConstructor().newInstance();

                        objectMethod = mClass.getMethod("setObject", HashMap.class);
                        objectMethod.invoke(mInstance, object);
                        objectMethod = mClass.getMethod("getObject");

                        newObject =  objectMethod.invoke(mInstance);
                        objects.add(newObject);
                    }
                    catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                            InvocationTargetException | InstantiationException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
                resultSet.close();
                statement.close();

                return objects;
            }
            catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public boolean insert (String query) {
        connection = getConnection();

        if (connection != null) {
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
        return false;
    }

}
