package services.ticketService;

/**
 * Created by melikaayoughi on 4/28/17.
 */
public class PassengerVO {
    private String firstName;
    private String lastName;
    private String gender;
    private String nationalID;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getNationalID() {
        return nationalID;
    }
}
