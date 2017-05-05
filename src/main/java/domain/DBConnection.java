package domain;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by melikaayoughi on 5/5/17.
 */
public class DBConnection {
    private static DBConnection dbConnection;
    private Context initCtx;
    private Context envCtx;
    private DataSource dataSource;


    public static DBConnection getDbConnection(){
        if(dbConnection==null) {
            dbConnection = new DBConnection();
            try {
                dbConnection.initCtx = new InitialContext();
                dbConnection.envCtx = (Context) dbConnection.initCtx.lookup("java:comp/env");
                dbConnection.dataSource = (DataSource) dbConnection.envCtx.lookup("jdbc/testdb");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }

        return dbConnection;
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public void closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
