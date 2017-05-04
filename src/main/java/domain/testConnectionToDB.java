package domain;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by melikaayoughi on 5/1/17.
 */
public class testConnectionToDB {

    public static void main(String[] args){

        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource) envCtx.lookup("jdbc/testdb");


            Connection connection = ds.getConnection();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery
                    ("select count(*) from users where username='melika' and password='ayoughi'");

            resultSet.next();
            boolean result = resultSet.getInt(1) != 0;

            System.out.println("results is: "+result);

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }

}
