package domain;

import java.util.ArrayList;

/**
 * Created by Ali_Iman on 5/2/17.
 */
public interface ReserveRepository {
    void storeReservation(Reservation reservation);
    Reservation getReservationByToken(String token);
    void updateReservation(Reservation reservation);

    ArrayList<TicketBean> getAllTicketBeans();
    ArrayList<TicketBean> getUserTicketBeans(String userName);
    TicketBean getTicketBean(String ticketId);
    TicketBean getUserTicketBean(String userName, String ticketId);
}