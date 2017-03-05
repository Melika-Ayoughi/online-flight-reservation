import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/3/17.
 */
public class AkbarTicket {
    private static AkbarTicket akbarTicket;
    private FlightProvider flightProvider;
    private FlightRepo flightRepo;
    private ReserveRepo reserveRepo;
    private SeatClassRepo seatClassRepo;


    private AkbarTicket() { }
    public static AkbarTicket getAkbarTicket() {
        if(akbarTicket == null){
            akbarTicket = new AkbarTicket();
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
        for(Flight flight : flights)
            flight = flightRepo.store(flight);
        Integer passengersCount = adultCount + childCount + infantCount;
        return getAppropriateFlights(flights, passengersCount);
    }


    public Reservation reserve (Reservation reservation) {
        ReserveValueObject reserveValueObject = flightProvider.doReservation(reservation);
        reservation.setToken(reserveValueObject.token);
        reservation.setTotalPrice(reserveValueObject.adultPrice, reserveValueObject.childPrice, reserveValueObject.infantPrice);
        reservation = reserveRepo.store(reservation);
        return reservation;
    }


    public Reservation finalize (String token) {
        Reservation reservation = reserveRepo.getReservationByToken(token);
        if (reservation == null) {
            /*
                no such reservation
                should write some kind of error
             */
            return null;
        }
        FinalizeValueObject finalizeValueObject = flightProvider.doFinalization(reservation);
        reservation.setReferenceCode(finalizeValueObject.referenceCode);
        reservation.setTicketNumbersList(finalizeValueObject.ticketNoList);
        return reservation;
    }
}