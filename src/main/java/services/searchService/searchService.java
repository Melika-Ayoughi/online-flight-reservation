package services.searchService;

import domain.AkbarTicket;
import domain.Flight;
import services.searchService.searchRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by melikaayoughi on 4/26/17.
 */
@Path("/searchService")
public class searchService {
    @POST
    @Path("/getFlights")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getFlights(searchRequest searchReq) throws IOException {
//    public Response getFlights(String source, String destination, String departureDate, Integer adultCount, Integer childCount, Integer infantCount) throws IOException {
        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket();
        ArrayList<Flight> flightsList = akbarTicket.search(searchReq.getSrcCode(), searchReq.getDestCode(),searchReq.getDate(),searchReq.getAdultCount(),searchReq.getChildCount(),searchReq.getInfantCount());
        return Response.status(200).entity(flightsList).build();
    }
}
