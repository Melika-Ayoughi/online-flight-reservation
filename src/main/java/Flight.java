import java.util.ArrayList;

/**
 * Created by Ali_Iman on 2/10/17.
 */
public class Flight {
    private String airlineCode;
    private String flightNumber;
    private String date;
    private String srcCode;
    private String destCode;
    private String departureTime;
    private String arrrivalTime;
    private String airplaneModel;

    private ArrayList<SeatClass> seatClasses;

    public Flight(String airlineCode, String flightNumber, String date, String srcCode, String destCode, String departureTime, String arrrivalTime, String airplaneModel, ArrayList<SeatClass> seatClasses) {
        this.airlineCode = airlineCode;
        this.flightNumber = flightNumber;
        this.date = date;
        this.srcCode = srcCode;
        this.destCode = destCode;
        this.departureTime = departureTime;
        this.arrrivalTime = arrrivalTime;
        this.airplaneModel = airplaneModel;
        this.seatClasses = seatClasses;
    }

    public ArrayList<SeatClass> getSeatClasses() {
        return seatClasses;
    }

    public String getDestCode() {
        return destCode;
    }

    public String getSrcCode() {

        return srcCode;
    }

    public String getAirlineCode() {

        return airlineCode;
    }
}
