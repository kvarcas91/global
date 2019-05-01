package main.Entities;


import main.Interfaces.Dao;
import java.util.HashMap;

public class User extends Entity<User>{


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
    private String firstName = null;
    private String lastName = null;
    private String organisationName = null;
    private String webAddress = null;

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
        private String firstName = null;
        private String lastName = null;
        private String organisationName = null;
        private String webAddress = null;

        public Builder (int userID, String accountType, String userName, String userPassword) {
            this.userID = userID;
            this.accountType = accountType;
            this.userName = userName;
            this.userPassword = userPassword;

        } public Builder (String accountType, String userName, String userPassword) {

            this.accountType = accountType;
            this.userName = userName;
            this.userPassword = userPassword;
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

        public Builder firstName (String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName (String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder organisationName (String organisationName) {
            this.organisationName = organisationName;
            return this;
        }

        public Builder webAddress (String webAddress) {
            this.webAddress = webAddress;
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
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.organisationName = builder.organisationName;
        this.webAddress = builder.webAddress;
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

    public String getFirstName() {return firstName;}

    public String getLastName () {return lastName;}

    public String getWebAddress () {return webAddress;}

    public String getOrganisationName () {return organisationName;}

    public void setUserID (int userID) {
        this.userID = userID;
    }

    public void setAccountType (String accountType) {
        this.accountType = accountType;
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    public void setOrganisationName (String organisationName) {
        this.organisationName = organisationName;
    }

    public void setLastName (String lastName) {
        this.lastName = lastName;
    }

    public void setWebAddress (String webAddress) {
        this.webAddress = webAddress;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public String getInsertQuery() {
        return String.format("INSERT INTO USERS VALUES (null, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                 getAccountType(), getUserName(), getUserPassword(), getTown(), getPostCode(), getAddress1(), getAddress2(),
                getEmail(), getPhoneNumber(), getFirstName(), getLastName(), getOrganisationName(), getWebAddress());
    }

    @Override
    public String getUpdateQuery() {
        return String.format("UPDATE USERS SET User_Name = '%s', User_Password = '%s', User_City = '%s', User_Post_Code = '%s', User_Street = '%s', " +
                "User_Street2 = '%s', User_Email = '%s', User_Phone_No = '%s', User_First_Name = '%s', User_Last_Name = '%s', Org_Name = '%s'," +
                "Org_WebAdress = '%s' WHERE User_ID = '%s'", getUserName(), getUserPassword(), getTown(), getPostCode(), getAddress1(),
                getAddress2(), getEmail(), getPhoneNumber(), getFirstName(), getLastName(), getOrganisationName(), getWebAddress(), getUserID());
    }

    @Override
    public void setObject(HashMap<String, String> object) {
        setUserID(Integer.parseInt(object.get("User_ID")));
        setAccountType(object.get("Account_Type"));
        setUserName(object.get("User_Name"));
        setUserPassword(object.get("User_Password"));
        setTown(object.get("User_City"));
        setPostCode(object.get("User_Post_Code"));
        setAddress1(object.get("User_Street"));
        setAddress2(object.get("User_Street2"));
        setEmail(object.get("User_Email"));
        setPhoneNumber(object.get("User_Phone_No"));
        setFirstName(object.get("User_First_Name"));
        setLastName(object.get("User_Last_Name"));
        setOrganisationName(object.get("Org_Name"));
        setWebAddress(object.get("Org_WebAdress"));
    }

    @Override
    public User getObject() {
        return this;
    }


    @Override
    public boolean contains (String s) {
        String searchValue = s.toLowerCase();
        return  ((getUserName() != null && getUserName().toLowerCase().contains(searchValue))||
                (getAccountType() != null && getAccountType().toLowerCase().contains(searchValue)) ||
                (getAddress1() != null && getAddress1().toLowerCase().contains(searchValue)) ||
                (getAddress2() != null && getAddress2().toLowerCase().contains(searchValue)) ||
                (getTown() != null && getTown().toLowerCase().contains(searchValue)) ||
                (getPostCode() != null && getPostCode().toLowerCase().contains(searchValue)) ||
                (getEmail() != null && getEmail().toLowerCase().contains(searchValue)) ||
                (getPhoneNumber() != null && getPhoneNumber().toLowerCase().contains(searchValue)) ||
                (getFirstName() != null && getFirstName().toLowerCase().contains(searchValue)) ||
                (getLastName() != null && getLastName().toLowerCase().contains(searchValue)) ||
                (getOrganisationName() != null && getOrganisationName().toLowerCase().contains(searchValue)) ||
                (getWebAddress() != null && getWebAddress().toLowerCase().contains(searchValue)));
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
                                    "number: %s\n" +
                                    "first name: %s\n" +
                                    "last name: %s\n" +
                                    "organisation name: %s\n" +
                                    "web address: %s",
                getUserID(), getAccountType(), getUserName(), getUserPassword(), getAddress1(), getAddress2(),
                getTown(), getPostCode(), getEmail(), getPhoneNumber(), getFirstName(), getLastName(), getOrganisationName(),
                getWebAddress());
    }
}
