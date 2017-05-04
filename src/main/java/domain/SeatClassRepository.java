package domain;

/**
 * Created by Ali_Iman on 5/2/17.
 */
public interface SeatClassRepository {
    void storeSeatClass(SeatClass seatClass);
    SeatClass getSeatClass(String name, String originCode, String destinationCode, String airlineCode);
    void updateSeatClass(SeatClass seatClass);
}
