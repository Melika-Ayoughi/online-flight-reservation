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
    private String referenceCode;
    private List<String> ticketNumbersList = new ArrayList<String>();

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public void setTicketNumbersList(List<String> ticketNumbersList) {
        this.ticketNumbersList = ticketNumbersList;
    }

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

    public ArrayList<String> getTickets(){
        ArrayList<String> tickets = new ArrayList<String>();
        for(int i=0; i<ticketNumbersList.size(); i++){
            tickets.add(passengerList.get(i).getFirstname() + " " + passengerList.get(i).getSurname() + " "
            + referenceCode + " " + ticketNumbersList.get(i) + " " + srcCode + " "
            + destCode + " " + airlineCode + " " + flightNumber + " " + seatClassName + " " );
        }
        return tickets;
    }

    public String getToken() {
        return token;
    }

    public String getDate() {
        return date;
    }

    public String getSrcCode() {

        return srcCode;
    }

    public String getDestCode() {
        return destCode;
    }

    public String getFlightNumber() {
        return flightNumber;
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
