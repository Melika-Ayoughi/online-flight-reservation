package services.resultService;

import domain.Flight;
import domain.SeatClass;

/**
 * Created by melikaayoughi on 5/12/17.
 */
public class resultRequest {
    private String srcCode = "";
    private String destCode = "";
    private String date = "";
    private String airlineCode="";
    private String flightNumber="";
    private Character seatClassName;

    public void setSrcCode(String srcCode) {
        this.srcCode = srcCode;
    }

    public void setDestCode(String destCode) {
        this.destCode = destCode;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setSeatClassName(Character seatClassName) {
        this.seatClassName = seatClassName;
    }

    public String getSrcCode() {
        return srcCode;
    }

    public String getDestCode() {
        return destCode;
    }

    public String getDate() {
        return date;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Character getSeatClassName() {
        return seatClassName;
    }
}
