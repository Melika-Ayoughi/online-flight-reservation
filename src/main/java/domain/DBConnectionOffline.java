package domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by melikaayoughi on 5/8/17.
 */
public class DBConnectionOffline implements DBConnection {

    private String url = "jdbc:hsqldb:hsql://localhost/testdb";
    private String username = "SA";
    private String password = "";

    public DBConnectionOffline() {}

    public Connection getConnection() {
        Connection connection=null;

        try {
            Class.forName("org.hsqldb.jdbcDriver");
            connection = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
