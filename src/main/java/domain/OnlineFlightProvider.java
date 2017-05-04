package domain;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/1/17.
 */
public abstract class OnlineFlightProvider implements InformationProvider, TicketIssuer {
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

    public abstract ArrayList<Flight> getFlightsList(String originCode, String destinationCode, String date) throws IOException;
    public abstract PriceValueObject getPricesList(SeatClass seatClass) throws IOException;
    public abstract ReserveValueObject doReservation(Reservation reservation) throws IOException;
    public abstract FinalizeValueObject doFinalization(Reservation reservation) throws IOException;
}