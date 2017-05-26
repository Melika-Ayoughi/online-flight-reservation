package services.ticketService;

/**
 * Created by melikaayoughi on 5/26/17.
 */
public class ViewTicketRequest {
    private String userToken = "";
    private String ticketNumber = "";

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getUserToken() {
        return userToken;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }
}
