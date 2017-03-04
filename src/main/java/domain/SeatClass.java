package domain;

/**
 * Created by Ali_Iman on 2/10/17.
 */
public class SeatClass {
    private Character name;
    private Integer adultPrice;
    private Integer childPrice;
    private Integer infantPrice;
    private String originCode;
    private String destinationCode;
    private String airlineCode;

    public SeatClass(Character name, String originCode, String destinationCode, String airlineCode) {
        this.name = name;
        this.originCode = originCode;
        this.destinationCode = destinationCode;
        this.airlineCode = airlineCode;
    }

    public void setAdultPrice(int adultPrice) {
        this.adultPrice = adultPrice;
    }
    public void setChildPrice(int childPrice) {
        this.childPrice = childPrice;
    }
    public void setInfantPrice(int infantPrice) {
        this.infantPrice = infantPrice;
    }

    public Character getName() {
        return name;
    }
    public String getOriginCode() {
        return originCode;
    }
    public String getDestinationCode() {
        return destinationCode;
    }
    public String getAirlineCode() {
        return airlineCode;
    }
    public Integer getAdultPrice() {
        return adultPrice;
    }
    public Integer getChildPrice() {
        return childPrice;
    }
    public Integer getInfantPrice() {
        return infantPrice;
    }

    public boolean equals(SeatClass seatClass) {
        if (this.getName().equals(seatClass.getName()) &&
                this.getOriginCode().equals(seatClass.getOriginCode()) &&
                this.getDestinationCode().equals(seatClass.getDestinationCode()) &&
                this.getAirlineCode().equals(seatClass.getAirlineCode()))
            return true;
        return false;
    }
}