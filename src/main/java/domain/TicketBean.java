package domain;

/**
 * Created by Ali_Iman on 3/5/17.
 */
public class TicketBean {
    public String firstName;
    public String surname;
    public String referenceCode;
    public String ticketNo;
    public String originCode;
    public String destinationCode;
    public String airlineCode;
    public String flightNo;
    public String seatClassName;
    public String departureTime;
    public String arrivalTime;
    public String airplaneModel;

    public TicketBean(String firstName, String surname, String referenceCode, String ticketNo, String originCode, String destinationCode, String airlineCode, String flightNo, String seatClassName, String departureTime, String arrivalTime, String airplaneModel) {
        this.firstName = firstName;
        this.surname = surname;
        this.referenceCode = referenceCode;
        this.ticketNo = ticketNo;
        this.originCode = originCode;
        this.destinationCode = destinationCode;
        this.airlineCode = airlineCode;
        this.flightNo = flightNo;
        this.seatClassName = seatClassName;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airplaneModel = airplaneModel;
    }

    @Override
    public String toString() {
        return "TicketBean{" +
                "firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", referenceCode='" + referenceCode + '\'' +
                ", ticketNo='" + ticketNo + '\'' +
                ", originCode='" + originCode + '\'' +
                ", destinationCode='" + destinationCode + '\'' +
                ", airlineCode='" + airlineCode + '\'' +
                ", flightNo='" + flightNo + '\'' +
                ", seatClassName='" + seatClassName + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", airplaneModel='" + airplaneModel + '\'' +
                '}';
    }
}