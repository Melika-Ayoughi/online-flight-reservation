package domain;

import org.apache.log4j.Logger;

import java.sql.*;

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
                "( \"TOKEN\", \"SRCCODE\", \"DESTCODE\", \"DATE\", \"AIRLINECODE\", \"FLIGHTNUMBER\", \"SEATCLASSNAME\", \"ADULTCOUNT\", \"CHILDCOUNT\", \"INFANTCOUNT\", \"TOTALPRICE\" )\n" +
                "VALUES ( '"+reservation.getToken()+"', '"+reservation.getSrcCode()+"', '"+reservation.getDestCode()+"', '"+reservation.getDate()+"', '"+reservation.getAirlineCode()+"', '"+reservation.getFlightNumber()+"', '"+reservation.getSeatClassName()+"', '"+reservation.getAdultCount()+"', '"+reservation.getChildCount()+"', '"+ reservation.getInfantCount()+"',"+reservation.getTotalPrice()+")";

        try {
            Statement statement = connection.createStatement();
            int insertReservationResult = statement.executeUpdate(query);
            logger.debug(insertReservationResult+" rows were inserted into reservation table without reference code");

            PreparedStatement insertmapPassengerReservation = connection.prepareStatement("INSERT INTO \"PUBLIC\".\"MAPPASSENGERRESERVATION\"\n" +
                    "( \"PASSENGERID\", \"RESERVATIONID\" ) VALUES (?,?)");

            for(Passenger passenger : reservation.getPassengerList()){
                String passegerId = storePassenger(passenger);
                insertmapPassengerReservation.setString(1,passegerId);
                insertmapPassengerReservation.setString(2,reservation.getToken());

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
        return null;
    }

    public void updateReservation(Reservation reservation) {

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
}
