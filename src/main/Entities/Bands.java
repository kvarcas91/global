package main.Entities;

import java.util.HashMap;

public class Bands extends Entity<Bands>{

    private int ID = -1;
    private String name = null;
    private String agent = null;

    public Bands () {}

    public Bands (int ID, String name, String agent) {
        this.ID = ID;
        this.name = name;
        this.agent = agent;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    @Override
    public String getQuery() {
        return String.format("INSERT INTO BANDS VALUES (%d, '%s', '%s')", getID(), getName(), getAgent());
    }

    @Override
    public void setObject(HashMap<String, String> object) {
        setID(Integer.parseInt(object.get("Band_ID")));
        setName(object.get("Band_Name"));
        setAgent(object.get("Band_Agent"));
    }

    @Override
    public Bands getObject() {
        return this;
    }

    @Override
    public String toString () {
        return String.format("id: %d\nband name: %s\nagent: %s", getID(), getName(), getAgent());
    }
}
