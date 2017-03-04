import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/3/17.
 */
public class SeatClassRepo {
    private static SeatClassRepo seatClassRepo;
    private ArrayList<SeatClass> seatClasses;

    private SeatClassRepo() { }
    public static SeatClassRepo getSeatClassRepo() {
        if(seatClassRepo == null) {
            seatClassRepo = new SeatClassRepo();
            seatClassRepo.seatClasses = new ArrayList<SeatClass>();
        }
        return seatClassRepo;
    }

    public ArrayList<SeatClass> getSeatClasses() {
        return seatClasses;
    }

    private SeatClass get(SeatClass seatClass) {
        for(SeatClass seatClassEntry : seatClasses)
            if(seatClassEntry.equals(seatClass))
                return seatClassEntry;
        return null;
    }

    private boolean alreadyExists(SeatClass seatClass) {
        for(SeatClass seatClassEntry : seatClasses)
            if(seatClassEntry.equals(seatClass))
                return true;
        return false;
    }

    private SeatClass update(SeatClass seatClass) {
        SeatClass seatClassEntry = get(seatClass);
        seatClassEntry.setAdultPrice(seatClass.getAdultPrice());
        seatClassEntry.setChildPrice(seatClass.getChildPrice());
        seatClassEntry.setInfantPrice(seatClass.getInfantPrice());
        return  seatClassEntry;
    }

    private SeatClass add(SeatClass seatClass) {
        seatClasses.add(seatClass);
        return seatClass;
    }

    public SeatClass store(SeatClass seatClass) {
        if (alreadyExists(seatClass))
            return update(seatClass);
        return add(seatClass);
    }
}