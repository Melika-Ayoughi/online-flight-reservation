package domain;

import java.io.IOException;
import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.NEW;
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
            akbarTicket.flightProvider = new CA1HelperServer("178.62.207.47", 8081);
            akbarTicket.flightRepo = FlightRepo.getFlightRepo();
            akbarTicket.reserveRepo = ReserveRepo.getReserveRepo();
            akbarTicket.seatClassRepo = SeatClassRepo.getSeatClassRepo();
            akbarTicket.logger.debug("Singleton akbarTicket construction");
        }
        return akbarTicket;
    }


    public void setFlightProvider(FlightProvider flightProvider) {
        this.flightProvider = flightProvider;
    }


    private SeatClass setSeatClassPrices(SeatClass seatClass) throws IOException {
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

    private ArrayList<Flight> search(String originCode, String destCode, String date) throws IOException {
        ArrayList<Flight> flights = flightProvider.getFlightsList(originCode, destCode, date);
        for(Flight flight : flights)
            for(MapSeatClassCapacity mapSeatClassCapacity : flight.getMapSeatClassCapacities())
                mapSeatClassCapacity.setSeatClass(setSeatClassPrices(mapSeatClassCapacity.getSeatClass()));
        ArrayList<Flight> flightArrayList = new ArrayList<Flight>();
        for(Flight flight : flights)
            flightArrayList.add(flightRepo.store(flight));
        flights.clear();
        logger.debug("SRCH "+originCode+" "+destCode+" "+date);
        return flightArrayList;
    }

    public ArrayList<Flight> search(String originCode, String destCode, String date,
                                    Integer adultCount, Integer childCount, Integer infantCount) throws IOException {
        ArrayList<Flight> flightArrayList = search(originCode, destCode, date);
        Integer passengersCount = adultCount + childCount + infantCount;
        logger.info("SRCH "+originCode+" "+destCode+" "+date+" "+adultCount+" "+childCount+" "+infantCount);
        return getAppropriateFlights(flightArrayList, passengersCount);
    }


    public Reservation reserve (Reservation reservation) throws IOException {
        ReserveValueObject reserveValueObject = flightProvider.doReservation(reservation);
        reservation.setToken(reserveValueObject.token);
        reservation.setTotalPrice(reserveValueObject.adultPrice, reserveValueObject.childPrice, reserveValueObject.infantPrice);
        reservation = reserveRepo.store(reservation);
        Flight flight = searchFlight(reservation.getAirlineCode(), reservation.getFlightNumber(), reservation.getDate(),
                                                                    reservation.getSrcCode(), reservation.getDestCode());
        logger.info("RES "+reservation.getToken()+" "+reservation.getAdultCount()
                +" "+reservation.getChildCount()+" "+reservation.getInfantCount()+" "+flight.getFlightId());
        return reservation;
    }


    public ArrayList<TicketBean> finalize (String token) throws IOException {
        Reservation reservation = reserveRepo.getReservationByToken(token);
        if (reservation == null) {
            logger.debug("There is no Reservation for given token " + token);
            /*     no such reservation. should write some kind of error    */
            return null;
        }
        FinalizeValueObject finalizeValueObject = flightProvider.doFinalization(reservation);
        reservation.setReferenceCode(finalizeValueObject.referenceCode);
        reservation.setTicketNumbersList(finalizeValueObject.ticketNoList);
        logger.info("FINRES "+reservation.getToken()+" "+reservation.getReferenceCode()+" "+reservation.getTotalPrice());

        String departureTime, arrivalTime, airplaneModel;
        Flight flight = searchFlight(reservation.getAirlineCode(), reservation.getFlightNumber(), reservation.getDate(),
                                                                    reservation.getSrcCode(), reservation.getDestCode());
        departureTime = flight.getDepartureTime();
        arrivalTime = flight.getArrivalTime();
        airplaneModel = flight.getAirplaneModel();

        ArrayList<TicketBean> ticketBeans = new ArrayList<TicketBean>();
        for (Integer i = 0; i < reservation.getPassengerList().size(); i++) {
            Passenger passenger = reservation.getPassengerList().get(i);
            String ticketNo = reservation.getTicketNumbersList().get(i);
            ticketBeans.add(new TicketBean(passenger.getFirstname(), passenger.getSurname(), reservation.getReferenceCode(),
                    ticketNo, reservation.getSrcCode(), reservation.getDestCode(), reservation.getAirlineCode(),
                    reservation.getFlightNumber(), reservation.getSeatClassName(), reservation.getDate(), departureTime, arrivalTime,
                    airplaneModel, reservation.getPassengerType(i), passenger.getGender()));
            SeatClass reservationSeatClass = searchSeatClass(reservation.getSeatClassName().charAt(0), reservation.getSrcCode(),
                                                                  reservation.getDestCode(), reservation.getAirlineCode());
            logger.info("TICKET "+ticketBeans.get(i).referenceCode+" "+ticketBeans.get(i).ticketNo+" "+
                                  reservation.getPassengerList().get(i).getNationalId()+" "+
                                  reservation.getPassengerType(i)+" "+reservationSeatClass.getPriceForType(reservation.getPassengerType(i)));
        }
        return ticketBeans;
    }

    public Flight searchFlight (String airlineCode, String flightNumber, String date, String srcCode, String destCode) throws IOException {
        ArrayList<Flight> flights = search(srcCode, destCode, date);
        for(Flight flight : flights)
            if(flight.getAirlineCode().equals(airlineCode) && flight.getFlightNumber().equals(flightNumber))
                return flight;
        return null;
    }

    public SeatClass searchSeatClass (Character name, String orgCode, String destCode, String airlineCode) throws IOException {
        SeatClass seatClass = new SeatClass(name, orgCode, destCode, airlineCode);
        seatClass = setSeatClassPrices(seatClass);
        logger.debug("searchSeatClass "+seatClass.getName()+" "+seatClass.getOriginCode()+" "+seatClass.getDestinationCode()+" "
                +seatClass.getAirlineCode()+" "+seatClass.getAdultPrice());
        return seatClass;
    }
}