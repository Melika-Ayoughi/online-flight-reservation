package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali_Iman on 3/3/17.
 */
public class ReserveRepo implements ReserveRepository {
    private static ReserveRepo reserveRepo;
    private ArrayList<Reservation> reservationsList;

    private ReserveRepo() { }
    public static ReserveRepo getReserveRepo() {
        if(reserveRepo == null) {
            reserveRepo = new ReserveRepo();
            reserveRepo.reservationsList = new ArrayList<Reservation>();
        }
        return reserveRepo;
    }

    public void storeReservation(Reservation reservation) {
        reservationsList.add(reservation);
        return;
    }
    public Reservation getReservationByToken(String token) {
        for (Reservation reservationEntry : reservationsList)
            if(reservationEntry.getToken().equals(token))
                return reservationEntry;
        return null;
    }
    public void updateReservation(Reservation reservation) {
        return;
    }

    private ArrayList<TicketBean> getReservationTickets(Reservation reservation) {
        ArrayList<TicketBean> ticketBeans = new ArrayList<TicketBean>();
        if(reservation.getReferenceCode()==null)
            return ticketBeans;

        FlightRepo flightRepo = FlightRepo.getFlightRepo();
        Flight flight = flightRepo.searchFlight(reservation.getAirlineCode(), reservation.getFlightNumber(),
                                    reservation.getDate(), reservation.getSrcCode(), reservation.getDestCode());

        String departureTime, arrivalTime, airplaneModel;
        departureTime = flight.getDepartureTime();
        arrivalTime = flight.getArrivalTime();
        airplaneModel = flight.getAirplaneModel();

        for (Integer i = 0; i < reservation.getPassengerList().size(); i++) {
            Passenger passenger = reservation.getPassengerList().get(i);
            String ticketNo = reservation.getTicketNumbersList().get(i);

            ticketBeans.add(new TicketBean(passenger.getFirstname(), passenger.getSurname(), reservation.getReferenceCode(),
                    ticketNo, reservation.getSrcCode(), reservation.getDestCode(), reservation.getAirlineCode(),
                    reservation.getFlightNumber(), reservation.getSeatClassName(), reservation.getDate(), departureTime, arrivalTime,
                    airplaneModel, reservation.getPassengerType(i), passenger.getGender()));
        }
        return ticketBeans;
    }
    public ArrayList<TicketBean> getAllTicketBeans() {
        ArrayList<TicketBean> ticketBeans = new ArrayList<TicketBean>();
        for(Reservation reservation : reservationsList)
            ticketBeans.addAll(getReservationTickets(reservation));
        return ticketBeans;
    }
    public ArrayList<TicketBean> getUserTicketBeans(String userName) {
        ArrayList<TicketBean> ticketBeans = new ArrayList<TicketBean>();
        for(Reservation reservation : reservationsList)
            if(reservation.getUserName().equals(userName))
                ticketBeans.addAll(getReservationTickets(reservation));
        return ticketBeans;
    }
    public TicketBean getTicketBean(String ticketID) {
        for(Reservation reservation : reservationsList) {
            for(int i=0; i<reservation.getTicketNumbersList().size(); i++) {
                String ticketNo = reservation.getTicketNumbersList().get(i);
                Passenger passenger = reservation.getPassengerList().get(i);

                if(ticketNo.equals(ticketID)) {
                    Flight flight = FlightRepo.getFlightRepo().searchFlight(reservation.getAirlineCode(), reservation.getFlightNumber(),
                            reservation.getDate(), reservation.getSrcCode(), reservation.getDestCode());

                    String departureTime, arrivalTime, airplaneModel;
                    departureTime = flight.getDepartureTime();
                    arrivalTime = flight.getArrivalTime();
                    airplaneModel = flight.getAirplaneModel();

                    TicketBean ticketBean = new TicketBean(passenger.getFirstname(), passenger.getSurname(), reservation.getReferenceCode(),
                            ticketNo, reservation.getSrcCode(), reservation.getDestCode(), reservation.getAirlineCode(),
                            reservation.getFlightNumber(), reservation.getSeatClassName(), reservation.getDate(), departureTime, arrivalTime,
                            airplaneModel, reservation.getPassengerType(i), passenger.getGender());

                    return ticketBean;
                }
            }
        }
        return null;
    }
    public TicketBean getUserTicketBean(String userName, String ticketID) {
        for(Reservation reservation : reservationsList) {
            if(reservation.getUserName().equals(userName)) {
                for(int i=0; i<reservation.getTicketNumbersList().size(); i++) {
                    String ticketNo = reservation.getTicketNumbersList().get(i);
                    Passenger passenger = reservation.getPassengerList().get(i);

                    if(ticketNo.equals(ticketID)) {
                        Flight flight = FlightRepo.getFlightRepo().searchFlight(reservation.getAirlineCode(), reservation.getFlightNumber(),
                                            reservation.getDate(), reservation.getSrcCode(), reservation.getDestCode());

                        String departureTime, arrivalTime, airplaneModel;
                        departureTime = flight.getDepartureTime();
                        arrivalTime = flight.getArrivalTime();
                        airplaneModel = flight.getAirplaneModel();

                        TicketBean ticketBean = new TicketBean(passenger.getFirstname(), passenger.getSurname(), reservation.getReferenceCode(),
                                ticketNo, reservation.getSrcCode(), reservation.getDestCode(), reservation.getAirlineCode(),
                                reservation.getFlightNumber(), reservation.getSeatClassName(), reservation.getDate(), departureTime, arrivalTime,
                                airplaneModel, reservation.getPassengerType(i), passenger.getGender());

                        return ticketBean;
                    }
                }
            }
        }
        return null;
    }

    public ArrayList<Reservation> getReservationsList() {
        return reservationsList;
    }
    public void setReservationsList(ArrayList<Reservation> reservationsList) {
        this.reservationsList = reservationsList;
    }
}