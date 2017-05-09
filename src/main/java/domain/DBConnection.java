package domain;

import java.sql.Connection;

/**
 * Created by melikaayoughi on 5/8/17.
 */
public interface DBConnection {
    Connection getConnection();
    void closeConnection(Connection connection);


}
