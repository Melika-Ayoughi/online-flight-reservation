package services.ticketService;

import domain.*;
import services.searchService.searchRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by melikaayoughi on 4/28/17.
 */
@Path("/ticketService")
public class ticketService {
    @POST
    @Path("/getTickets")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getTickets(ticketRequest ticketRequest) throws IOException {

//    public Response getTickets(searchRequest searchRequest/*, SeatClass seatClass, Flight flight*/, ArrayList<PassengerVO> adultPassengerList, ArrayList<PassengerVO> childPassengerList, ArrayList<PassengerVO> infantPassengerList) throws IOException {


        DBConnection dbConnection = new DBConnectionOffline();
        ReserveRepository reserveRep = new ReserveDAO(dbConnection);
        SearchLogRepository searchLogRep = new SearchLogDAO(dbConnection);
        FlightRepository flightRep = new FlightDAO(dbConnection);
        SeatClassRepository seatClassRep = new SeatClassDAO(dbConnection);

        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket(reserveRep, searchLogRep, flightRep, seatClassRep);

        ArrayList<PassengerVO> passengerList = ticketRequest.getAdultPassengerList();
        passengerList.addAll(ticketRequest.getChildPassengerList());
        passengerList.addAll(ticketRequest.getInfantPassengerList());

        ArrayList<Passenger> passengers = new ArrayList<Passenger>();
        for(PassengerVO passengerVO : passengerList){
            passengers.add(new Passenger(passengerVO.getFirstName(), passengerVO.getLastName()
            , passengerVO.getNationalID(), passengerVO.getGender()));
        }


        Reservation reservation = new Reservation(ticketRequest.getSearchRequest().getSrcCode()
                , ticketRequest.getSearchRequest().getDestCode()
                , ticketRequest.getSearchRequest().getDate()
                , ticketRequest.getAirlineCode()
                , ticketRequest.getFlightNumber()
                , ticketRequest.getSeatClass()
                , ticketRequest.getSearchRequest().getAdultCount().toString()
                , ticketRequest.getSearchRequest().getChildCount().toString()
                , ticketRequest.getSearchRequest().getInfantCount().toString()
                , passengers);




        Reservation finalReservation = AkbarTicket.getAkbarTicket().reserve(reservation);

        ArrayList<TicketBean> ticketBeans = AkbarTicket.getAkbarTicket().finalize(finalReservation.getToken());

        return Response.status(200).entity(ticketBeans).build();
    }
}
