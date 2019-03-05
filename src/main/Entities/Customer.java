package main.Entities;

public class Customer extends User {

    private String title = null;
    private String firstName = null;
    private String lastName = null;

    public Customer (Builder builder, String title, String firstName, String lastName) {
        super(builder);
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer (Builder builder){
        super(builder);
    }

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString () {
        return String.format(super.toString() + "title: %s\n" +
                                                "first name: %s\n" +
                                                "last name: %s\n", getTitle(), getFirstName(), getLastName());
    }
}