import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali_Iman on 3/3/17.
 */
public class ReserveRepo {
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

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    private Reservation get(Reservation reservation) {
        for(Reservation reservationEntry : reservations)
            if(reservationEntry.equals(reservation))
                return reservationEntry;
        return null;
    }

    private boolean alreadyExists(Reservation reservation) {
        for(Reservation reservationEntry : reservations)
            if(reservationEntry.equals(reservation))
                return true;
        return false;
    }

    private Reservation update(Reservation reservation) {
        Reservation reservationEntry = get(reservation);
        /*
            update reservationEntry
            update reservationEntry
            update reservationEntry
         */
        return  reservationEntry;
    }

    private Reservation add(Reservation reservation) {
        reservations.add(reservation);
        return reservation;
    }

    public Reservation store(Reservation reservation) {
        if (alreadyExists(reservation))
            return update(reservation);
        return add(reservation);
    }

    public Reservation getReservationByToken(String token) {
        for (Reservation reservationEntry : reservations)
            if(reservationEntry.getToken().equals(token))
                return reservationEntry;
        return null;
    }
}