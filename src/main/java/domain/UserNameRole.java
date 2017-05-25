package domain;

/**
 * Created by Ali_Iman on 5/25/17.
 */
public class UserNameRole {
    private String userName;
    private String role;

    public UserNameRole(String userName, String role) {
        this.userName = userName;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }
    public String getRole() {
        return role;
    }
}
