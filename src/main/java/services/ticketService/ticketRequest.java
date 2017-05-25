package services.ticketService;

import java.util.ArrayList;

import services.searchService.searchRequest;

/**
 * Created by melikaayoughi on 4/28/17.
 */
public class ticketRequest {

    private String token;

    private searchRequest searchRequest;

    private String seatClass;

    private String airlineCode;
    private String flightNumber;

    private ArrayList<PassengerVO> adultPassengerList;
    private ArrayList<PassengerVO> childPassengerList;
    private ArrayList<PassengerVO> infantPassengerList;

    public void setToken(String token) {
        this.token = token;
    }
    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public void setSearchRequest(services.searchService.searchRequest searchRequest) {
        this.searchRequest = searchRequest;
    }

    public void setAdultPassengerList(ArrayList<PassengerVO> adultPassengerList) {
        this.adultPassengerList = adultPassengerList;
    }

    public void setChildPassengerList(ArrayList<PassengerVO> childPassengerList) {
        this.childPassengerList = childPassengerList;
    }

    public void setInfantPassengerList(ArrayList<PassengerVO> infantPassengerList) {
        this.infantPassengerList = infantPassengerList;
    }


    public String getToken() {
        return token;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }


    public String getSeatClass() {
        return seatClass;
    }


    public services.searchService.searchRequest getSearchRequest() {
        return searchRequest;
    }

    public ArrayList<PassengerVO> getAdultPassengerList() {
        return adultPassengerList;
    }

    public ArrayList<PassengerVO> getChildPassengerList() {
        return childPassengerList;
    }

    public ArrayList<PassengerVO> getInfantPassengerList() {
        return infantPassengerList;
    }


}
