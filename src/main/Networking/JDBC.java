package main.Networking;

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
    private final String connURL = "jdbc:mysql://(host=remotemysql.com,port=3306)/QEvOAGN7uq?user=QEvOAGN7uq&password=RRDlZZcPcm";

    public JDBC () {
        try {
            System.out.println("Creating connection");
            DriverManager.setLoginTimeout(5);
            connection = DriverManager.getConnection(connURL);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public java.sql.Connection getConnection () {

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

    public boolean edit () {
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
