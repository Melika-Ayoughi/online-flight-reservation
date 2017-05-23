package domain;

/**
 * Created by Ali_Iman on 5/18/17.
 */
public class User {
    private String userName;
    private String passWord;
    private String role;

    public User(String userName, String passWord, String role) {
        this.userName = userName;
        this.passWord = passWord;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }
    public String getRole() {
        return role;
    }

    public Boolean checkPassWord(String pass) {
        if (passWord.equals(pass))
            return true;
        return false;
    }
}
