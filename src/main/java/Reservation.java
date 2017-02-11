import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali_Iman on 2/11/17.
 */
public class Reservation {
    private String srcCode;
    private String destCode;
    private String date;
    private String airlineCode;
    private String flightNumber;
    private String seatClassName;
    private String adultCount;
    private String childCount;
    private String infantCount;

    private List<Passenger> passengerList = new ArrayList<Passenger>();

    private String token;

    public Reservation() {

    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    /*
    This returns the whole reservation string without passenger information
     */
    public String toString() {
        return srcCode + " " + destCode + " " + date + " " + airlineCode + " "
                + flightNumber + " " + seatClassName+ " " + adultCount+ " "
                + childCount + " " + infantCount;
    }

    public String getToken() {
        return token;
    }

    public int getTotalPrice(String adultPrice, String childPrice, String infantPrice){
        return Integer.parseInt(adultPrice)*Integer.parseInt(adultCount)
                + Integer.parseInt(childPrice)*Integer.parseInt(childCount)
                + Integer.parseInt(infantPrice)*Integer.parseInt(infantCount);
    }

    public List<Passenger> getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(List<Passenger> passengerList) {
        this.passengerList = passengerList;
    }

    public Reservation(String srcCode, String destCode, String date, String airlineCode, String flightNumber, String seatClassName, String adultCount, String childCount, String infantCount) {
        this.srcCode = srcCode;
        this.destCode = destCode;
        this.date = date;
        this.airlineCode = airlineCode;
        this.flightNumber = flightNumber;
        this.seatClassName = seatClassName;
        this.adultCount = adultCount;
        this.childCount = childCount;
        this.infantCount = infantCount;
    }
}
