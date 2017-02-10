/**
 * Created by Ali_Iman on 2/10/17.
 */
public class SeatClass {
    private char name;
    private char leftNumber;
    private int adultPrice;
    private int childPrice;
    private int infantPrice;

    public void setAdultPrice(int adultPrice) {
        this.adultPrice = adultPrice;
    }

    public void setChildPrice(int childPrice) {
        this.childPrice = childPrice;
    }

    public void setInfantPrice(int infantPrice) {
        this.infantPrice = infantPrice;
    }

    public SeatClass(char name, char leftNumber) {

        this.name = name;
        this.leftNumber = leftNumber;
    }

    public char getName() {
        return name;
    }

    public char getLeftNumber() {
        return leftNumber;
    }

    public int getAdultPrice() {
        return adultPrice;
    }

    public int getChildPrice() {
        return childPrice;
    }

    public int getInfantPrice() {
        return infantPrice;
    }
}
