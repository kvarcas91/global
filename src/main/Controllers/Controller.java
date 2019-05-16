package main.Controllers;

public abstract class Controller{

    public enum AccountTypes {
        PUBLIC, ADMIN, ROOT, ORGANIZATION, AGENT, ORGANISER, NONE
    }


   protected Controller.AccountTypes getType (String key) {
        switch (key) {
            case "PUBLIC":
                return AccountTypes.PUBLIC;
            case "ADMIN":
                return AccountTypes.ADMIN;
            case "ROOT":
                return AccountTypes.ROOT;
            case "ORGANISATION":
                return AccountTypes.ORGANIZATION;
            case "AGENT":
                return AccountTypes.AGENT;
            case "ORGANISER":
                return AccountTypes.ORGANISER;
            case "NONE":
                return AccountTypes.NONE;
                default: return null;
        }
   }
}
