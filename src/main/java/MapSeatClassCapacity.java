/**
 * Created by Ali_Iman on 3/2/17.
 */
public class MapSeatClassCapacity {
    private SeatClass seatClass;
    private Integer capacity;

    public MapSeatClassCapacity(SeatClass seatClass, Integer capacity) {
        this.seatClass = seatClass;
        this.capacity = capacity;
    }

    public void setSeatClass(SeatClass seatClass) {
        this.seatClass = seatClass;
    }
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }
    public Integer getCapacity() {
        return capacity;
    }
}
