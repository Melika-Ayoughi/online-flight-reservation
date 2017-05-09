package domain;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by melikaayoughi on 5/4/17.
 */
public class FlightDAO implements FlightRepository {
    DBConnection dbConnection = null;

    private Logger logger = Logger.getLogger(FlightDAO.class);

    public FlightDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void storeFlights(ArrayList<Flight> flights) {
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

        Connection connection = dbConnection.getConnection();

        String query = "SELECT * FROM \"PUBLIC\".\"FLIGHTS\"\n" +
                "where airlineCode='"+airlineCode+"' and flightNumber='"+flightNumber+"' and date='"+date+"' and srcCode='"+srcCode+"' and destCode='"+destCode+"'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()){
                resultFlight = constructFlightSeatClass(resultSet);
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

        Connection connection = dbConnection.getConnection();

        String query = "SELECT * FROM \"PUBLIC\".\"FLIGHTS\"\n" +
                "where date='"+date+"' and srcCode='"+srcCode+"' and destCode='"+destCode+"'";

        try{


            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            //for each flight
            while (resultSet.next()) {
                Flight newFlight = constructFlightSeatClass(resultSet);
                resultFlights.add(newFlight);
            }

            System.out.println("Resulted Flights: "+resultFlights);
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
        Connection connection = dbConnection.getConnection();
        Flight searchedFlight = getFlight(flight.getAirlineCode(), flight.getFlightNumber(), flight.getDate(), flight.getSrcCode(), flight.getDestCode());

        if (searchedFlight!=null){
            String query = "UPDATE \"PUBLIC\".\"FLIGHTS\" SET arrivaltime='"+flight.getArrivalTime()+"', departuretime='"+flight.getDepartureTime()+"', airplanemodel='"+flight.getAirplaneModel()+"', lastupdatedate=CURRENT_TIMESTAMP\n" +
                    "where flightid="+searchedFlight.getFlightId();

            try {
                Statement statement = connection.createStatement();
                int updateFlightResult = statement.executeUpdate(query);

                logger.debug(updateFlightResult+" rows in Flight table were updated.");

                PreparedStatement updateCapacityPrepared = connection.prepareStatement("UPDATE \"PUBLIC\".\"MAPFLIGHTSEATCLASS\" SET capacity = ? where flightid=? and seatclassid=?");

                for (MapSeatClassCapacity mapSeatClassCapacity : flight.getMapSeatClassCapacities()){
                    updateCapacityPrepared.setInt(1,mapSeatClassCapacity.getCapacity());
                    updateCapacityPrepared.setInt(2,searchedFlight.getFlightId());
                    updateCapacityPrepared.setInt(3,getSeatClassIndex(mapSeatClassCapacity.getSeatClass().getName(),
                            mapSeatClassCapacity.getSeatClass().getOriginCode(), mapSeatClassCapacity.getSeatClass().getDestinationCode(),
                            mapSeatClassCapacity.getSeatClass().getAirlineCode()));
                    logger.debug("1 row is ready to be updated in mapflightseatclass");
                }
                updateCapacityPrepared.executeUpdate();
                updateCapacityPrepared.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Flight constructFlightSeatClass(ResultSet flightResultSet) throws SQLException {

        Flight resultFlight = null;
        Connection connection = dbConnection.getConnection();

        System.out.println("Flight: FlightID: "+flightResultSet.getInt(1)+", AirlineCODE: "+flightResultSet.getString(2)
                +", Flightnumber: "+ flightResultSet.getString(3)+", date: "+ flightResultSet.getString(4)
                +", SourceCode: "+flightResultSet.getString(5)+", DestinationCode: "+ flightResultSet.getString(6)
                +", DepTime: "+flightResultSet.getString(7)+", ArrTime: "+ flightResultSet.getString(8)
                +", Airplanemodel: "+flightResultSet.getString(9)+", LastUpdateDate: "+ flightResultSet.getTimestamp(10));

        String mapseatclasscapacityquery = "SELECT * FROM \"PUBLIC\".\"MAPFLIGHTSEATCLASS\" m, \"PUBLIC\".\"SEATCLASSES\" s\n" +
                "where m.flightId="+flightResultSet.getInt(1)+" and m.seatclassid = s.seatClassId";

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


            mapSeatClassCapacities.add(new MapSeatClassCapacity(seatClass,joinResultSet.getInt(3)));
            System.out.println("SeatClass: AirlineCode: "+seatClass.getAirlineCode()+", OriginCode: "+seatClass.getOriginCode()
            +", DestCode: "+seatClass.getDestinationCode()+", Name: "+seatClass.getName()
            +", AdultPrice: "+seatClass.getAdultPrice()+", ChildPrice: "+seatClass.getChildPrice()
            +", InfantPrice: "+seatClass.getInfantPrice()+", LastUpdate: "+seatClass.getLastUpdateDate());
        }

        resultFlight = new Flight(flightResultSet.getString(2),flightResultSet.getString(3),
                flightResultSet.getString(4), flightResultSet.getString(5),
                flightResultSet.getString(6), flightResultSet.getString(7),
                flightResultSet.getString(8),flightResultSet.getString(9),
                flightResultSet.getDate(10),mapSeatClassCapacities);

        resultFlight.setFlightId(flightResultSet.getInt(1));

        joinResultSet.close();
        joinstatement.close();

        return resultFlight;
    }

    /**
     *
     * @param column is flightId or seatClassId
     * @param table is FLIGHTS or SEATCLASSES
     * @return
     */
    private Integer getLastIndex(String column, String table){
        Integer lastIndex = 0;

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
