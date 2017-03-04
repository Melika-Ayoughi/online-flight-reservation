package domain;

import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/3/17.
 */
public class FlightRepo {
    private static FlightRepo flightRepo;
    private ArrayList<Flight> flights;

    private FlightRepo() { }
    public static FlightRepo getFlightRepo() {
        if(flightRepo == null)
            flightRepo = new FlightRepo();
        return flightRepo;
    }

    public void setFlights(ArrayList<Flight> flights) {
        this.flights = flights;
    }
    public ArrayList<Flight> getFlights() {
        return flights;
    }
}