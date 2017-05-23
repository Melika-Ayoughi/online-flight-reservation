package domain;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/2/17.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        DBConnection dbConnection = new DBConnectionOffline();
        UserDAO userDAO = new UserDAO(dbConnection);
        ReserveDAO reserveDAO = new ReserveDAO(dbConnection);

        User user = userDAO.authenticateUser("LeonardoDaVinci", "DaVinciCode");
        String role = userDAO.getUserRole("MelikaAyoughi");
        System.out.println("Yeay");

        ArrayList<TicketBean> ticketBeans1 = reserveDAO.getAllTicketBeans();
        ArrayList<TicketBean> ticketBeans2 = reserveDAO.getUserTicketBeans("LeonardoDaVinci");
        TicketBean ticketBean1 = reserveDAO.getTicketBean("e4b4ff88-ae14-9250-ee16-c41ac011fb53");
        TicketBean ticketBean2 = reserveDAO.getUserTicketBean("AliIman", "8f255d05-a401-05b3-e300-eb9b5e665c01");

        System.out.println("Yeay");

        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket();
////        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket(ReserveRepo.getReserveRepo(), SearchLogRepo.getSearchLogRepo(), FlightRepo.getFlightRepo(), SeatClassRepo.getSeatClassRepo());
//
//        ArrayList<User> users = new ArrayList<User>();
//        User user = new User("AliIman", "1235813", "user");
//        User admin = new User("MelikaAyoughi", "Meli", "admin");
//        users.add(user);
//        users.add(admin);
//        UserRepo.getUserRepo().setUsersList(users);
//

//        ArrayList<Flight> flights = akbarTicket.search("THR", "MHD", "05Feb", 0, 1, 0);
//        ArrayList<Passenger> passengers  = new ArrayList<Passenger>();
//        passengers.add(new Passenger("Ali","Iman", "0912425489", "male"));
//        passengers.add(new Passenger("Melika","Ayoughi", "0939763600", "female"));
//        passengers.add(new Passenger("Hope","Vanished", "0919224123", "female"));
//        Reservation reservation = new Reservation("THR", "MHD", "05Feb", "IR", "452", "Y", "1", "1", "1", "LeonardoDaVinci", passengers);
//        reservation = akbarTicket.reserve(reservation);
//        ArrayList<TicketBean> ticketBeans = akbarTicket.finalize(reservation.getToken());
//        ArrayList<Flight> flights2 = akbarTicket.search("MHD", "THR", "06Feb", 1, 0, 0);
//        ArrayList<Flight> flights3 = akbarTicket.search("THR", "MHD", "05Feb", 1, 0, 0);
//        ArrayList<SeatClass> seatClassRepos = SeatClassRepo.getSeatClassRepo().getSeatClassesList();
//        ArrayList<Reservation> reservations = ReserveRepo.getReserveRepo().getReservationsList();
//        Flight flight1 = akbarTicket.searchFlight("THR","MHD", "05Feb", "IR", "822");
//        Flight flight4 = akbarTicket.immediateLookUp("THR", "MHD", "05Feb", "IR", "822", 'Y');
//        ArrayList<Flight> flightsRepooo = FlightRepo.getFlightRepo().getFlights();
//        ArrayList<SearchLog> searchLogsList = SearchLogRepo.getSearchLogRepo().getSearchLogsList();
//        ArrayList<Flight> emptyFlightsList = akbarTicket.search("THR", "MHD", "06Feb", 1, 0, 0);
//        System.out.println(reservation.getTotalPrice());
    }
}