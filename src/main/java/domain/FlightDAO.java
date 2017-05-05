package domain;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by melikaayoughi on 5/4/17.
 */
public class FlightDAO implements FlightRepository {
    private Logger logger = Logger.getLogger(FlightDAO.class);

    //it's better to have store flights so we could use prepared statements
    public void storeFlight(Flight flight) {
        DBConnection dbConnection = DBConnection.getDbConnection();
        Connection connection = dbConnection.getConnection();

        String query = "INSERT INTO \"PUBLIC\".\"FLIGHTS\"\n" +
                "( \"AIRLINECODE\", \"FLIGHTNUMBER\", \"DATE\", \"SRCCODE\", \"DESTCODE\", \"DEPARTURETIME\", \"ARRIVALTIME\", \"AIRPLANEMODEL\", \"LASTUPDATEDATE\" )\n" +
                "VALUES ('"+flight.getAirlineCode()+"', '"+flight.getFlightNumber()+"', '"+flight.getDate()+"', '"+flight.getSrcCode()+"', '"+flight.getDestCode()+"', '"+flight.getDepartureTime()+"', '"+flight.getArrivalTime()+"', '"+flight.getAirplaneModel()+"', CURRENT_TIMESTAMP)";


        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            logger.debug("Flight is inserted into Flights table.logger");
            System.out.println("Flight is inserted into Flights table.");
            statement.close();
            dbConnection.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Flight getFlight(String airlineCode, String flightNumber, String date, String srcCode, String destCode) {
        return null;
    }

    public void storeFlights(ArrayList<Flight> flights) {

    }

    public ArrayList<Flight> searchFlights(String date, String srcCode, String destCode) {

        return null;
    }

    public void updateFlight(Flight flight) {

    }
}
