package domain;

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
    private String arrivalTime;
    private String airplaneModel;

    private ArrayList<MapSeatClassCapacity> mapSeatClassCapacities;

    public Flight(String airlineCode, String flightNumber, String date, String srcCode, String destCode, String departureTime, String arrrivalTime, String airplaneModel, ArrayList<MapSeatClassCapacity> mapSeatClassCapacities) {
        this.airlineCode = airlineCode;
        this.flightNumber = flightNumber;
        this.date = date;
        this.srcCode = srcCode;
        this.destCode = destCode;
        this.departureTime = departureTime;
        this.arrivalTime = arrrivalTime;
        this.airplaneModel = airplaneModel;
        this.mapSeatClassCapacities = mapSeatClassCapacities;
    }

    public ArrayList<MapSeatClassCapacity> getMapSeatClassCapacities() {
        return mapSeatClassCapacities;
    }
    public String getDate() {
        return date;
    }
    public String getDepartureTime() {
        return departureTime;
    }
    public String getArrivalTime() {
        return arrivalTime;
    }
    public String getAirplaneModel() {
        return airplaneModel;
    }
    public String getFlightNumber() {
        return flightNumber;
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

//    public boolean equals(Flight flight) {
//        if(this.airlineCode.equals(flight.airlineCode) &&
//                this.)
//        return false;
//    }
}
