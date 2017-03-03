/**
 * Created by Ali_Iman on 2/11/17.
 */
public class Passenger {
    private String firstname;
    private String surname;
    private String nationalId;

    public Passenger(String firstname, String surname, String nationalId) {
        this.firstname = firstname;
        this.surname = surname;
        this.nationalId = nationalId;
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

    @Override
    public String toString() {
        return firstname + " " + surname + " " + nationalId;
    }
}
