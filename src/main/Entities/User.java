package main.Entities;


public class User {

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
