package domain;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by melikaayoughi on 5/4/17.
 */
public class FlightDAO implements FlightRepository {
    private Logger logger = Logger.getLogger(FlightDAO.class);

    public void storeFlights(ArrayList<Flight> flights) {
        DBConnection dbConnection = DBConnection.getDbConnection();
        Connection connection = dbConnection.getConnection();



        try {

            PreparedStatement insertFlightPreparedStatement = connection.prepareStatement("INSERT INTO \"PUBLIC\".\"FLIGHTS\"\n" +
                    "( \"AIRLINECODE\", \"FLIGHTNUMBER\", \"DATE\", \"SRCCODE\", \"DESTCODE\", \"DEPARTURETIME\", \"ARRIVALTIME\", \"AIRPLANEMODEL\", \"LASTUPDATEDATE\" )\n" +
                    "VALUES (?,?,?,?,?,?,?,?,?)");
//            PreparedStatement insertSeatClassPreparedStatement = connection.prepareStatement("");
//            PreparedStatement insertMapFlightSeatClassPreparedStatement = connection.prepareStatement("");

            for (Flight flight : flights) {
               insertFlightPreparedStatement.setString(1,flight.getAirlineCode());
               insertFlightPreparedStatement.setString(2,flight.getFlightNumber());
               insertFlightPreparedStatement.setString(3,flight.getDate());
               insertFlightPreparedStatement.setString(4,flight.getSrcCode());
               insertFlightPreparedStatement.setString(5,flight.getDestCode());
               insertFlightPreparedStatement.setString(6,flight.getDepartureTime());
               insertFlightPreparedStatement.setString(7,flight.getArrivalTime());
               insertFlightPreparedStatement.setString(8,flight.getAirplaneModel());
               insertFlightPreparedStatement.setString(9,"CURRENT_TIMESTAMP");

               insertFlightPreparedStatement.executeUpdate();

//                for(MapSeatClassCapacity mapSeatClassCapacity : flight.getMapSeatClassCapacities()){
//
//                }
            }


//
//
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(query);

            logger.debug("Flight is inserted into Flights table.logger");
            System.out.println("Flight is inserted into Flights table.");
            insertFlightPreparedStatement.close();
//            statement.close();
            dbConnection.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Flight getFlight(String airlineCode, String flightNumber, String date, String srcCode, String destCode) {
        return null;
    }

    public ArrayList<Flight> searchFlights(String date, String srcCode, String destCode) {

        return null;
    }

    public void updateFlight(Flight flight) {

    }
}
