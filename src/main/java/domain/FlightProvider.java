package domain;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/1/17.
 */
public interface FlightProvider {
    ArrayList<Flight> getFlightsList(String originCode, String destinationCode, String date) throws IOException;
    PriceValueObject getPricesList(SeatClass seatClass) throws IOException;
    ReserveValueObject doReservation(Reservation reservation) throws IOException;
    FinalizeValueObject doFinalization(Reservation reservation) throws IOException;
}