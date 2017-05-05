package domain;

import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/3/17.
 */
public class SeatClassRepo implements SeatClassRepository {
    private static SeatClassRepo seatClassRepo;
    private ArrayList<SeatClass> seatClassesList;

    private SeatClassRepo() { }
    public static SeatClassRepo getSeatClassRepo() {
        if(seatClassRepo == null) {
            seatClassRepo = new SeatClassRepo();
            seatClassRepo.seatClassesList = new ArrayList<SeatClass>();
        }
        return seatClassRepo;
    }


    public void storeSeatClass(SeatClass seatClass) {
        seatClassesList.add(seatClass);
        return;
    }
    public SeatClass getSeatClass(Character name, String originCode, String destinationCode, String airlineCode) {
        for(SeatClass seatClassEntry : seatClassesList) {
            if(seatClassEntry.getName().equals(name) && seatClassEntry.getOriginCode().equals(originCode) &&
                seatClassEntry.getDestinationCode().equals(destinationCode) && seatClassEntry.getAirlineCode().equals(airlineCode))
                return seatClassEntry;
        }
        return null;
    }
    public void updateSeatClass(SeatClass seatClass) {
        for(SeatClass seatClassEntry : seatClassesList) {
            if(seatClassEntry.equals(seatClass)) {
               seatClassEntry.setAdultPrice(seatClass.getAdultPrice());
               seatClassEntry.setChildPrice(seatClass.getChildPrice());
               seatClassEntry.setInfantPrice(seatClass.getInfantPrice());
               seatClassEntry.setLastUpdateDate(seatClass.getLastUpdateDate());
               return;
            }
        }
        return;
    }

    public ArrayList<SeatClass> getSeatClasses() {
        return seatClassesList;
    }
}