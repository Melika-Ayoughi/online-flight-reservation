package domain;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ali_Iman on 5/2/17.
 */
public interface InformationProvider {
    ArrayList<Flight> getFlightsList(String originCode, String destinationCode, String date) throws IOException;
    PriceValueObject getPricesList(SeatClass seatClass) throws IOException;
}