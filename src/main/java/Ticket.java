/**
 * Created by Ali_Iman on 2/12/17.
 */
public class Ticket {
    private Passenger passenger;
    private String ticketNumber;

    public Passenger getPassenger() {
        return passenger;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public Ticket(Passenger passenger, String ticketNumber) {

        this.passenger = passenger;
        this.ticketNumber = ticketNumber;
    }
}
