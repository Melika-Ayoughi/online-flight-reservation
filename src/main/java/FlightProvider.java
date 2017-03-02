import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/1/17.
 */
public interface FlightProvider {
    ArrayList<Flight> getFlightsList(String originCode, String destinationCode, String date);
    ArrayList<Integer> getPricesList(Flight flight);
    ReserveValueObject reserve(Flight flight);
    FinalizeValueObject finalize(Flight flight);
}
