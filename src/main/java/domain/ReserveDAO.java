package domain;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by melikaayoughi on 5/4/17.
 */
public class ReserveDAO implements ReserveRepository {
    DBConnection dbConnection = null;
    private Logger logger = Logger.getLogger(ReserveDAO.class);
    public ReserveDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void storeReservation(Reservation reservation) {
        Connection connection = dbConnection.getConnection();
        String query="INSERT INTO \"PUBLIC\".\"RESERVATIONS\"\n" +
                "( \"TOKEN\", \"SRCCODE\", \"DESTCODE\", \"DATE\", \"AIRLINECODE\", \"FLIGHTNUMBER\", \"SEATCLASSNAME\", \"ADULTCOUNT\", \"CHILDCOUNT\", \"INFANTCOUNT\", \"TOTALPRICE\", \"USERNAME\" )\n" +
                "VALUES ( '"+reservation.getToken()+"', '"+reservation.getSrcCode()+"', '"+reservation.getDestCode()+"', '"+reservation.getDate()+"', '"+reservation.getAirlineCode()+"', '"+reservation.getFlightNumber()+"', '"+reservation.getSeatClassName()+"', '"+reservation.getAdultCount()+"', '"+reservation.getChildCount()+"', '"+ reservation.getInfantCount()+"',"+reservation.getTotalPrice()+", '"+reservation.getUserName()+"')";

        try {
            Statement statement = connection.createStatement();
            int insertReservationResult = statement.executeUpdate(query);
            logger.debug(insertReservationResult+" rows were inserted into reservation table without reference code");

            PreparedStatement insertmapPassengerReservation = connection.prepareStatement("INSERT INTO \"PUBLIC\".\"MAPPASSENGERRESERVATION\"\n" +
                    "( \"PASSENGERID\", \"RESERVATIONID\", \"PASSENGERINDEX\") VALUES (?,?,?)");

            for(int i=0; i<reservation.getPassengerList().size(); i++){
                String passegerId = storePassenger(reservation.getPassengerList().get(i));
                insertmapPassengerReservation.setString(1,passegerId);
                insertmapPassengerReservation.setString(2,reservation.getToken());
                insertmapPassengerReservation.setInt(3, i);

                insertmapPassengerReservation.executeUpdate();
            }

            insertmapPassengerReservation.close();
            statement.close();
            dbConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Reservation getReservationByToken(String token) {
        Reservation reservation = null;
        Connection connection = dbConnection.getConnection();
        String query="SELECT * FROM \"PUBLIC\".\"RESERVATIONS\" where token='"+token+"'";

        try {
            Statement statement = connection.createStatement();
            ResultSet reservationResultSet = statement.executeQuery(query);

            if(reservationResultSet.next()){
                reservation = new Reservation(reservationResultSet.getString(2), reservationResultSet.getString(3),
                        reservationResultSet.getString(4), reservationResultSet.getString(5),
                        reservationResultSet.getString(6), reservationResultSet.getString(7),
                        reservationResultSet.getString(8), reservationResultSet.getString(9),
                        reservationResultSet.getString(10), reservationResultSet.getString("USERNAME"));
                reservation.setToken(token);
                reservation.setTotalPrice(reservationResultSet.getInt(11));
                String referenceCode = reservationResultSet.getString(12);
                if(referenceCode!=null)
                    reservation.setReferenceCode(referenceCode);

                String getPassengerIdByTokenQuery = "SELECT passengerid FROM \"PUBLIC\".\"MAPPASSENGERRESERVATION\" where reservationid='"+token+"' ORDER BY PASSENGERINDEX";

                Statement statement1 = connection.createStatement();
                ResultSet passengerIdResuts = statement1.executeQuery(getPassengerIdByTokenQuery);

                ArrayList<Passenger> passengerList = new ArrayList<Passenger>();
                while(passengerIdResuts.next()){
                    Passenger passenger = getPassengerById(passengerIdResuts.getString(1));
                    if(passenger!=null){
                        passengerList.add(passenger);
                    }
                }
                reservation.setPassengerList(passengerList);

                String getTicketsQuery = "SELECT ticketnumber FROM \"PUBLIC\".\"TICKETNUMBERS\" where reservationid='"+token+"' ORDER BY ticketindex";
                Statement statement2 = connection.createStatement();
                ResultSet ticketNumbersResultSet = statement2.executeQuery(getTicketsQuery);

                ArrayList<String> ticketNumberList = new ArrayList<String>();
                while(ticketNumbersResultSet.next()){
                    ticketNumberList.add(ticketNumbersResultSet.getString(1));
                }
                if(ticketNumberList.size()!=0)
                    reservation.setTicketNumbersList(ticketNumberList);

                ticketNumbersResultSet.close();
                statement2.close();
                passengerIdResuts.close();
                statement1.close();
                reservationResultSet.close();
                statement.close();
                dbConnection.closeConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }
    public void updateReservation(Reservation reservation) {
        Connection connection = dbConnection.getConnection();
        String query = "UPDATE \"PUBLIC\".\"RESERVATIONS\" SET referencecode='"+reservation.getReferenceCode()+"' where token='"+reservation.getToken()+"'";

        try {
            Statement statement = connection.createStatement();
            int updatereservationresult = statement.executeUpdate(query);
            logger.debug(updatereservationresult+" rows were updated in reservation table");

            String preparedQuery = "INSERT INTO \"PUBLIC\".\"TICKETNUMBERS\"\n" +
                    "( \"TICKETNUMBER\", \"RESERVATIONID\", \"TICKETINDEX\" )\n" +
                    "VALUES (?,?,?)";
            PreparedStatement insertticketNumbers = connection.prepareStatement(preparedQuery);

            for(int i =0; i<reservation.getTicketNumbersList().size(); i++){
                insertticketNumbers.setString(1,reservation.getTicketNumbersList().get(i));
                insertticketNumbers.setString(2,reservation.getToken());
                insertticketNumbers.setInt(3,i);

                insertticketNumbers.executeUpdate();
                logger.debug("1 row was inserted into ticketnumbers table");
            }

            insertticketNumbers.close();
            statement.close();
            dbConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param passenger
     * @return returns passengerid
     */
    private String storePassenger(Passenger passenger){
        String passengerId = getPassengerId(passenger);

        if(passengerId == null){
            Connection connection = dbConnection.getConnection();
            String query = "INSERT INTO \"PUBLIC\".\"PASSENGERS\"\n" +
                    "( \"FIRSTNAME\", \"SURNAME\", \"NATIONALID\", \"GENDER\" )\n" +
                    "VALUES ( '"+passenger.getFirstname()+"', '"+passenger.getSurname()+"', '"+passenger.getNationalId()+"', '"+passenger.getGender()+"')";

            try {
                Statement statement = connection.createStatement();
                int insertPassengerResult = statement.executeUpdate(query);
                logger.debug(insertPassengerResult+" rows were inserted into Passenger table");
                passengerId = passenger.getNationalId();


                statement.close();
                dbConnection.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return passengerId;
    }
    private String getPassengerId(Passenger passenger){
        String passengerNationalId = null;

        Connection connection = dbConnection.getConnection();
        String query = "SELECT * FROM \"PUBLIC\".\"PASSENGERS\" where nationalid='"+passenger.getNationalId()+"'";

        try {
            Statement statement = connection.createStatement();
            ResultSet passengerResultSet = statement.executeQuery(query);

            if(passengerResultSet.next()){
                passengerNationalId = passengerResultSet.getString(3);
            }

            statement.close();
            dbConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passengerNationalId;
    }
    private Passenger getPassengerById(String passengerId){
        Passenger passenger = null;
        Connection connection = dbConnection.getConnection();

        String query ="SELECT * FROM \"PUBLIC\".\"PASSENGERS\" where nationalid="+passengerId;


        try {
            Statement statement = connection.createStatement();
            ResultSet getPassengerResult = statement.executeQuery(query);

            if(getPassengerResult.next()){
                passenger = new Passenger(getPassengerResult.getString(1), getPassengerResult.getString(2),
                        getPassengerResult.getString(3),getPassengerResult.getString(4));
            }

            getPassengerResult.close();
            statement.close();
            dbConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passenger;
    }

    /*....*/
    private String getPassengerType (Integer passengerIndex, String adultCount, String childCount) {
        if (passengerIndex < Integer.parseInt(adultCount))
            return ("adult");
        else if (passengerIndex < Integer.parseInt(adultCount)+Integer.parseInt(childCount))
            return ("child");
        return ("infant");
    }
    private ArrayList<TicketBean> getQueryTicketBeans(String query) {
        ArrayList<TicketBean> ticketBeans = new ArrayList<TicketBean>();
        Connection connection = dbConnection.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                String passengerType = getPassengerType(resultSet.getInt("PASSENGERINDEX"),
                        resultSet.getString("ADULTCOUNT"), resultSet.getString("CHILDCOUNT"));
                ticketBeans.add(new TicketBean(resultSet.getString("FIRSTNAME"),
                        resultSet.getString("SURNAME"), resultSet.getString("REFERENCECODE"),
                        resultSet.getString("TICKETNUMBER"), resultSet.getString("SRCCODE"),
                        resultSet.getString("DESTCODE"), resultSet.getString("AIRLINECODE"),
                        resultSet.getString("FLIGHTNUMBER"), resultSet.getString("SEATCLASSNAME"),
                        resultSet.getString("DATE"), resultSet.getString("DEPARTURETIME"),
                        resultSet.getString("ARRIVALTIME"), resultSet.getString("AIRPLANEMODEL"),
                        passengerType, resultSet.getString("GENDER")));
            }
            resultSet.close();
            statement.close();
            dbConnection.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketBeans;
    }
    public ArrayList<TicketBean> getAllTicketBeans() {
        String query = "SELECT MPR.PASSENGERINDEX, R.ADULTCOUNT, R.CHILDCOUNT, P.FIRSTNAME, P.SURNAME, R.REFERENCECODE, TN.TICKETNUMBER, R.SRCCODE, R.DESTCODE, R.AIRLINECODE, R.FLIGHTNUMBER, R.SEATCLASSNAME, R.DATE, F.DEPARTURETIME, F.ARRIVALTIME, F.AIRPLANEMODEL, P.GENDER FROM \"PUBLIC\".\"TICKETNUMBERS\" TN, \"PUBLIC\".\"RESERVATIONS\" R, \"PUBLIC\".\"FLIGHTS\" F, \"PUBLIC\".\"MAPPASSENGERRESERVATION\" MPR, \"PUBLIC\".\"PASSENGERS\" P WHERE TN.RESERVATIONID = R.TOKEN AND R.SRCCODE = F.SRCCODE AND R.DESTCODE = F.DESTCODE AND R.DATE = F.DATE AND R.AIRLINECODE = F.AIRLINECODE AND F.FLIGHTNUMBER = R.FLIGHTNUMBER AND P.NATIONALID = MPR.PASSENGERID AND MPR.RESERVATIONID = R.TOKEN AND MPR.PASSENGERINDEX = TN.TICKETINDEX";
        return getQueryTicketBeans(query);
    }
    public ArrayList<TicketBean> getUserTicketBeans(String userName) {
        String query = "SELECT MPR.PASSENGERINDEX, R.ADULTCOUNT, R.CHILDCOUNT, P.FIRSTNAME, P.SURNAME, R.REFERENCECODE, TN.TICKETNUMBER, R.SRCCODE, R.DESTCODE, R.AIRLINECODE, R.FLIGHTNUMBER, R.SEATCLASSNAME, R.DATE, F.DEPARTURETIME, F.ARRIVALTIME, F.AIRPLANEMODEL, P.GENDER FROM \"PUBLIC\".\"TICKETNUMBERS\" TN, \"PUBLIC\".\"RESERVATIONS\" R, \"PUBLIC\".\"FLIGHTS\" F, \"PUBLIC\".\"MAPPASSENGERRESERVATION\" MPR, \"PUBLIC\".\"PASSENGERS\" P, \"PUBLIC\".\"USERS\" U WHERE TN.RESERVATIONID = R.TOKEN AND U.USERNAME = R.USERNAME AND R.SRCCODE = F.SRCCODE AND R.DESTCODE = F.DESTCODE AND R.DATE = F.DATE AND R.AIRLINECODE = F.AIRLINECODE AND F.FLIGHTNUMBER = R.FLIGHTNUMBER AND P.NATIONALID = MPR.PASSENGERID AND MPR.RESERVATIONID = R.TOKEN AND MPR.PASSENGERINDEX = TN.TICKETINDEX AND U.USERNAME = '" + userName + "'";
        return getQueryTicketBeans(query);
    }
    public TicketBean getTicketBean(String ticketId) {
        ArrayList<TicketBean> ticketBeans = new ArrayList<TicketBean>();
        String query = "SELECT MPR.PASSENGERINDEX, R.ADULTCOUNT, R.CHILDCOUNT, P.FIRSTNAME, P.SURNAME, R.REFERENCECODE, TN.TICKETNUMBER, R.SRCCODE, R.DESTCODE, R.AIRLINECODE, R.FLIGHTNUMBER, R.SEATCLASSNAME, R.DATE, F.DEPARTURETIME, F.ARRIVALTIME, F.AIRPLANEMODEL, P.GENDER FROM \"PUBLIC\".\"TICKETNUMBERS\" TN, \"PUBLIC\".\"RESERVATIONS\" R, \"PUBLIC\".\"FLIGHTS\" F, \"PUBLIC\".\"MAPPASSENGERRESERVATION\" MPR, \"PUBLIC\".\"PASSENGERS\" P, \"PUBLIC\".\"USERS\" U WHERE  TN.RESERVATIONID = R.TOKEN AND U.USERNAME = R.USERNAME AND R.SRCCODE = F.SRCCODE AND R.DESTCODE = F.DESTCODE AND R.DATE = F.DATE AND R.AIRLINECODE = F.AIRLINECODE AND F.FLIGHTNUMBER = R.FLIGHTNUMBER AND P.NATIONALID = MPR.PASSENGERID AND MPR.RESERVATIONID = R.TOKEN AND MPR.PASSENGERINDEX = TN.TICKETINDEX AND TN.TICKETNUMBER = '" + ticketId + "'";
        ticketBeans = getQueryTicketBeans(query);
        if(ticketBeans.size()==0)
            return null;
        return  ticketBeans.get(0);
    }
    public TicketBean getUserTicketBean(String userName, String ticketId) {
        ArrayList<TicketBean> ticketBeans = new ArrayList<TicketBean>();
        String query = "SELECT MPR.PASSENGERINDEX, R.ADULTCOUNT, R.CHILDCOUNT, P.FIRSTNAME, P.SURNAME, R.REFERENCECODE, TN.TICKETNUMBER, R.SRCCODE, R.DESTCODE, R.AIRLINECODE, R.FLIGHTNUMBER, R.SEATCLASSNAME, R.DATE, F.DEPARTURETIME, F.ARRIVALTIME, F.AIRPLANEMODEL, P.GENDER FROM \"PUBLIC\".\"TICKETNUMBERS\" TN, \"PUBLIC\".\"RESERVATIONS\" R, \"PUBLIC\".\"FLIGHTS\" F, \"PUBLIC\".\"MAPPASSENGERRESERVATION\" MPR, \"PUBLIC\".\"PASSENGERS\" P, \"PUBLIC\".\"USERS\" U WHERE  TN.RESERVATIONID = R.TOKEN AND U.USERNAME = R.USERNAME AND R.SRCCODE = F.SRCCODE AND R.DESTCODE = F.DESTCODE AND R.DATE = F.DATE AND R.AIRLINECODE = F.AIRLINECODE AND F.FLIGHTNUMBER = R.FLIGHTNUMBER AND P.NATIONALID = MPR.PASSENGERID AND MPR.RESERVATIONID = R.TOKEN AND MPR.PASSENGERINDEX = TN.TICKETINDEX AND TN.TICKETNUMBER = '" + ticketId + "' AND U.USERNAME = '" + userName + "'";
        ticketBeans = getQueryTicketBeans(query);
        if(ticketBeans.size()==0)
            return null;
        return  ticketBeans.get(0);
    }
}