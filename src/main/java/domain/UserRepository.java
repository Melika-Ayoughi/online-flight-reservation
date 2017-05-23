package domain;

/**
 * Created by Ali_Iman on 5/18/17.
 */
public interface UserRepository {
    User authenticateUser(String userName, String passWord);
    String getUserRole(String userName);
}
