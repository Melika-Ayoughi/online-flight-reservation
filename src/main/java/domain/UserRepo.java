package domain;

import java.util.ArrayList;

/**
 * Created by Ali_Iman on 5/18/17.
 */
public class UserRepo implements UserRepository {
    private static UserRepo userRepo;
    private ArrayList<User> usersList;

    private UserRepo() { }
    public static UserRepo getUserRepo() {
        if(userRepo == null) {
            userRepo = new UserRepo();
            userRepo.usersList = new ArrayList<User>();
        }
        return userRepo;
    }

    public User authenticateUser(String userName, String passWord) {
        for(User userEntry : usersList)
            if(userEntry.getUserName().equals(userName) && userEntry.checkPassWord(passWord))
                return userEntry;
        return null;
    }
    public String getUserRole(String userName) {
        for(User userEntry : usersList)
            if(userEntry.getUserName().equals(userName))
                return userEntry.getRole();
        return null;
    }

    public ArrayList<User> getUsersList() {
        return usersList;
    }
    public void setUsersList(ArrayList<User> usersList) {
        this.usersList = usersList;
    }
}
