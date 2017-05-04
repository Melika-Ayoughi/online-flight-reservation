package domain;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

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
        return null;
    }


    public PriceValueObject getPricesList(SeatClass seatClass) throws IOException {
        return null;
    }
}
