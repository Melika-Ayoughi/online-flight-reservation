/**
 * Created by Ali_Iman on 3/1/17.
 */
public class ReserveValueObject {
    public String token;
    public Integer adultPrice, childPrice, infantPrice;

    public ReserveValueObject(String token, Integer adultPrice, Integer childPrice, Integer infantPrice) {
        this.token = token;
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
        this.infantPrice = infantPrice;
    }
}