import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/2/17.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        FlightProvider ca1HelperServer = new CA1HelperServer("188.166.78.119", 8081);
        ArrayList<Flight> flights = ca1HelperServer.getFlightsList("THR", "MHD", "05Feb");
    }
}
