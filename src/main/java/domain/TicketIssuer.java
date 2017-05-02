package domain;

import java.io.IOException;

/**
 * Created by Ali_Iman on 5/2/17.
 */
public interface TicketIssuer {
    ReserveValueObject doReservation(Reservation reservation) throws IOException;
    FinalizeValueObject doFinalization(Reservation reservation) throws IOException;
}
