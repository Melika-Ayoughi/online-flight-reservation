package domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Ali_Iman on 5/5/17.
 */
public class AkbarTicketTest {
    AkbarTicket akbarTicket;

    @Before
    public void setUp() throws Exception {
        akbarTicket = AkbarTicket.getAkbarTicket();
        akbarTicket.setReserveRepository(ReserveRepo.getReserveRepo());
        akbarTicket.setFlightRepository(FlightRepo.getFlightRepo());
        akbarTicket.setSeatClassRepository(SeatClassRepo.getSeatClassRepo());
        ReserveRepo.getReserveRepo().setReservationsList(new ArrayList<Reservation>());
        FlightRepo.getFlightRepo().setFlightsList(new ArrayList<Flight>());
        SeatClassRepo.getSeatClassRepo().setSeatClassesList(new ArrayList<SeatClass>());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void search() throws Exception {
        ArrayList<Flight> flights = akbarTicket.search("THR", "MHD", "05Feb", 0, 1, 0);
        flights = akbarTicket.search("THR", "MHD", "05Feb", 0, 1, 0);
        flights = akbarTicket.search("THR", "MHD", "05Feb", 0, 1, 0);
        flights = akbarTicket.search("THR", "MHD", "05Feb", 0, 1, 0);
        assertEquals(flights.size() , 2);
        assertEquals(flights.get(0).getDepartureTime() , "1740");
        assertEquals(flights.get(0).getMapSeatClassCapacities().get(0).getCapacity().toString() , "8");

        assertEquals(FlightRepo.getFlightRepo().getFlights().size() , 2);
        assertEquals(FlightRepo.getFlightRepo().getFlights().get(0).getMapSeatClassCapacities().size() , 3);
        assertEquals(FlightRepo.getFlightRepo().getFlights().get(1).getMapSeatClassCapacities().size() , 3);
        assertEquals(FlightRepo.getFlightRepo().getFlights().get(0).getFlightId().toString() , "0");
        assertEquals(SeatClassRepo.getSeatClassRepo().getSeatClasses().size() , 5);
    }

    @Test
    public void searchSeatClass() throws Exception {
        SeatClass seatClass = akbarTicket.searchSeatClass('Y', "THR", "MHD", "IR");
        seatClass = akbarTicket.searchSeatClass('Y', "THR", "MHD", "IR");
        seatClass = akbarTicket.searchSeatClass('Y', "THR", "MHD", "IR");
        seatClass = akbarTicket.searchSeatClass('Y', "THR", "MHD", "IR");
        assertEquals(seatClass.getAdultPrice().toString() , "3000");
        assertEquals(seatClass.getChildPrice().toString() , "2000");
        assertEquals(seatClass.getInfantPrice().toString() , "1000");
    }



    @Test
    public void finalize() throws Exception {
        ArrayList<Passenger> passengers  = new ArrayList<Passenger>();
        passengers.add(new Passenger("Ali","Iman", "0912425489", "male"));
        passengers.add(new Passenger("Melika","Ayoughi", "0939763600", "female"));
        passengers.add(new Passenger("Hope","Vanished", "0919224123", "female"));
        Reservation reservation = new Reservation("THR", "MHD", "05Feb", "IR", "452", "Y", "1", "1", "1", passengers);
        reservation = akbarTicket.reserve(reservation);
        ArrayList<TicketBean> ticketBeans = akbarTicket.finalize(reservation.getToken());
        assertEquals(reservation.getAdultCount() , "1");
        assertEquals(reservation.getSeatClassName() , "Y");
        assertEquals(ticketBeans.size() , 3);
        assertEquals(ticketBeans.get(0).airplaneModel , "M80");
        assertEquals(ticketBeans.get(1).arrivalTime , "1850");
        assertEquals(ticketBeans.get(2).firstName , "Hope");
    }

}