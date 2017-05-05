package domain;

import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/3/17.
 */
public class FlightRepo implements FlightRepository{
    private static FlightRepo flightRepo;
    private ArrayList<Flight> flightsList;

    private FlightRepo() { }
    public static FlightRepo getFlightRepo() {
        if(flightRepo == null) {
            flightRepo = new FlightRepo();
            flightRepo.flightsList = new ArrayList<Flight>();
        }
        return flightRepo;
    }


    public void storeFlights(ArrayList<Flight> flights) {
        for(Flight flight : flights) {
            flight.setFlightId(flightsList.size());
            flightsList.add(flight);
        }
        return;
    }
    public ArrayList<Flight> searchFlights(String date, String srcCode, String destCode) {
        ArrayList<Flight> flights = new ArrayList<Flight>();
        for(Flight flightEntry : flightsList)
            if(flightEntry.getDate().equals(date) &&
                    flightEntry.getSrcCode().equals(srcCode) && flightEntry.getDestCode().equals(destCode))
                flights.add(flightEntry);
        if(flights.size()==0)
            return null;
        return flights;
    }
    public void updateFlight(Flight flight) {
        for(Flight flightEntry : flightsList) {
            if (flightEntry.equals(flight)) {
                flightEntry.setArrivalTime(flight.getArrivalTime());
                flightEntry.setDepartureTime(flight.getDepartureTime());
                flightEntry.setAirplaneModel(flight.getAirplaneModel());
                flightEntry.setLastUpdateDate(flight.getLastUpdateDate());
                for (Integer i = 0; i < flightEntry.getMapSeatClassCapacities().size(); i++)
                    flightEntry.getMapSeatClassCapacities().get(i).setCapacity(flight.getMapSeatClassCapacities().get(i).getCapacity());
                return;
            }
        }
        return;
    }

    public ArrayList<Flight> getFlights() {
        return flightsList;
    }
}