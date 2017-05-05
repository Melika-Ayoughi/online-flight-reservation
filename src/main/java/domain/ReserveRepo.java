package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali_Iman on 3/3/17.
 */
public class ReserveRepo implements ReserveRepository {
    private static ReserveRepo reserveRepo;
    private ArrayList<Reservation> reservationsList;

    private ReserveRepo() { }
    public static ReserveRepo getReserveRepo() {
        if(reserveRepo == null) {
            reserveRepo = new ReserveRepo();
            reserveRepo.reservationsList = new ArrayList<Reservation>();
        }
        return reserveRepo;
    }


    public void storeReservation(Reservation reservation) {
        reservationsList.add(reservation);
        return;
    }
    public Reservation getReservationByToken(String token) {
        for (Reservation reservationEntry : reservationsList)
            if(reservationEntry.getToken().equals(token))
                return reservationEntry;
        return null;
    }
    public void updateReservation(Reservation reservation) {
        return;
    }

    public ArrayList<Reservation> getReservations() {
        return reservationsList;
    }
    public void setReservationsList(ArrayList<Reservation> reservationsList) {
        this.reservationsList = reservationsList;
    }
}