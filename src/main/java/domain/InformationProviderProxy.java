package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ali_Iman on 5/4/17.
 */
public class InformationProviderProxy implements InformationProvider {
    private InformationProvider informationProvider;
    private Integer timeToLive;
    private FlightRepository flightRepository;
    private SeatClassRepository seatClassRepository;

    public InformationProviderProxy(InformationProvider informationProvider, Integer timeToLive,
                                    FlightRepository flightRepository, SeatClassRepository seatClassRepository) {
        this.informationProvider = informationProvider;
        this.timeToLive = timeToLive;
        this.flightRepository = flightRepository;
        this.seatClassRepository = seatClassRepository;
    }


    public ArrayList<Flight> getFlightsList(String originCode, String destinationCode, String date) throws IOException {
        ArrayList<Flight> flights = flightRepository.searchFlights(date, originCode, destinationCode);
        if (flights == null) {
            flights = informationProvider.getFlightsList(originCode, destinationCode, date);
            flightRepository.storeFlights(flights);
            return flights;
        }
        long minutesPassed = ((new Date()).getTime()-flights.get(0).getLastUpdateDate().getTime())/(60 * 1000 * 60);
        if(minutesPassed >= timeToLive) {
            flights = informationProvider.getFlightsList(originCode, destinationCode, date);
            for(Flight flight : flights)
                flightRepository.updateFlight(flight);
            return flights;
        }
        return flights;
    }


    public PriceValueObject getPricesList(SeatClass inputSeatClass) throws IOException {
        SeatClass seatClass = seatClassRepository.getSeatClass(inputSeatClass.getName(), inputSeatClass.getOriginCode(),
                                                    inputSeatClass.getDestinationCode(), inputSeatClass.getAirlineCode());
        if (seatClass == null) {
            PriceValueObject priceValueObject = informationProvider.getPricesList(inputSeatClass);
            inputSeatClass.setAdultPrice(priceValueObject.adultPrice);
            inputSeatClass.setChildPrice(priceValueObject.childPrice);
            inputSeatClass.setInfantPrice(priceValueObject.infantPrice);
            seatClassRepository.storeSeatClass(inputSeatClass);
            return priceValueObject;
        }

        Long minutesPassed = null;
        if(seatClass.getAdultPrice() != null)
            minutesPassed = ((new Date()).getTime()-seatClass.getLastUpdateDate().getTime())/(60 * 1000 * 60);

        if(minutesPassed == null || minutesPassed >= timeToLive) {
            PriceValueObject priceValueObject = informationProvider.getPricesList(seatClass);
            seatClass.setAdultPrice(priceValueObject.adultPrice);
            seatClass.setChildPrice(priceValueObject.childPrice);
            seatClass.setInfantPrice(priceValueObject.infantPrice);
            seatClassRepository.updateSeatClass(seatClass);
            return priceValueObject;
        }
        inputSeatClass.setLastUpdateDate(seatClass.getLastUpdateDate());
        return (new PriceValueObject(seatClass.getAdultPrice(), seatClass.getChildPrice(), seatClass.getInfantPrice()));
    }
}