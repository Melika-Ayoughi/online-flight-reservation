package domain;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;


/**
 * Created by Ali_Iman on 5/18/17.
 */
public class UserDAO implements UserRepository {
    DBConnection dbConnection = null;
    private Logger logger = Logger.getLogger(UserDAO.class);
    public UserDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public User authenticateUser(String userName, String passWord) {
        Connection connection = dbConnection.getConnection();
        String query="SELECT * FROM \"PUBLIC\".\"USERS\" U where U.USERNAME = ? AND U.PASSWORD = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, passWord);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return (new User(resultSet.getString("USERNAME"), resultSet.getString("PASSWORD"), resultSet.getString("ROLE")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getUserRole(String userName) {
        Connection connection = dbConnection.getConnection();
        String query = "SELECT U.ROLE FROM \"PUBLIC\".\"USERS\" U where U.USERNAME = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return resultSet.getString("ROLE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
