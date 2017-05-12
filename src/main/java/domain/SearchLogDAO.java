package domain;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by melikaayoughi on 5/11/17.
 */
public class SearchLogDAO implements SearchLogRepository {
    DBConnection dbConnection = null;
    private Logger logger = Logger.getLogger(SearchLogDAO.class);

    public SearchLogDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void storeLogRecord(String srcCode, String destCode, String date){
        Connection connection = dbConnection.getConnection();
        String query = "INSERT INTO \"PUBLIC\".\"LOGS\"\n" +
                "( \"SRCCODE\", \"DESTCODE\", \"DATE\", \"LASTUPDATEDATE\" )\n" +
                "VALUES ( '"+srcCode+"', '"+destCode+"', '"+date+"',CURRENT_TIMESTAMP)";

        try {
            Statement statement = connection.createStatement();
            Integer insertLogResult = statement.executeUpdate(query);
            logger.debug(insertLogResult+" rows were inserted into Logs table");

            statement.close();
            dbConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public SearchLog getLogRecord(String srcCode, String destCode, String date){
        SearchLog logrecord = null;

        Connection connection = dbConnection.getConnection();
        String query="SELECT * FROM \"PUBLIC\".\"LOGS\" where srccode='"+srcCode+"' and destcode='"+destCode+"' and date='"+date+"'";

        try {
            Statement statement = connection.createStatement();
            ResultSet getLogResultSet = statement.executeQuery(query);

            if(getLogResultSet.next()){
                logrecord = new SearchLog(getLogResultSet.getString(1), getLogResultSet.getString(2),
                        getLogResultSet.getString(3), getLogResultSet.getTimestamp(4));
            }

            getLogResultSet.close();
            statement.close();
            dbConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logrecord;
    }

    public void updateLogRecord(SearchLog searchLog){
        Connection connection = dbConnection.getConnection();
        String query = "UPDATE \"PUBLIC\".\"LOGS\" SET lastupdatedate=CURRENT_TIMESTAMP where " +
                "srccode='"+searchLog.getSrcCode()+"' and destcode='"+searchLog.getDestCode()+"' and date='"+searchLog.getDate()+"'";

        try {
            Statement statement = connection.createStatement();
            Integer updateLogResult = statement.executeUpdate(query);
            logger.debug(updateLogResult+" rows were updated in Logs table");

            statement.close();
            dbConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
