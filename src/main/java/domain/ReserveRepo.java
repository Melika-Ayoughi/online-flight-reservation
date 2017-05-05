package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali_Iman on 3/3/17.
 */
public class ReserveRepo implements ReserveRepository {
    private static ReserveRepo reserveRepo;
    private ArrayList<Reservation> reservations;

    private ReserveRepo() { }
    public static ReserveRepo getReserveRepo() {
        if(reserveRepo == null) {
            reserveRepo = new ReserveRepo();
            reserveRepo.reservations = new ArrayList<Reservation>();
        }
        return reserveRepo;
    }


    public void storeReservation(Reservation reservation) {
        reservations.add(reservation);
        return;
    }
    public Reservation getReservationByToken(String token) {
        for (Reservation reservationEntry : reservations)
            if(reservationEntry.getToken().equals(token))
                return reservationEntry;
        return null;
    }
    public void updateReservation(Reservation reservation) {
        return;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }
}