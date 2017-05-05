package domain;

import org.apache.log4j.Logger;

import java.sql.*;
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
            PreparedStatement insertSeatClassPreparedStatement = connection.prepareStatement("INSERT INTO \"PUBLIC\".\"SEATCLASSES\"\n" +
                    "( \"NAME\", \"ORIGINCODE\", \"DESTINATIONCODE\", \"AIRLINECODE\" ) \n" +
                    "VALUES (?,?,?,?)");
            PreparedStatement insertMapFlightSeatClassPreparedStatement = connection.prepareStatement("INSERT INTO \"PUBLIC\".\"MAPFLIGHTSEATCLASS\"\n" +
                    "( \"FLIGHTID\", \"SEATCLASSID\", \"CAPACITY\" )\n" +
                    "VALUES (?,?,?)");

            for (Flight flight : flights) {
                insertFlightPreparedStatement.setString(1,flight.getAirlineCode());
                insertFlightPreparedStatement.setString(2,flight.getFlightNumber());
                insertFlightPreparedStatement.setString(3,flight.getDate());
                insertFlightPreparedStatement.setString(4,flight.getSrcCode());
                insertFlightPreparedStatement.setString(5,flight.getDestCode());
                insertFlightPreparedStatement.setString(6,flight.getDepartureTime());
                insertFlightPreparedStatement.setString(7,flight.getArrivalTime());
                insertFlightPreparedStatement.setString(8,flight.getAirplaneModel());
                insertFlightPreparedStatement.setTimestamp(9,new Timestamp(System.currentTimeMillis()));


                insertFlightPreparedStatement.executeUpdate();
                logger.debug("Flight "+flight.getAirlineCode()+","+flight.getFlightNumber()+","+flight.getDate()+","+flight.getSrcCode()+","+flight.getDestCode()+","+flight.getDepartureTime()+","+flight.getArrivalTime()+","+flight.getAirplaneModel()+" is inserted into Flights table.");


                for(int j=0; j<flight.getMapSeatClassCapacities().size(); j++){



                    if(getSeatClassIndex(flight.getMapSeatClassCapacities().get(j).getSeatClass().getName(),
                            flight.getMapSeatClassCapacities().get(j).getSeatClass().getOriginCode(),
                            flight.getMapSeatClassCapacities().get(j).getSeatClass().getDestinationCode(),
                            flight.getMapSeatClassCapacities().get(j).getSeatClass().getAirlineCode())==0) {

                        insertSeatClassPreparedStatement.setString(1, Character.toString(flight.getMapSeatClassCapacities().get(j).getSeatClass().getName()));
                        insertSeatClassPreparedStatement.setString(2, flight.getMapSeatClassCapacities().get(j).getSeatClass().getOriginCode());
                        insertSeatClassPreparedStatement.setString(3, flight.getMapSeatClassCapacities().get(j).getSeatClass().getDestinationCode());
                        insertSeatClassPreparedStatement.setString(4, flight.getMapSeatClassCapacities().get(j).getSeatClass().getAirlineCode());

                        insertSeatClassPreparedStatement.executeUpdate();
                        logger.debug("SeatClass " + flight.getMapSeatClassCapacities().get(j).getSeatClass().getName() + "," +
                                flight.getMapSeatClassCapacities().get(j).getSeatClass().getOriginCode() + "," +
                                flight.getMapSeatClassCapacities().get(j).getSeatClass().getDestinationCode() + "," +
                                flight.getMapSeatClassCapacities().get(j).getSeatClass().getAirlineCode() + "," +
                                " is inserted into SeatClass table.");

                    }
                    else {
                        System.out.println("seatclass was already there");
                    }


                    System.out.println("flight id: "+getFlightIndex(flight.getAirlineCode(),flight.getFlightNumber(),flight.getDate(),flight.getSrcCode(),flight.getDestCode()));

                    System.out.println("seatclass id: "+getSeatClassIndex(flight.getMapSeatClassCapacities().get(j).getSeatClass().getName(),
                            flight.getMapSeatClassCapacities().get(j).getSeatClass().getOriginCode(),
                            flight.getMapSeatClassCapacities().get(j).getSeatClass().getDestinationCode(),
                            flight.getMapSeatClassCapacities().get(j).getSeatClass().getAirlineCode()));

                    System.out.println("capacity: "+flight.getMapSeatClassCapacities().get(j).getCapacity());



                    insertMapFlightSeatClassPreparedStatement.setInt(1,getFlightIndex(flight.getAirlineCode(),flight.getFlightNumber(),flight.getDate(),flight.getSrcCode(),flight.getDestCode()));
                    insertMapFlightSeatClassPreparedStatement.setInt(2, getSeatClassIndex(flight.getMapSeatClassCapacities().get(j).getSeatClass().getName(),
                            flight.getMapSeatClassCapacities().get(j).getSeatClass().getOriginCode(),
                            flight.getMapSeatClassCapacities().get(j).getSeatClass().getDestinationCode(),
                            flight.getMapSeatClassCapacities().get(j).getSeatClass().getAirlineCode()));
                    insertMapFlightSeatClassPreparedStatement.setInt(3, flight.getMapSeatClassCapacities().get(j).getCapacity());

                    insertMapFlightSeatClassPreparedStatement.executeUpdate();
                    logger.debug("MapFlightSeatClass is inserted into MapFlightSeatClass table");

                }
            }

            insertFlightPreparedStatement.close();
            insertSeatClassPreparedStatement.close();
            insertMapFlightSeatClassPreparedStatement.close();
            dbConnection.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Flight getFlight(String airlineCode, String flightNumber, String date, String srcCode, String destCode) {

        Flight resultFlight = null;

        DBConnection dbConnection = DBConnection.getDbConnection();
        Connection connection = dbConnection.getConnection();

        String query = "SELECT * FROM \"PUBLIC\".\"FLIGHTS\"\n" +
                "where airlineCode='IR' and flightNumber='452' and date='05Feb' and srcCode='THR' and destCode='MHD'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()){
                System.out.println("flight: "+resultSet.getInt(1)+resultSet.getString(2)+","+resultSet.getString(3)+","+
                        resultSet.getString(4)+","+resultSet.getString(5)+","+
                        resultSet.getString(6)+","+ resultSet.getString(7)+","+
                        resultSet.getString(8)+","+resultSet.getString(9)+","+
                        resultSet.getTimestamp(10));

                String mapseatclasscapacityquery = "SELECT * FROM \"PUBLIC\".\"MAPFLIGHTSEATCLASS\" m, \"PUBLIC\".\"SEATCLASSES\" s\n" +
                        "where m.flightId="+resultSet.getInt(1)+" and m.seatclassid = s.seatClassId";

                Statement joinstatement = connection.createStatement();
                ResultSet joinResultSet = joinstatement.executeQuery(mapseatclasscapacityquery);

                ArrayList<MapSeatClassCapacity> mapSeatClassCapacities = new ArrayList<MapSeatClassCapacity>();

                while (joinResultSet.next()){
                    System.out.println("join results: "+joinResultSet.getInt(1)+","+joinResultSet.getInt(2)+","+
                            joinResultSet.getInt(3)+","+joinResultSet.getString(5)+","+
                            joinResultSet.getInt(6)+","+joinResultSet.getInt(7)+","+joinResultSet.getInt(8)+","+
                            joinResultSet.getString(9)+","+joinResultSet.getString(10)+","+joinResultSet.getString(11)+","+
                            joinResultSet.getTimestamp(12));

                    SeatClass seatClass = new SeatClass(joinResultSet.getString(5).charAt(0),joinResultSet.getString(9),
                            joinResultSet.getString(10),joinResultSet.getString(11));

                    seatClass.setInfantPrice(joinResultSet.getInt(8));
                    seatClass.setChildPrice(joinResultSet.getInt(7));
                    seatClass.setAdultPrice(joinResultSet.getInt(6));


                    seatClass.setLastUpdateDate(joinResultSet.getTimestamp(12));

                    mapSeatClassCapacities.add(new MapSeatClassCapacity(seatClass,joinResultSet.getInt(3)));
                    System.out.println("--------------------------------");
                }

                resultFlight = new Flight(resultSet.getString(2),resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7),
                        resultSet.getString(8),resultSet.getString(9),
                        resultSet.getDate(10),mapSeatClassCapacities);

                joinResultSet.close();
                joinstatement.close();
            }
            resultSet.close();
            statement.close();
            dbConnection.closeConnection(connection);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultFlight;

    }

    public ArrayList<Flight> searchFlights(String date, String srcCode, String destCode) {

        ArrayList<Flight> resultFlights = new ArrayList<Flight>();

        DBConnection dbConnection = DBConnection.getDbConnection();
        Connection connection = dbConnection.getConnection();

        String query = "SELECT * FROM \"PUBLIC\".\"FLIGHTS\"\n" +
                "where date='"+date+"' and srcCode='"+srcCode+"' and destCode='"+destCode+"'";

        try{


            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            //for each flight
            while (resultSet.next()) {

                System.out.println("flight: "+resultSet.getInt(1)+resultSet.getString(2)+","+resultSet.getString(3)+","+
                        resultSet.getString(4)+","+resultSet.getString(5)+","+
                        resultSet.getString(6)+","+ resultSet.getString(7)+","+
                        resultSet.getString(8)+","+resultSet.getString(9)+","+
                        resultSet.getTimestamp(10));

                String mapseatclasscapacityquery = "SELECT * FROM \"PUBLIC\".\"MAPFLIGHTSEATCLASS\" m, \"PUBLIC\".\"SEATCLASSES\" s\n" +
                        "where m.flightId="+resultSet.getInt(1)+" and m.seatclassid = s.seatClassId";

                Statement joinstatement = connection.createStatement();
                ResultSet joinResultSet = joinstatement.executeQuery(mapseatclasscapacityquery);

                ArrayList<MapSeatClassCapacity> mapSeatClassCapacities = new ArrayList<MapSeatClassCapacity>();

                while (joinResultSet.next()){
                    System.out.println("join results: "+joinResultSet.getInt(1)+","+joinResultSet.getInt(2)+","+
                            joinResultSet.getInt(3)+","+joinResultSet.getString(5)+","+
                            joinResultSet.getInt(6)+","+joinResultSet.getInt(7)+","+joinResultSet.getInt(8)+","+
                            joinResultSet.getString(9)+","+joinResultSet.getString(10)+","+joinResultSet.getString(11)+","+
                            joinResultSet.getTimestamp(12));

                    SeatClass seatClass = new SeatClass(joinResultSet.getString(5).charAt(0),joinResultSet.getString(9),
                            joinResultSet.getString(10),joinResultSet.getString(11));

                    seatClass.setAdultPrice(joinResultSet.getInt(6));
                    seatClass.setChildPrice(joinResultSet.getInt(7));
                    seatClass.setInfantPrice(joinResultSet.getInt(8));
                    seatClass.setLastUpdateDate(joinResultSet.getTimestamp(12));

//                    System.out.println(" capacity is: "+joinResultSet.getInt(3));

                    mapSeatClassCapacities.add(new MapSeatClassCapacity(seatClass,joinResultSet.getInt(3)));
                    System.out.println("--------------------------------");
                }


                resultFlights.add(new Flight(resultSet.getString(2),resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7),
                        resultSet.getString(8),resultSet.getString(9),
                        resultSet.getDate(10),mapSeatClassCapacities));

                joinResultSet.close();
                joinstatement.close();

            }

            System.out.println("resultsflights: "+resultFlights);
            resultSet.close();
            statement.close();
            dbConnection.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(resultFlights.size()==0)
            return null;

        return  resultFlights;

    }

    public void updateFlight(Flight flight) {

    }

    //flightId, FLIGHTS
    //seatClassId, SEATCLASSES
    private Integer getLastIndex(String column, String table){
        Integer lastIndex = 0;

        DBConnection dbConnection = DBConnection.getDbConnection();
        Connection connection = dbConnection.getConnection();

        String query = "SELECT MAX("+column+") FROM \"PUBLIC\".\""+table+"\"";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            resultSet.next();
            lastIndex = resultSet.getInt(1);
            resultSet.close();
            statement.close();
            dbConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastIndex;
    }

    /**
     *
     * @param name
     * @param originCode
     * @param destinationCode
     * @param airlineCode
     * @return returns 0 if seatClass was not found and returns the index of the seatclass otherwise
     */
    private Integer getSeatClassIndex(Character name, String originCode, String destinationCode, String airlineCode){
        Integer index = 0;

        DBConnection dbConnection = DBConnection.getDbConnection();
        Connection connection = dbConnection.getConnection();

        String query = "SELECT * FROM \"PUBLIC\".\"SEATCLASSES\"\n" +
                "where name='"+name+"' and originCode='"+originCode+"' and destinationCode='"+destinationCode+"' and airlineCode='"+airlineCode+"'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            if(resultSet.next())
                index = resultSet.getInt(1);

            resultSet.close();
            statement.close();
            dbConnection.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    /**
     *
     * @param airlineCode
     * @param flightNumber
     * @param date
     * @param srcCode
     * @param destCode
     * @return returns 0 if flight was not found and returns the index of the flight otherwise
     */
    private Integer getFlightIndex(String airlineCode, String flightNumber, String date, String srcCode, String destCode){
        Integer index = 0;

        DBConnection dbConnection = DBConnection.getDbConnection();
        Connection connection = dbConnection.getConnection();

        try{
            String query = "SELECT * FROM \"PUBLIC\".\"FLIGHTS\"\n" +
                    "where airlineCode='"+airlineCode+"' and flightNumber='"+flightNumber+"' and date='"+date+"' and srcCode='"+srcCode+"' and destCode='"+destCode+"'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next())
                index = resultSet.getInt(1);

            resultSet.close();
            statement.close();
            dbConnection.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

       return index;
    }


}
