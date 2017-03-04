import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/2/17.
 */
public class Main {
    public static void main(String[] args) throws IOException {
//        FlightProvider ca1HelperServer = new CA1HelperServer("188.166.78.119", 8081);
//        ArrayList<Flight> flights = ca1HelperServer.getFlightsList("THR", "MHD", "05Feb");
//        System.out.println(flights.size());
//        PriceValueObject priceValueObject = ca1HelperServer.getPricesList(flights.get(0).getMapSeatClassCapacities().get(0).getSeatClass());
//        System.out.println(flights.size());
//        ArrayList<Passenger> passengers  = new ArrayList<Passenger>();
//        System.out.println(flights.size());
//        passengers.add(new Passenger("Ali","Iman", "0912425"));
//        passengers.add(new Passenger("Melika","Ayoughi", "0939763"));
//        passengers.add(new Passenger("Hope","Vanished", "0919224"));
//        System.out.println(flights.size());
//        Reservation reservation = new Reservation("THR", "MHD", "05Feb", "IR", "452", "Y", "1", "1", "1", passengers);
//        System.out.println(flights.size());
//        ReserveValueObject reserveValueObject = ca1HelperServer.doReservation(reservation);
//        reservation.setToken(reserveValueObject.token);
//        FinalizeValueObject finalizeValueObject = ca1HelperServer.doFinalization(reservation);
//        System.out.println(flights.size());

        FlightProvider ca1HelperServer = new CA1HelperServer("188.166.78.119", 8081);
        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket();
        akbarTicket.setFlightProvider(ca1HelperServer);
        ArrayList<Flight> flights = akbarTicket.search("THR", "MHD", "05Feb", 2, 1, 0);
        System.out.println(flights.size());
        ArrayList<Passenger> passengers  = new ArrayList<Passenger>();
        passengers.add(new Passenger("Ali","Iman", "0912425"));
        passengers.add(new Passenger("Melika","Ayoughi", "0939763"));
        passengers.add(new Passenger("Hope","Vanished", "0919224"));
        Reservation reservation = new Reservation("THR", "MHD", "05Feb", "IR", "452", "Y", "1", "1", "1", passengers);
        reservation = akbarTicket.reserve(reservation);
        ArrayList<Flight> flights2 = akbarTicket.search("THR", "MHD", "05Feb", 2, 1, 0);
        ArrayList<SeatClass> seatClassRepos = SeatClassRepo.getSeatClassRepo().getSeatClasses();
        System.out.println(reservation.getTotalPrice());
    }
}
