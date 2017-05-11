package domain;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by melikaayoughi on 5/4/17.
 */
public class SeatClassDAO implements SeatClassRepository {
    DBConnection dbConnection = null;

    private Logger logger = Logger.getLogger(SeatClass.class);

    public SeatClassDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void storeSeatClass(SeatClass seatClass) {
        //should be empty
    }

    public SeatClass getSeatClass(Character name, String originCode, String destinationCode, String airlineCode) {
        SeatClass resultSeatClass = null;

        Connection connection = dbConnection.getConnection();

        String query = "SELECT * FROM \"PUBLIC\".\"SEATCLASSES\" where name='"+name+"' and origincode='"+originCode+"' and destinationcode='"+destinationCode+"' and airlinecode='"+airlineCode+"'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()){
                resultSeatClass = new SeatClass(resultSet.getString(2).charAt(0),resultSet.getString(6),
                        resultSet.getString(7),resultSet.getString(8));
                resultSeatClass.setAdultPrice(Integer.parseInt(resultSet.getString(3)));
                resultSeatClass.setChildPrice(Integer.parseInt(resultSet.getString(4)));
                resultSeatClass.setInfantPrice(Integer.parseInt(resultSet.getString(5)));
                resultSeatClass.setLastUpdateDate(resultSet.getTimestamp(9));
            }

            resultSet.close();
            statement.close();
            dbConnection.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSeatClass;
    }

    public void updateSeatClass(SeatClass seatClass) {

        Connection connection = dbConnection.getConnection();

        Integer indexSeatClass = getSeatClassIndex(seatClass.getName(), seatClass.getOriginCode(), seatClass.getDestinationCode(),
                seatClass.getAirlineCode());

        String query = "UPDATE \"PUBLIC\".\"SEATCLASSES\" SET adultprice="+seatClass.getAdultPrice()+", childprice="+seatClass.getChildPrice()+", infantprice="+seatClass.getInfantPrice()+", lastupdatedate=CURRENT_TIMESTAMP where seatclassid="+indexSeatClass;


        try {
            Statement statement = connection.createStatement();
            int updateSeatClassResult = statement.executeUpdate(query);
            logger.debug(updateSeatClassResult+" rows were updated in seatClass table");

        } catch (SQLException e) {
            e.printStackTrace();
        }

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

    public boolean seatClassExists(Character name, String originCode, String destinationCode, String airlineCode){
        if(getSeatClassIndex(name,originCode,destinationCode,airlineCode)==0)
            return false;
        return true;
    }

}
