package services.ticketService;

import domain.*;
import org.apache.log4j.Logger;
import services.searchService.searchRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by melikaayoughi on 4/28/17.
 */
@Path("/ticketService")
public class ticketService {

    private final Logger logger = Logger.getLogger(ticketService.class);
    @POST
    @Path("/getTickets")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getTickets(ticketRequest ticketRequest) throws IOException {

        logger.debug("token is: " + ticketRequest.getToken());

        DBConnection dbConnection = new DBConnectionOffline();
        ReserveRepository reserveRep = new ReserveDAO(dbConnection);
        SearchLogRepository searchLogRep = new SearchLogDAO(dbConnection);
        FlightRepository flightRep = new FlightDAO(dbConnection);
        SeatClassRepository seatClassRep = new SeatClassDAO(dbConnection);
        UserRepository userRep = new UserDAO(dbConnection);

        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket(reserveRep, searchLogRep, flightRep, seatClassRep, userRep);

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




        Reservation finalReservation = AkbarTicket.getAkbarTicket().reserve(reservation, ticketRequest.getToken());

        ArrayList<TicketBean> ticketBeans = AkbarTicket.getAkbarTicket().finalize(finalReservation.getToken());

        return Response.status(200).entity(ticketBeans).build();
    }

    @POST
    @Path("/tickets")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response viewTickets(String token) throws IOException {
        logger.debug("before calling");
        ArrayList<TicketBean> ticketBeans = AkbarTicket.getAkbarTicket().getTickets(token);
        logger.debug("after calling. tickets beans are: "+ ticketBeans.toString());
        if(ticketBeans==null)
            return Response.status(403).build();
        return Response.status(200).entity(ticketBeans).build();
    }

    @POST
    @Path("/ticketsById")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response viewTicketById(ViewTicketRequest viewTicketRequest) throws IOException {
        logger.debug("before calling view ticket");
        TicketBean ticketBean = AkbarTicket.getAkbarTicket().getTicket(viewTicketRequest.getUserToken(), viewTicketRequest.getTicketNumber());
        logger.debug("after calling view ticket. ticket bean is: ");
        if(ticketBean==null)
            return Response.status(403).build();
        return Response.status(200).entity(ticketBean).build();
    }

}
