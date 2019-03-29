package main.Entities;


public class User {

    /**
     * @Create user without optional parameters (returns User):
     *      @User user = new User.Builder(userID, accountType, userName, userPassword).build();
     *
     *      OR
     *
     * @Create user with optional parameters (returns User):
     *      @User user = new User.Builder(userID, accountType, userName, userPassword)
     *                              .email(String)
     *                              .address1(String)
     *                              .address2(String)
     *                              .town(String)
     *                              .postCode(String)
     *                              .phoneNumber(String)
     *                              .build();
     *
     * @Create user.builder (returns User.Builder):
     *      @User.Builder builder = new User.Builder(userID, accountType, userName, userPassword);
     *      DO NOT call build() method as it will convert it to USER object
     *
     * @Create customer (returns Customer):
     *      REQUIREMENTS:
     *      @Customer (User.Builder(), title, firstName, lastName)
     *
     *      @Customer customer = new Customer (new User.Builder(userID, accountType, userName, userPassword), title, firstName, lastName);
     *
     *      OR
     *
     *      @CREATE User with builder
     *      @Customer customer = new Customer(new User.Builder(user), title, firstName, lastName);
     *
     *      OR
     *
     *      @CREATE user builder
     *      @Customer customer = new Customer(builder, title, firstName, lastName);
     *
     * Create organiser (returns organiser):
     *      REQUIREMENTS:
     *      @Organiser (User.Builder(), title, firstName, lastName, webAddress)
     *
     *      @Organiser organiser = new Organization (new User.Builder(params), title, firstName, lastName, webAddress);
     *
     *      OR
     *
     *      @CREATE User with builder
     *      @Organiser organiser = new Organization(new User.Builder(user), title, firstName, lastName, webAddress);
     *
     *      OR
     *
     *      @CREATE user builder
     *      @Organiser organiser = new Organization(builder, title, firstName, lastName, webAddress);
     *
     */


    // Mandatory
    private int userID = -1;
    private String accountType = null;
    private String userName = null;
    private String userPassword = null;

    // Optional
    private String address1 = null;
    private String address2 = null;
    private String town = null;
    private String postCode = null;
    private String email = null;
    private String phoneNumber = null;

    public static class Builder {

        private int userID = -1;
        private String accountType = null;
        private String userName = null;
        private String userPassword = null;
        private String address1 = null;
        private String address2 = null;
        private String town = null;
        private String postCode = null;
        private String email = null;
        private String phoneNumber = null;

        public Builder (int userID, String accountType, String userName, String userPassword) {
            this.userID = userID;
            this.accountType = accountType;
            this.userName = userName;
            this.userPassword = userPassword;
        }

        public Builder (User user) {
            this.userID = user.getUserID();
            this.accountType = user.getAccountType();
            this.userName = user.getUserName();
            this.userPassword = user.getUserPassword();
            this.address1 = user.getAddress1();
            this.address2 = user.getAddress2();
            this.town = user.getTown();
            this.postCode = user.getPostCode();
            this.email = user.getEmail();
            this.phoneNumber = user.getPhoneNumber();
        }

        public Builder address1 (String address1) {
            this.address1 = address1;
            return this;
        }

        public Builder address2 (String address2) {
            this.address2 = address2;
            return this;
        }

        public Builder town (String town) {
            this.town = town;
            return this;
        }

        public Builder postCode (String postCode) {
            this.postCode = postCode;
            return this;
        }

        public Builder email (String email) {
            this.email = email;
            return this;
        }

        public Builder phoneNumber (String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public User build (){
            return new User (this);
        }
    }


    public User () {}

    public User (Builder builder) {
        this.userID = builder.userID;
        this.accountType = builder.accountType;
        this.userName = builder.userName;
        this.userPassword = builder.userPassword;
        this.address1 = builder.address1;
        this.address2 = builder.address2;
        this.town = builder.town;
        this.postCode = builder.postCode;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;

    }

    public int getUserID() {
        return userID;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getTown() {
        return town;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString () {
        return String.format("id: %d\n" +
                                    "type: %s\n" +
                                    "username: %s\n" +
                                    "password: %s\n" +
                                    "address1: %s\n" +
                                    "address2: %s\n" +
                                    "town: %s\n" +
                                    "post code: %s\n" +
                                    "email: %s\n" +
                                    "number: %s\n",
                userID, accountType, userName, userPassword, address1, address2, town, postCode, email, phoneNumber);
    }
}
