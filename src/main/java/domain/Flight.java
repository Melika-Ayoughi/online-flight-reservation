package domain;

import java.util.ArrayList;
import java.util.Date;

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
    private Integer flightId;
    private Date lastUpdateDate;
    private ArrayList<MapSeatClassCapacity> mapSeatClassCapacities;

    public Flight(String airlineCode, String flightNumber, String date, String srcCode, String destCode, String departureTime, String arrivalTime, String airplaneModel, Date lastUpdateDate, ArrayList<MapSeatClassCapacity> mapSeatClassCapacities) {
        this.airlineCode = airlineCode;
        this.flightNumber = flightNumber;
        this.date = date;
        this.srcCode = srcCode;
        this.destCode = destCode;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airplaneModel = airplaneModel;
        this.lastUpdateDate = lastUpdateDate;
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
    public Integer getFlightId() {
        return flightId;
    }
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public void setAirplaneModel(String airplaneModel) {
        this.airplaneModel = airplaneModel;
    }
    public void setMapSeatClassCapacities(ArrayList<MapSeatClassCapacity> mapSeatClassCapacities) {
        this.mapSeatClassCapacities = mapSeatClassCapacities;
    }
    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public boolean equals(Flight flight) {
        if(this.airlineCode.equals(flight.airlineCode) &&
                this.flightNumber.equals(flight.flightNumber) && this.date.equals(flight.date) &&
                this.srcCode.equals(flight.srcCode) && this.destCode.equals(flight.destCode))
            return true;
        return false;
    }

    public Integer getSeatClassCapacity(Character seatClassName) {
        for (MapSeatClassCapacity mapSeatClassCapacity : mapSeatClassCapacities)
            if (mapSeatClassCapacity.getSeatClass().getName().equals(seatClassName))
                return mapSeatClassCapacity.getCapacity();
        return null;
    }
}