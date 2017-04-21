package services;

import domain.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali_Iman on 4/20/17.
 */

@Path("/test")
public class test {

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Flight> getFlights() throws IOException {
//    public Response getFlights(String source, String destination, String departureDate, Integer adultCount, Integer childCount, Integer infantCount) throws IOException {
        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket();
        ArrayList<Flight> flightsList = akbarTicket.search("THR", "MHD", "05Feb", 1, 1, 1);
        return flightsList;
        //        return Response.status(200).entity(flightsList).build();
    }
}
