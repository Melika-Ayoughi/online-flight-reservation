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
    private InformationProvider informationProvider;
    private InformationProvider immediateInformationProvider;
    private TicketIssuer ticketIssuer;
    private ReserveRepository reserveRepository;
    private SearchLogRepository searchLogRepository;
    private FlightRepository flightRepository;
    private SeatClassRepository seatClassRepository;
    private final Logger logger = Logger.getLogger(AkbarTicket.class);


    private AkbarTicket() { }
    public static AkbarTicket getAkbarTicket() throws IOException {
        if(akbarTicket == null){
            akbarTicket = new AkbarTicket();
            OnlineFlightProvider onlineFlightProvider = new CA1HelperServer("178.62.207.47", 8081);

            DBConnection dbConnection = new DBConnectionOffline();
            akbarTicket.reserveRepository = new ReserveDAO(dbConnection);
            akbarTicket.searchLogRepository = new SearchLogDAO(dbConnection);
            akbarTicket.flightRepository = new FlightDAO(dbConnection);
            akbarTicket.seatClassRepository = new SeatClassDAO(dbConnection);

            akbarTicket.immediateInformationProvider = new InformationProviderProxy(onlineFlightProvider, 0,
                    akbarTicket.searchLogRepository, akbarTicket.flightRepository, akbarTicket.seatClassRepository);
            akbarTicket.informationProvider = new InformationProviderProxy(onlineFlightProvider, 30,
                    akbarTicket.searchLogRepository, akbarTicket.flightRepository, akbarTicket.seatClassRepository);
            akbarTicket.ticketIssuer = onlineFlightProvider;
            akbarTicket.logger.debug("Singleton akbarTicket construction");
        }
        return akbarTicket;
    }
    public static AkbarTicket getAkbarTicket(ReserveRepository reserveRepo, SearchLogRepository searchLogRepo, FlightRepository flightRepo, SeatClassRepository seatClassRepo) throws IOException {
        if(akbarTicket == null){
            akbarTicket = new AkbarTicket();
            OnlineFlightProvider onlineFlightProvider = new CA1HelperServer("178.62.207.47", 8081);

            akbarTicket.reserveRepository = reserveRepo;
            akbarTicket.searchLogRepository = searchLogRepo;
            akbarTicket.flightRepository = flightRepo;
            akbarTicket.seatClassRepository = seatClassRepo;

            akbarTicket.immediateInformationProvider = new InformationProviderProxy(onlineFlightProvider, 0,
                    akbarTicket.searchLogRepository, akbarTicket.flightRepository, akbarTicket.seatClassRepository);
            akbarTicket.informationProvider = new InformationProviderProxy(onlineFlightProvider, 30,
                    akbarTicket.searchLogRepository, akbarTicket.flightRepository, akbarTicket.seatClassRepository);
            akbarTicket.ticketIssuer = onlineFlightProvider;
            akbarTicket.logger.debug("Singleton akbarTicket construction with inputs");
        }
        return akbarTicket;
    }


    public void setInformationProvider(InformationProvider informationProvider) {
        this.informationProvider = informationProvider;
    }
    public void setImmediateInformationProvider(InformationProvider immediateInformationProvider) {
        this.immediateInformationProvider = immediateInformationProvider;
    }
    public void setTicketIssuer(TicketIssuer ticketIssuer) {
        this.ticketIssuer = ticketIssuer;
    }
    public void setReserveRepository(ReserveRepository reserveRepository) {
        this.reserveRepository = reserveRepository;
    }
    public void setFlightRepository(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
    public void setSeatClassRepository(SeatClassRepository seatClassRepository) {
        this.seatClassRepository = seatClassRepository;
    }


    private Flight copyFlight(Flight flight) {
        Flight copy = new Flight(flight.getAirlineCode(), flight.getFlightNumber(), flight.getDate(), flight.getSrcCode(),
                flight.getDestCode(), flight.getDepartureTime(), flight.getArrivalTime(), flight.getAirplaneModel(),
                flight.getLastUpdateDate(), flight.getMapSeatClassCapacities());
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
    private SeatClass setSeatClassPrices(SeatClass seatClass, InformationProvider infoProvider) throws IOException {
        PriceValueObject priceValueObject = infoProvider.getPricesList(seatClass);
        seatClass.setAdultPrice(priceValueObject.adultPrice);
        seatClass.setChildPrice(priceValueObject.childPrice);
        seatClass.setInfantPrice(priceValueObject.infantPrice);
        return seatClass;
    }
    private ArrayList<Flight> search(String originCode, String destCode, String date, InformationProvider infoProvider) throws IOException {
        ArrayList<Flight> flights = infoProvider.getFlightsList(originCode, destCode, date);
        if(flights == null)
            return (new ArrayList<Flight>());
        for(Flight flight : flights)
            for(MapSeatClassCapacity mapSeatClassCapacity : flight.getMapSeatClassCapacities())
                mapSeatClassCapacity.setSeatClass(setSeatClassPrices(mapSeatClassCapacity.getSeatClass(), infoProvider));
        logger.debug("SRCH "+originCode+" "+destCode+" "+date);
        return flights;
    }


    private SeatClass searchSeatClass (Character name, String orgCode, String destCode, String airlineCode, InformationProvider infoProvider) throws IOException {
        SeatClass seatClass = new SeatClass(name, orgCode, destCode, airlineCode);
        seatClass = setSeatClassPrices(seatClass, infoProvider);
        logger.debug("searchSeatClass "+seatClass.getName()+" "+seatClass.getOriginCode()+" "+seatClass.getDestinationCode()+" "
                +seatClass.getAirlineCode()+" "+seatClass.getAdultPrice());
        return seatClass;
    }
    private ArrayList<Flight> search(String originCode, String destCode, String date, Integer adultCount,
                                     Integer childCount, Integer infantCount, InformationProvider infoProvider) throws IOException {
        ArrayList<Flight> flightArrayList = search(originCode, destCode, date, infoProvider);
        Integer passengersCount = adultCount + childCount + infantCount;
        logger.info("SRCH "+originCode+" "+destCode+" "+date+" "+adultCount+" "+childCount+" "+infantCount);
        return getAppropriateFlights(flightArrayList, passengersCount);
    }
    private Flight searchFlight (String srcCode, String destCode, String date, String airlineCode,
                                 String flightNumber, InformationProvider infoProvider) throws IOException {
        ArrayList<Flight> flights = search(srcCode, destCode, date, infoProvider);
        for(Flight flight : flights)
            if(flight.getAirlineCode().equals(airlineCode) && flight.getFlightNumber().equals(flightNumber))
                return flight;
        return null;
    }


    public SeatClass searchSeatClass (Character name, String orgCode, String destCode, String airlineCode) throws IOException {
        return searchSeatClass(name, orgCode, destCode, airlineCode, informationProvider);
    }
    public ArrayList<Flight> search(String originCode, String destCode, String date, Integer adultCount,
                                    Integer childCount, Integer infantCount) throws IOException {
        return search(originCode, destCode, date, adultCount, childCount, infantCount, informationProvider);
    }
    public Flight searchFlight (String srcCode, String destCode, String date, String airlineCode, String flightNumber) throws IOException {
        return searchFlight(srcCode, destCode, date, airlineCode, flightNumber, informationProvider);
    }
    public Flight immediateLookUp (String srcCode, String destCode, String date, String airlineCode, String flightNumber, Character seatClassName) throws IOException {
        setSeatClassPrices(new SeatClass(seatClassName, srcCode, destCode, airlineCode), immediateInformationProvider);
        ArrayList<Flight> flights = immediateInformationProvider.getFlightsList(srcCode, destCode, date);
        for(Flight flightEntry : flights)
            if(flightEntry.getAirlineCode().equals(airlineCode) && flightEntry.getFlightNumber().equals(flightNumber))
                return flightEntry;
        return null;
    }


    public Reservation reserve (Reservation reservation) throws IOException {
        ReserveValueObject reserveValueObject = ticketIssuer.doReservation(reservation);
        reservation.setToken(reserveValueObject.token);
        reservation.setTotalPrice(reserveValueObject.adultPrice, reserveValueObject.childPrice, reserveValueObject.infantPrice);

        reserveRepository.storeReservation(reservation);
        Flight flight = searchFlight(reservation.getSrcCode(), reservation.getDestCode(),
                                     reservation.getDate(), reservation.getAirlineCode(), reservation.getFlightNumber());

        logger.info("RES "+reservation.getToken()+" "+reservation.getAdultCount()
                +" "+reservation.getChildCount()+" "+reservation.getInfantCount()+" "+flight.getFlightId());
        return reservation;
    }
    public ArrayList<TicketBean> finalize (String token) throws IOException {
        Reservation reservation = reserveRepository.getReservationByToken(token);
        if (reservation == null) {
            logger.debug("There is no Reservation for given token " + token);
            /*     no such reservation. should write some kind of error    */
            return null;
        }
        FinalizeValueObject finalizeValueObject = ticketIssuer.doFinalization(reservation);
        reservation.setReferenceCode(finalizeValueObject.referenceCode);
        reservation.setTicketNumbersList(finalizeValueObject.ticketNoList);

        reserveRepository.updateReservation(reservation);
        logger.info("FINRES "+reservation.getToken()+" "+reservation.getReferenceCode()+" "+reservation.getTotalPrice());

        String departureTime, arrivalTime, airplaneModel;
        Flight flight = searchFlight(reservation.getSrcCode(), reservation.getDestCode(), reservation.getDate(),
                                                                    reservation.getAirlineCode(), reservation.getFlightNumber());
        departureTime = flight.getDepartureTime();
        arrivalTime = flight.getArrivalTime();
        airplaneModel = flight.getAirplaneModel();

        ArrayList<TicketBean> ticketBeans = new ArrayList<TicketBean>();
        SeatClass reservationSeatClass = searchSeatClass(reservation.getSeatClassName().charAt(0), reservation.getSrcCode(),
                reservation.getDestCode(), reservation.getAirlineCode());
        for (Integer i = 0; i < reservation.getPassengerList().size(); i++) {
            Passenger passenger = reservation.getPassengerList().get(i);
            String ticketNo = reservation.getTicketNumbersList().get(i);
            ticketBeans.add(new TicketBean(passenger.getFirstname(), passenger.getSurname(), reservation.getReferenceCode(),
                    ticketNo, reservation.getSrcCode(), reservation.getDestCode(), reservation.getAirlineCode(),
                    reservation.getFlightNumber(), reservation.getSeatClassName(), reservation.getDate(), departureTime, arrivalTime,
                    airplaneModel, reservation.getPassengerType(i), passenger.getGender()));
            logger.info("TICKET "+ticketBeans.get(i).referenceCode+" "+ticketBeans.get(i).ticketNo+" "+
                                  reservation.getPassengerList().get(i).getNationalId()+" "+
                                  reservation.getPassengerType(i)+" "+reservationSeatClass.getPriceForType(reservation.getPassengerType(i)));
        }
        return ticketBeans;
    }
}