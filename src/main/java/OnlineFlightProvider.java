import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/1/17.
 */
public abstract class OnlineFlightProvider implements FlightProvider {
    protected Socket helperServerSocket;
    protected BufferedReader inHelperServer;
    protected PrintWriter outHelperServer;

    private void connectToHelperServer(String HelperIP, int HelperPort) throws IOException {
        helperServerSocket = new Socket(HelperIP, HelperPort);
        inHelperServer = new BufferedReader(new InputStreamReader(helperServerSocket.getInputStream()));
        outHelperServer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(helperServerSocket.getOutputStream())), true);
    }

    public OnlineFlightProvider(String helperServerIP, Integer helperServerPort) throws IOException {
        connectToHelperServer(helperServerIP, helperServerPort);
    }

    public abstract ArrayList<Flight> getFlightsList(String originCode, String destinationCode, String date);
    public abstract ArrayList<Integer> getPricesList(Flight flight);
    public abstract ReserveValueObject reserve(Flight flight);
    public abstract FinalizeValueObject finalize(Flight flight);
}
