package domain;

/**
 * Created by Ali_Iman on 2/11/17.
 */
public class Passenger {
    private String firstname;
    private String surname;
    private String nationalId;
    private String gender;

    public Passenger(String firstname, String surname, String nationalId, String gender) {
        this.firstname = firstname;
        this.surname = surname;
        this.nationalId = nationalId;
        this.gender = gender;
    }

    public String getFirstname() {
        return firstname;
    }
    public String getSurname() {
        return surname;
    }
    public String getNationalId() {
        return nationalId;
    }
    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return firstname + " " + surname + " " + nationalId;
    }
}
