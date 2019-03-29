package main.Entities;

public class Customer extends User {

    private String firstName = null;
    private String lastName = null;

    public Customer () {}

    public Customer (Builder builder, String firstName, String lastName) {
        super(builder);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer (Builder builder){
        super(builder);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString () {
        return String.format(super.toString() + "first name: %s\n" +
                                                "last name: %s\n", getFirstName(), getLastName());
    }
}