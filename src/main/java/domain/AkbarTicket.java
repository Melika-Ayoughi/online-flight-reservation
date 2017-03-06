package domain;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 * Created by Ali_Iman on 3/3/17.
 */
public class AkbarTicket {
    private static AkbarTicket akbarTicket;
    private FlightProvider flightProvider;
    private FlightRepo flightRepo;
    private ReserveRepo reserveRepo;
    private SeatClassRepo seatClassRepo;
    private final Logger logger = Logger.getLogger(AkbarTicket.class);


    private AkbarTicket() { }
    public static AkbarTicket getAkbarTicket() throws IOException {
        if(akbarTicket == null){
            akbarTicket = new AkbarTicket();
            // Default provider
            akbarTicket.flightProvider = new CA1HelperServer("188.166.78.119", 8081);
            akbarTicket.flightRepo = FlightRepo.getFlightRepo();
            akbarTicket.reserveRepo = ReserveRepo.getReserveRepo();
            akbarTicket.seatClassRepo = SeatClassRepo.getSeatClassRepo();
        }
        return akbarTicket;
    }


    public void setFlightProvider(FlightProvider flightProvider) {
        this.flightProvider = flightProvider;
    }


    private SeatClass setSeatClassPrices(SeatClass seatClass) {
        PriceValueObject priceValueObject = flightProvider.getPricesList(seatClass);
        seatClass.setAdultPrice(priceValueObject.adultPrice);
        seatClass.setChildPrice(priceValueObject.childPrice);
        seatClass.setInfantPrice(priceValueObject.infantPrice);
        return seatClassRepo.store(seatClass);
    }


    private Flight copyFlight(Flight flight) {
        Flight copy = new Flight(flight.getAirlineCode(), flight.getFlightNumber(), flight.getDate(), flight.getSrcCode(),
                flight.getDestCode(), flight.getDepartureTime(), flight.getArrivalTime(), flight.getAirplaneModel(),
                flight.getMapSeatClassCapacities());
        copy.setFlightId(flight.getFlightId());
        ArrayList<MapSeatClassCapacity> copyMapSeatClassCapacities = new ArrayList<MapSeatClassCapacity>();
        for (MapSeatClassCapacity mapSeatClassCapacity : flight.getMapSeatClassCapacities())
                copyMapSeatClassCapacities.add(new MapSeatClassCapacity(mapSeatClassCapacity.getSeatClass(), mapSeatClassCapacity.getCapacity()));
        copy.setMapSeatClassCapacities(copyMapSeatClassCapacities);
        return copy;
    }


    private ArrayList<Flight> getAppropriateFlights (ArrayList<Flight> flights, Integer passengersCount) {
        ArrayList<Flight> copiedFlights = new ArrayList<Flight>();
        for (Flight flight : flights)
            copiedFlights.add(copyFlight(flight));
        for(Flight flight : copiedFlights) {
            ArrayList<MapSeatClassCapacity> seatClassCapacities = new ArrayList<MapSeatClassCapacity>();
            for (MapSeatClassCapacity mapSeatClassCapacity : flight.getMapSeatClassCapacities())
                seatClassCapacities.add(mapSeatClassCapacity);
            for (MapSeatClassCapacity mapSeatClassCapacity : seatClassCapacities)
                if (mapSeatClassCapacity.getCapacity() < passengersCount)
                    flight.getMapSeatClassCapacities().remove(mapSeatClassCapacity);
            seatClassCapacities.clear();
        }
        ArrayList<Flight> flightArrayList = new ArrayList<Flight>();
        for (Flight flight : copiedFlights)
            flightArrayList.add(flight);
        for (Flight flight : flightArrayList)
            if(flight.getMapSeatClassCapacities().size()==0)
                copiedFlights.remove(flight);
        flightArrayList.clear();
        return copiedFlights;
    }


    public ArrayList<Flight> search(String originCode, String destCode, String date,
                                    Integer adultCount, Integer childCount, Integer infantCount) {
        ArrayList<Flight> flights = flightProvider.getFlightsList(originCode, destCode, date);
        for(Flight flight : flights)
            for(MapSeatClassCapacity mapSeatClassCapacity : flight.getMapSeatClassCapacities())
                mapSeatClassCapacity.setSeatClass(setSeatClassPrices(mapSeatClassCapacity.getSeatClass()));
        ArrayList<Flight> flightArrayList = new ArrayList<Flight>();
        for(Flight flight : flights)
            flightArrayList.add(flightRepo.store(flight));
        flights.clear();
        Integer passengersCount = adultCount + childCount + infantCount;
        logger.info("SRCH "+originCode+" "+destCode+" "+date);
        return getAppropriateFlights(flightArrayList, passengersCount);
    }


    public Reservation reserve (Reservation reservation) {
        ReserveValueObject reserveValueObject = flightProvider.doReservation(reservation);
        reservation.setToken(reserveValueObject.token);
        reservation.setTotalPrice(reserveValueObject.adultPrice, reserveValueObject.childPrice, reserveValueObject.infantPrice);
        reservation = reserveRepo.store(reservation);
        //TODO add flight id
        logger.info("RES "+reservation.getToken()+" "+reservation.getAdultCount()
                +" "+reservation.getChildCount()+" "+reservation.getInfantCount());
        return reservation;
    }


    public ArrayList<TicketBean> finalize (String token) {
        Reservation reservation = reserveRepo.getReservationByToken(token);
        if (reservation == null) {
            /*     no such reservation. should write some kind of error    */
            return null;
        }
        FinalizeValueObject finalizeValueObject = flightProvider.doFinalization(reservation);
        reservation.setReferenceCode(finalizeValueObject.referenceCode);
        reservation.setTicketNumbersList(finalizeValueObject.ticketNoList);
        logger.info("FINRES "+reservation.getToken()+" "+reservation.getReferenceCode()+" "+reservation.getTotalPrice());

        String departureTime = "", arrivalTime = "", airplaneModel = "";
        ArrayList<Flight> flights = akbarTicket.search(reservation.getSrcCode(), reservation.getDestCode(), reservation.getDate(), 0, 0, 0);
        for (Flight flight : flights)
            if (flight.getAirlineCode().equals(reservation.getAirlineCode()) && flight.getFlightNumber().equals(reservation.getFlightNumber())) {
                departureTime = flight.getDepartureTime();
                arrivalTime = flight.getArrivalTime();
                airplaneModel = flight.getAirplaneModel();
            }

        ArrayList<TicketBean> ticketBeans = new ArrayList<TicketBean>();
        for (Integer i = 0; i < reservation.getPassengerList().size(); i++) {
            Passenger passenger = reservation.getPassengerList().get(i);
            String ticketNo = reservation.getTicketNumbersList().get(i);
            ticketBeans.add(new TicketBean(passenger.getFirstname(), passenger.getSurname(), reservation.getReferenceCode(),
                    ticketNo, reservation.getSrcCode(), reservation.getDestCode(), reservation.getAirlineCode(),
                    reservation.getFlightNumber(), reservation.getSeatClassName(), departureTime, arrivalTime, airplaneModel));
            //TODO add<Customer Type> <Price>
            logger.info("TICKET "+ticketBeans.get(i).referenceCode+" "+ticketBeans.get(i).ticketNo
                    +" "+reservation.getPassengerList().get(i).getNationalId()+" ");
        }
        return ticketBeans;
    }
}