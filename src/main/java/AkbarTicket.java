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
//      return seatClassRepo.update(seatClass);
        return seatClass;
    }

    public ArrayList<Flight> search(String originCode, String destCode, String date,
                                    Integer adultCount, Integer childCount, Integer infantCount) {
        ArrayList<Flight> flights = flightProvider.getFlightsList(originCode, destCode, date);
        for(Flight flight : flights)
            for(MapSeatClassCapacity mapSeatClassCapacity : flight.getMapSeatClassCapacities())
                mapSeatClassCapacity.setSeatClass(setSeatClassPrices(mapSeatClassCapacity.getSeatClass()));
//      for(Flight flight : flights)
//          flight = flightRepo.update(flight);
        Integer passengersCount = adultCount + childCount + infantCount;
        for(Flight flight : flights) {
            ArrayList<MapSeatClassCapacity> seatClassCapacities = flight.getMapSeatClassCapacities();
            for (MapSeatClassCapacity mapSeatClassCapacity : seatClassCapacities)
                if (mapSeatClassCapacity.getCapacity() < passengersCount)
                    flight.getMapSeatClassCapacities().remove(mapSeatClassCapacity);
        }
        for(Flight flight : flights)
            if(flight.getMapSeatClassCapacities().size()==0)
                flights.remove(flight);
        return flights;
    }
}