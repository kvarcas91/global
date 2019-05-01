package main.Networking;

import main.Entities.Entity;
import main.Utils.WriteLog;
import main.View.NotificationPane;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBC {

    private static final int CONNECTION_TIMEOUT = 5;
    private static final Logger LOGGER = Logger.getLogger(JDBC.class.getName());
    private static java.sql.Connection connection = null;
    private static JDBC instance = null;
    private static final String connURL = "jdbc:mysql://(host=remotemysql.com,port=3306)/QEvOAGN7uq?user=QEvOAGN7uq&password=RRDlZZcPcm";

    private JDBC () {
        instance = this;
        WriteLog.addHandler(LOGGER);
        LOGGER.log(Level.INFO, "Creating JDBC instance from constructor at: {0}. Current instance is null: {1}; should be: true\n",
                new Object[]{LocalTime.now(), (instance == null)});
    }

    public static JDBC getInstance() {
        if (instance == null) {
            synchronized (JDBC.class) {
                if (instance == null) {
                    return new JDBC();
                }
            }
        }
        return instance;
    }

    public static void createConnection() {
        if (connection == null) {
            new Thread(() -> {
                LOGGER.log(Level.INFO, "Creating JDBC connection at: {0}\n", LocalTime.now());
                DriverManager.setLoginTimeout(CONNECTION_TIMEOUT);
                try {
                    connection = DriverManager.getConnection(connURL);
                    LOGGER.log(Level.INFO, "Connected at: {0}\n", LocalTime.now());
                }
                catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "SQLException at: {0}; message: {1}\n", new Object[]{LocalTime.now(), e.getMessage()});
                }
            }).start();
        }
    }

    public static boolean isConnected() {
        LOGGER.log(Level.INFO, "Checking network connection at: {0}; result: {1}\n",
                new Object[]{LocalTime.now(), (connection != null)});
        if (connection == null) NotificationPane.show("No network connection");
        return connection != null;
    }

    private Thread checkNetworkConnection () {
        return new Thread(() -> {
            // check connection
        });
    }

    public static int getCount (String query) {
        LOGGER.log(Level.INFO, "Getting count on query: {0} at: {1}\n", new Object[]{query, LocalTime.now()});
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
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

    public static boolean delete (String table, String columnID, int id) {

        String query = String.format("DELETE FROM %s WHERE %s = '%d'", table, columnID, id);
        LOGGER.log(Level.INFO, "Deleting object. Query: {0}\n", query);
        if (isConnected()) {
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

    public static boolean update (String query) {
        LOGGER.log(Level.INFO, "Updating data at: {0}; query: {1}\n", new Object[]{LocalTime.now(), query});
        if (isConnected()) {
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


    public static Entity get (String query, String className) {
        LOGGER.log(Level.INFO, "Getting data from db at: {0}; query: {1}; calling class: {2}\n", new Object[]{
                LocalTime.now(), query, className});


        if (isConnected()) {

            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                ResultSetMetaData metaData = resultSet.getMetaData();

                if (resultSet.next()) {
                    return getData(className, resultSet, metaData);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "SQLException at: {0}; message: {1}\n", new Object[]{LocalTime.now(), e.getMessage()});
                return null;
            }
        }
        return null;
    }

    public static ArrayList<Entity> getAll (String query, String className) {
        LOGGER.log(Level.INFO, "Getting data from db at: {0}; query: {1}; calling class: {2}\n", new Object[]{
                LocalTime.now(), query, className});

        ArrayList<Entity> entities = new ArrayList<>();
        if (isConnected()) {

            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                ResultSetMetaData metaData = resultSet.getMetaData();


                while (resultSet.next()) {
                    entities.add(getData(className, resultSet, metaData));
                }
                resultSet.close();
                statement.close();

                return entities;
            }
            catch (SQLException e) {
                e.printStackTrace();
                return entities;
            }
        }
        return entities;
    }


    public static boolean insert (String query) {
        LOGGER.log(Level.INFO, "Inserting data at: {0}; query: {1}\n", new Object[]{LocalTime.now(), query});
        if (isConnected()) {
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

    private static Entity getData (String className, ResultSet resultSet, ResultSetMetaData metaData) {
        HashMap<String, String> object = new HashMap<>();
        Class<?> mClass;

        try {
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                object.put(metaData.getColumnName(i), resultSet.getString(metaData.getColumnName(i)));
            }

            try {
                mClass = Class.forName(className);

                Entity entity = (Entity) mClass.getConstructor().newInstance();
                entity.setObject(object);
                return entity;
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                    InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void Destroy () {
        LOGGER.log(Level.INFO, "Destroying JDBC instance at: {0}\n", LocalTime.now());
        if (instance != null) {
            instance = null;
        }

        try {
        LOGGER.log(Level.INFO, "Closing SQL Connection instance at: {0}\n", LocalTime.now());
            if (!connection.isClosed()) {
                connection.close();
            }
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException at: {0}; message: {1}\n", new Object[]{LocalTime.now(), e.getMessage()});
        }

    }

}
