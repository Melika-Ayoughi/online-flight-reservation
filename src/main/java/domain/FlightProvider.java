package domain;

import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/1/17.
 */
public interface FlightProvider {
    ArrayList<Flight> getFlightsList(String originCode, String destinationCode, String date);
    PriceValueObject getPricesList(SeatClass seatClass);
    ReserveValueObject doReservation(Reservation reservation);
    FinalizeValueObject doFinalization(Reservation reservation);
}