package main.Entities;

public class Organization extends User {

    private String webAddress = null;
    private String organisationName = null;


    public Organization(Builder builder, String organisationName, String webAddress) {
        super(builder);
        this.organisationName = organisationName;
        this.webAddress = webAddress;
    }

    public Organization (Builder builder) {
        super(builder);
    }

    public String getWebAddress() {
        return webAddress;
    }

    public String getOrganisationName () {return this.organisationName;}

    @Override
    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    @Override
    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    @Override
    public String toString () {
        return String.format(super.toString() + "webAddress: %s\n" +
                                                "Organization name: %s\n", getWebAddress(), getOrganisationName());
    }

}
