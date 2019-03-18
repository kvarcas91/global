package main.Networking;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

    private static String fromDB;
    private String connURL = "jdbc:mysql://(host=remotemysql.com,port=3306)/QEvOAGN7uq?user=QEvOAGN7uq&password=RRDlZZcPcm";
    //String with adress and login to db
    public JDBC(String query,int numberOfCol) {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connURL);
            //set connecton

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            //send request

            while (rs.next()) {
                printFromDB(rs,numberOfCol);
            }
            conn.close();
            //disconnect from db
        }
        catch(SQLException exc) {
            System.err.println(exc.getMessage());
        }
    }
    private static void printFromDB(ResultSet rs,int numberOfColumn){
        System.out.println(""); //Just to separate rows in console
        for (int i = 1; i <= numberOfColumn; i++)
        try{
            fromDB = rs.getString(i);
            System.out.println(fromDB);
        }catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
