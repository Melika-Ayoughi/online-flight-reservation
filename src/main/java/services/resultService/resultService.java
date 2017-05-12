package services.resultService;

import domain.AkbarTicket;
import domain.Flight;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by melikaayoughi on 5/12/17.
 */

@Path("/resultService")
public class resultService {
    @POST
    @Path("/getImmediateFlightInfo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getImmediateFlightInfo(resultRequest resultrequest) throws IOException {
        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket();
        Flight flight = akbarTicket.immediateLookUp(resultrequest.getSrcCode(),resultrequest.getDestCode(),
                resultrequest.getDate(),resultrequest.getAirlineCode(),resultrequest.getFlightNumber(),
                resultrequest.getSeatClassName());

        return Response.status(200).entity(flight).build();
    }
}
