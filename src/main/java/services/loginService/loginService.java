package services.loginService;

import domain.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Ali_Iman on 5/25/17.
 */
@Path("/loginService")
public class loginService {
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(loginRequest loginRequest) throws IOException {
        DBConnection dbConnection = new DBConnectionOffline();
        ReserveRepository reserveRep = new ReserveDAO(dbConnection);
        SearchLogRepository searchLogRep = new SearchLogDAO(dbConnection);
        FlightRepository flightRep = new FlightDAO(dbConnection);
        SeatClassRepository seatClassRep = new SeatClassDAO(dbConnection);
        UserRepository userRep = new UserDAO(dbConnection);

        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket(reserveRep, searchLogRep, flightRep, seatClassRep, userRep);
        String token = akbarTicket.login(loginRequest.getUsername(), loginRequest.getPassword());
        if(token==null)
            return Response.status(403).build();

        loginResponse loginResponse = new loginResponse();
        loginResponse.setToken(token);
        return Response.status(200).entity(loginResponse).build();
    }
}
