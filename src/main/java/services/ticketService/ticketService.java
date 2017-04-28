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

//
//        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket();
//
//        adultPassengerList.addAll(childPassengerList);
//        adultPassengerList.addAll(infantPassengerList);
//
//
//        Reservation reservation = new Reservation(flight.getSrcCode(), flight.getDestCode(),
//                flight.getDate(), flight.getAirlineCode(), flight.getFlightNumber(), seatClass.getName().toString(),
//                searchRequest.getAdultCount().toString(),searchRequest.getChildCount().toString(),searchRequest.getInfantCount().toString(), adultPassengerList);
//
//
//

//        Reservation finalReservation = AkbarTicket.getAkbarTicket().reserve(reservation);
//
//        ArrayList<TicketBean> ticketBeans = AkbarTicket.getAkbarTicket().finalize(finalReservation.getToken());
//
//        return Response.status(200).entity(ticketBeans).build();
        return Response.status(200).entity(ticketRequest).build();
    }
}
