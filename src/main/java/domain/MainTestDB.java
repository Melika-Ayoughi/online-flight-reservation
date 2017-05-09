package domain;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by melikaayoughi on 5/8/17.
 */
public class MainTestDB {
    public static void main(String[] args) throws IOException {
//        DBConnection dbConnection = DBConnectionWithTomcat.getDbConnectionWithTomcat();
        DBConnection dbConnection = new DBConnectionOffline();
        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket();
//        ArrayList<Flight> flights = akbarTicket.search("THR", "MHD", "05Feb", 0, 1, 0);
        FlightDAO flightDAO = new FlightDAO(dbConnection);

//        flightDAO.storeFlights(flights);





//        FlightDAO flightDAO = new FlightDAO(dbConnection);
//
//        flightDAO.getFlight("airlinecode","flightnumber", "date", "srcCode","destCode");
//
//        ArrayList<Flight> flightArrayList = flightDAO.searchFlights("05Feb","THR","MHD");

        Flight flight = flightDAO.getFlight("IR","452","05Feb","THR","ISF");





        /*
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(*) from users where username='melika' and password='ayoughi'");
            resultSet.next();
            boolean result = resultSet.getInt(1) != 0;
            System.out.println("result: "+result);
            resultSet.close();
            statement.close();
            dbConnection.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        */

    }
}
