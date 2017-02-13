import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ali_Iman on 2/13/17.
 */
public class ReservationTest {

    Reservation reservation1;
    Reservation reservation2;
    Reservation reservation3;
    Reservation reservation4;
    Reservation reservation5;

    @Before
    public void setUp() throws Exception {
        reservation1 = new Reservation("THR", "MHD", "05Feb", "IR", "452", "M", "2", "0", "0");
        reservation2 = new Reservation("THR", "MHD", "05Feb", "IR", "452", "M", "0", "6", "0");
        reservation3 = new Reservation("THR", "MHD", "05Feb", "IR", "452", "M", "0", "0", "3");
        reservation4 = new Reservation("THR", "MHD", "05Feb", "IR", "452", "M", "2", "0", "4");
        reservation5 = new Reservation("THR", "MHD", "05Feb", "IR", "452", "M", "7", "3", "6");
    }

    @After
    public void tearDown() throws Exception {


    }

    @Test
    public void getTotalPrice() throws Exception {
        assertEquals(reservation1.getTotalPrice("1000", "100", "10") , 2000);
        assertEquals(reservation1.getTotalPrice("10", "9", "8") , 20);
        assertEquals(reservation2.getTotalPrice("1000", "100", "10") , 600);
        assertEquals(reservation2.getTotalPrice("10", "9", "8") , 54);
        assertEquals(reservation3.getTotalPrice("1000", "100", "10") , 30);
        assertEquals(reservation3.getTotalPrice("10", "9", "8") , 24);
        assertEquals(reservation4.getTotalPrice("1000", "100", "10") , 2040);
        assertEquals(reservation4.getTotalPrice("10", "9", "8") , 52);
        assertEquals(reservation5.getTotalPrice("1000", "100", "10") , 7360);
        assertEquals(reservation5.getTotalPrice("10", "9", "8") , 145);
    }
}