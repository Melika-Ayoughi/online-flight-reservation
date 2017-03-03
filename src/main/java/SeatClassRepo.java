import java.util.ArrayList;

/**
 * Created by Ali_Iman on 3/3/17.
 */
public class SeatClassRepo {
    private static SeatClassRepo seatClassRepo;
    private ArrayList<SeatClass> seatClasses;

    private SeatClassRepo() { }
    public static SeatClassRepo getSeatClassRepo() {
        if(seatClassRepo == null)
            seatClassRepo = new SeatClassRepo();
        return seatClassRepo;
    }

    public void setSeatClasses(ArrayList<SeatClass> seatClasses) {
        this.seatClasses = seatClasses;
    }
    public ArrayList<SeatClass> getSeatClasses() {
        return seatClasses;
    }
}
