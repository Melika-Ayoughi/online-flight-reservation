package domain;

import java.util.ArrayList;

/**
 * Created by Ali_Iman on 5/2/17.
 */
public interface FlightRepository {
    void storeFlights(ArrayList<Flight> flights);
    ArrayList<Flight> searchFlights(String date, String srcCode, String destCode);
    void updateFlight(Flight flight);
}