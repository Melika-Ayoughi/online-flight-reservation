package services.resultService;

import domain.*;

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
        DBConnection dbConnection = new DBConnectionOffline();
        ReserveRepository reserveRep = new ReserveDAO(dbConnection);
        SearchLogRepository searchLogRep = new SearchLogDAO(dbConnection);
        FlightRepository flightRep = new FlightDAO(dbConnection);
        SeatClassRepository seatClassRep = new SeatClassDAO(dbConnection);
        UserRepository userRep = new UserDAO(dbConnection);

        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket(reserveRep, searchLogRep, flightRep, seatClassRep, userRep);

        String role = akbarTicket.getRoleByAuthenticationToken(resultrequest.getToken());
        System.out.println("role is: "+role);
        if(role==null || role.equals("admin")){
            System.out.println("role is: "+role);
            return Response.status(403).build();
        }

        Flight flight = akbarTicket.immediateLookUp(resultrequest.getSrcCode(),resultrequest.getDestCode(),
                resultrequest.getDate(),resultrequest.getAirlineCode(),resultrequest.getFlightNumber(),
                resultrequest.getSeatClassName());

        return Response.status(200).entity(flight).build();
    }
}
