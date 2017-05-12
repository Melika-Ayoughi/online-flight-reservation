package domain;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/2/17.
 */
public class Main {
    public static void main(String[] args) throws IOException {
//        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket();
        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket(ReserveRepo.getReserveRepo(), FlightRepo.getFlightRepo(), SeatClassRepo.getSeatClassRepo());

        ArrayList<Flight> flights = akbarTicket.search("THR", "MHD", "05Feb", 0, 1, 0);
        ArrayList<Passenger> passengers  = new ArrayList<Passenger>();
        passengers.add(new Passenger("Ali","Iman", "0912425489", "male"));
        passengers.add(new Passenger("Melika","Ayoughi", "0939763600", "female"));
        passengers.add(new Passenger("Hope","Vanished", "0919224123", "female"));
        Reservation reservation = new Reservation("THR", "MHD", "05Feb", "IR", "452", "Y", "1", "1", "1", passengers);
        reservation = akbarTicket.reserve(reservation);
        ArrayList<Flight> flights2 = akbarTicket.search("MHD", "THR", "06Feb", 1, 0, 0);
        ArrayList<Flight> flights3 = akbarTicket.search("THR", "MHD", "05Feb", 1, 0, 0);
        ArrayList<SeatClass> seatClassRepos = SeatClassRepo.getSeatClassRepo().getSeatClassesList();
        ArrayList<Reservation> reservations = ReserveRepo.getReserveRepo().getReservationsList();
        Flight flight1 = akbarTicket.searchFlight("THR","MHD", "05Feb", "IR", "822");
        ArrayList<TicketBean> ticketBeans = akbarTicket.finalize(reservation.getToken());
        Flight flight4 = akbarTicket.immediateLookUp("THR", "MHD", "05Feb", "IR", "822", 'Y');
        ArrayList<Flight> flightsRepooo = FlightRepo.getFlightRepo().getFlights();
        System.out.println(reservation.getTotalPrice());
    }
}