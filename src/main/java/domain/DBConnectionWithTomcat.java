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
public class DBConnectionWithTomcat implements DBConnection{
    private static DBConnectionWithTomcat dbConnectionWithTomcat;
    private Context initCtx;
    private Context envCtx;
    private DataSource dataSource;

    private DBConnectionWithTomcat() {}

    public static DBConnectionWithTomcat getDbConnectionWithTomcat(){
        if(dbConnectionWithTomcat ==null) {
            dbConnectionWithTomcat = new DBConnectionWithTomcat();
            try {
                dbConnectionWithTomcat.initCtx = new InitialContext();
                dbConnectionWithTomcat.envCtx = (Context) dbConnectionWithTomcat.initCtx.lookup("java:comp/env");
                dbConnectionWithTomcat.dataSource = (DataSource) dbConnectionWithTomcat.envCtx.lookup("jdbc/testdb");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }

        return dbConnectionWithTomcat;
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
