package domain;

import java.util.ArrayList;

/**
 * Created by Ali_Iman on 5/2/17.
 */
public interface FlightRepository {
    void storeFlight(Flight flight);
    ArrayList<Flight> searchFlights(String date, String srcCode, String destCode);
    void updateFlight(Flight flight);
//    Flight getFlight(String airlineCode, String flightNumber, String date, String srcCode, String destCode);
}