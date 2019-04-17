package main.Interfaces;

import java.util.HashMap;

public interface Dao <T> {

    String getQuery ();

    void setObject(HashMap<String, String> object);

    T getObject();

}