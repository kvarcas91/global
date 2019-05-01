package main.Utils.Test;

import main.Entities.Entity;
import main.Networking.JDBC;
import java.util.ArrayList;

public class Test {

    public static void printTable (String table, String className) {
        System.out.println("-----------------------------------");
        System.out.println(String.format("ALL %s", table));
        String query = String.format("SELECT * FROM %s", table);
        ArrayList<Entity> obj = JDBC.getAll(query, className);
        for (Entity object : obj) {
            System.out.println(object.toString());
            System.out.println("***");
        }
        System.out.println("------------------------------------");
    }
}
