package domain;

import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/1/17.
 */
public class FinalizeValueObject {
    public String referenceCode;
    public ArrayList<String> ticketNoList;

    public FinalizeValueObject(String referenceCode, ArrayList<String> ticketNoList) {
        this.referenceCode = referenceCode;
        this.ticketNoList = ticketNoList;
    }
}
