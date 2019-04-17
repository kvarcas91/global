package main.Entities;

import main.Interfaces.Dao;

public abstract class Entity<T> implements Dao<T> {

    public boolean contains(String s) {return false;}

}
