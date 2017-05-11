package domain;

import java.util.Date;

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
    private Date lastUpdateDate;

    public SeatClass(Character name, String originCode, String destinationCode, String airlineCode) {
        this.name = name;
        this.originCode = originCode;
        this.destinationCode = destinationCode;
        this.airlineCode = airlineCode;
    }

    public void setAdultPrice(Integer adultPrice) {
        this.adultPrice = adultPrice;
    }
    public void setChildPrice(Integer childPrice) {
        this.childPrice = childPrice;
    }
    public void setInfantPrice(Integer infantPrice) {
        this.infantPrice = infantPrice;
    }
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
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
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public boolean equals(SeatClass seatClass) {
        if (this.getName().equals(seatClass.getName()) &&
                this.getOriginCode().equals(seatClass.getOriginCode()) &&
                this.getDestinationCode().equals(seatClass.getDestinationCode()) &&
                this.getAirlineCode().equals(seatClass.getAirlineCode()))
            return true;
        return false;
    }

    public Integer getPriceForType(String type) {
        if (type.equals("infant"))
            return infantPrice;
        else if (type.equals("child"))
            return childPrice;
        return adultPrice;
    }
}