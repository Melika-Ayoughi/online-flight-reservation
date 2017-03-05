import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/3/17.
 */
public class FlightRepo {
    private static FlightRepo flightRepo;
    private ArrayList<Flight> flights;

    private FlightRepo() { }
    public static FlightRepo getFlightRepo() {
        if(flightRepo == null) {
            flightRepo = new FlightRepo();
            flightRepo.flights = new ArrayList<Flight>();
        }
        return flightRepo;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    private Flight get(Flight flight) {
        for(Flight flightEntry : flights)
            if(flightEntry.equals(flight))
                return flightEntry;
        return null;
    }

    private boolean alreadyExists(Flight flight) {
        for(Flight flightEntry : flights)
            if(flightEntry.equals(flight))
                return true;
        return false;
    }

    private Flight update(Flight flight) {
        Flight flightEntry = get(flight);
        flightEntry.setDepartureTime(flight.getDepartureTime());
        flightEntry.setArrivalTime(flight.getArrivalTime());
        flightEntry.setAirplaneModel(flight.getAirplaneModel());
        flightEntry.setMapSeatClassCapacities(flight.getMapSeatClassCapacities());
        return  flightEntry;
    }

    private Flight add(Flight flight) {
        flights.add(flight);
        return flight;
    }

    public Flight store(Flight flight) {
        if (alreadyExists(flight))
            return update(flight);
        return add(flight);
    }
}