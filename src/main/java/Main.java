import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/2/17.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        FlightProvider ca1HelperServer = new CA1HelperServer("188.166.78.119", 8081);
        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket();
        akbarTicket.setFlightProvider(ca1HelperServer);
        ArrayList<Flight> flights = akbarTicket.search("THR", "MHD", "05Feb", 0, 1, 0);
        System.out.println(flights.size());
        ArrayList<Passenger> passengers  = new ArrayList<Passenger>();
        passengers.add(new Passenger("Ali","Iman", "0912425"));
        passengers.add(new Passenger("Melika","Ayoughi", "0939763"));
        passengers.add(new Passenger("Hope","Vanished", "0919224"));
        Reservation reservation = new Reservation("THR", "MHD", "05Feb", "IR", "452", "Y", "1", "1", "1", passengers);
        reservation = akbarTicket.reserve(reservation);
        ArrayList<Flight> flights2 = akbarTicket.search("THR", "MHD", "05Feb", 2, 1, 0);
        ArrayList<SeatClass> seatClassRepos = SeatClassRepo.getSeatClassRepo().getSeatClasses();
        ArrayList<Flight> flightsRepooo = FlightRepo.getFlightRepo().getFlights();
        ArrayList<Reservation> reservations = ReserveRepo.getReserveRepo().getReservations();
        akbarTicket.finalize(reservation.getToken());
        System.out.println(reservation.getTotalPrice());
    }
}
