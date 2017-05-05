package services.searchService;

import domain.*;
import services.searchService.searchRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

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

//        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket();
//        ArrayList<Flight> flights = akbarTicket.search("THR", "MHD", "05Feb", 0, 1, 0);
//
//        FlightDAO flightDAO = new FlightDAO();
//        flightDAO.getFlight("airlinecode","flightnumber", "date", "srcCode","destCode");

//        flightDAO.searchFlights("05Feb","THR","MHD");
//        flightDAO.storeFlights(flights);


//        DBConnection dbConnection = DBConnection.getDbConnection();
//        Connection con = dbConnection.getConnection();
//
//
//
//        try {
//            Statement statement = con.createStatement();
//            ResultSet resultSet = null;
//            resultSet = statement.executeQuery
//                    ("select count(*) from users where username='melika' and password='ayoughi'");
//
//            resultSet.next();
//            boolean result = resultSet.getInt(1) != 0;
//
//            System.out.println("results is: "+result);
//
//            resultSet.close();
//            statement.close();
//            dbConnection.closeConnection(con);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }


        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket();
        ArrayList<Flight> flightsList = akbarTicket.search(searchReq.getSrcCode(), searchReq.getDestCode(),searchReq.getDate(),searchReq.getAdultCount(),searchReq.getChildCount(),searchReq.getInfantCount());
        return Response.status(200).entity(flightsList).build();
    }
}
