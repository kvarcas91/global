package main.Entities;

public class Organization extends Customer {

    private String webAddress;

    public Organization(Builder builder, String title, String firstName, String lastName, String webAddress) {
        super(builder, title, firstName, lastName);
        this.webAddress = webAddress;
    }


    public String getWebAddress() {
        return webAddress;
    }

    @Override
    public String toString () {
        return String.format(super.toString() + "webAddress: %s\n", getWebAddress());
    }

}
