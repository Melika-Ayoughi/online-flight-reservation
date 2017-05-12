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
    private SearchLogRepository searchLogRepository;
    private FlightRepository flightRepository;
    private SeatClassRepository seatClassRepository;

    public InformationProviderProxy(InformationProvider informationProvider, Integer timeToLive, SearchLogRepository searchLogRepository,
                                    FlightRepository flightRepository, SeatClassRepository seatClassRepository) {
        this.informationProvider = informationProvider;
        this.timeToLive = timeToLive;
        this.searchLogRepository = searchLogRepository;
        this.flightRepository = flightRepository;
        this.seatClassRepository = seatClassRepository;
    }


    public ArrayList<Flight> getFlightsList(String originCode, String destinationCode, String date) throws IOException {
        SearchLog searchLog = searchLogRepository.getLogRecord(originCode, destinationCode, date);

        if(searchLog == null) {
            ArrayList<Flight> flights = informationProvider.getFlightsList(originCode, destinationCode, date);
            searchLogRepository.storeLogRecord(originCode, destinationCode, date);
            flightRepository.storeFlights(flights);
            flights = flightRepository.searchFlights(date, originCode, destinationCode);
            return flights;
        }

        long minutesPassed = (System.currentTimeMillis() - searchLog.getLastUpdateDate().getTime()) / (1000 * 60);
        if(minutesPassed >= timeToLive) {
            ArrayList<Flight> flights = informationProvider.getFlightsList(originCode, destinationCode, date);
            searchLog.setLastUpdateDate(new Date());
            searchLogRepository.updateLogRecord(searchLog);
            for(Flight flight : flights)
                flightRepository.updateFlight(flight);
            flights = flightRepository.searchFlights(date, originCode, destinationCode);
            return flights;
        }

        return flightRepository.searchFlights(date, originCode, destinationCode);
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
            minutesPassed = (System.currentTimeMillis()-seatClass.getLastUpdateDate().getTime()) / (1000 * 60);

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