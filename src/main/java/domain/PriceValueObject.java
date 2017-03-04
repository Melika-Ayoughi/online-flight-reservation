package domain;

/**
 * Created by Ali_Iman on 3/2/17.
 */
public class PriceValueObject {
    public Integer adultPrice;
    public Integer childPrice;
    public Integer infantPrice;

    public PriceValueObject(Integer adultPrice, Integer childPrice, Integer infantPrice) {
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
        this.infantPrice = infantPrice;
    }
}
