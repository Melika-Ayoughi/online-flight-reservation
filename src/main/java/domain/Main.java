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

        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket();

        String tokenAliIman = akbarTicket.login("AliIman", "1235813");
        String tokenMelikaAyoughi = akbarTicket.login("MelikaAyoughi", "Meli");
        String tokenLeonardoDaVinci = akbarTicket.login("LeonardoDaVinci", "DaVinciCode");

        String roleAliIman = akbarTicket.getRoleByAuthenticationToken(tokenAliIman);
        String roleMelikaAyoughi = akbarTicket.getRoleByAuthenticationToken(tokenMelikaAyoughi);
        String roleLeonardoDaVinci = akbarTicket.getRoleByAuthenticationToken(tokenLeonardoDaVinci);

        ArrayList<TicketBean> ticketBeansAliIman = akbarTicket.getTickets(tokenAliIman);
        ArrayList<TicketBean> ticketBeansMelikaAyoughi = akbarTicket.getTickets(tokenMelikaAyoughi);
        ArrayList<TicketBean> ticketBeansLeonardoDaVinci = akbarTicket.getTickets(tokenLeonardoDaVinci);

        TicketBean ticketBeanAliIman = akbarTicket.getTicket(tokenAliIman, "75f51b57-4b4e-b16e-14c0-c72e3de9ed29");
        TicketBean ticketBeanMelikaAyoughi = akbarTicket.getTicket(tokenMelikaAyoughi, "75f51b57-4b4e-b16e-14c0-c72e3de9ed29");
        TicketBean ticketBeanLeonardoDaVinci = akbarTicket.getTicket(tokenLeonardoDaVinci, "75f51b57-4b4e-b16e-14c0-c72e3de9ed29");

        ArrayList<Passenger> passengers  = new ArrayList<Passenger>();
        passengers.add(new Passenger("AmazingCat","WomanTheGreat", "123098456", "female"));
        passengers.add(new Passenger("AmazingBat","ManTheGreat", "101010101", "male"));
        passengers.add(new Passenger("AmazingSuper","ManTheGreat", "009891200", "male"));
        Reservation reservation = new Reservation("THR", "MHD", "05Feb", "IR", "452", "Y", "1", "1", "1", passengers);
        reservation = akbarTicket.reserve(reservation, tokenLeonardoDaVinci);
        ArrayList<TicketBean> ticketBeans = akbarTicket.finalize(reservation.getToken());

        System.out.println("Yeah");



//        User user = userDAO.authenticateUser("LeonardoDaVinci", "DaVinciCode");
//        String role = userDAO.getUserRole("MelikaAyoughi");
//        System.out.println("Yeay");
//
//        ArrayList<TicketBean> ticketBeans1 = reserveDAO.getAllTicketBeans();
//        ArrayList<TicketBean> ticketBeans2 = reserveDAO.getUserTicketBeans("LeonardoDaVinci");
//        TicketBean ticketBean1 = reserveDAO.getTicketBean("e4b4ff88-ae14-9250-ee16-c41ac011fb53");
//        TicketBean ticketBean2 = reserveDAO.getUserTicketBean("AliIman", "8f255d05-a401-05b3-e300-eb9b5e665c01");
//
//        System.out.println("Yeay");
//
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