package domain;

import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/3/17.
 */
public class ReserveRepo {
    private static ReserveRepo reserveRepo;
    private ArrayList<Reservation> reservations;

    private ReserveRepo() { }
    public static ReserveRepo getReserveRepo() {
        if(reserveRepo == null)
            reserveRepo = new ReserveRepo();
        return reserveRepo;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }
    public ArrayList<Reservation> getReservations() {
        return reservations;
    }
}