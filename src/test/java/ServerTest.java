//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.PrintWriter;
//import java.util.ArrayList;
//
//import static org.junit.Assert.*;
//
///**
// * Created by Ali_Iman on 2/13/17.
// */
//public class ServerTest {
//
//    private Server server;
//    ArrayList<Integer> Actual = new ArrayList<Integer>();
//
//
//    @Before
//    public void setUp() throws Exception {
//        server = new Server();
//        Actual.clear();
//        server.connectToHelperServer("188.166.78.119", 8081);
//        server.setOutClient(new PrintWriter(System.out));
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        server.helperServerSocket.close();
//    }
//
//    @Test
//    public void handleSearch() throws Exception {
//        ArrayList<Integer> Actual = new ArrayList<Integer>();
//
//
//        /*
//            search THR MHD 05Feb 2 2 5
//
//            Flight: IR 452 Departure: 17:40 Arrival: 18:50 Airplane: M80
//            Class: M Price: 24000
//            ***
//            Flight: IR 822 Departure: 07:30 Arrival: 08:40 Airplane: 351
//            Class: F Price: 15000
//         */
//
//
//        String req1 = "search THR MHD 05Feb 2 2 5";
//        server.handleSearch(req1);
//        Actual.clear();
//        Actual.add(24000);
//        Actual.add(15000);
//        assertEquals(server.testPrices, Actual);
//
//
//
//        /*
//            search THR MHD 05Feb 1 0 1
//
//            Flight: IR 452 Departure: 17:40 Arrival: 18:50 Airplane: M80
//            Class: Y Price: 4000
//            Class: M Price: 6000
//            ***
//            Flight: IR 822 Departure: 07:30 Arrival: 08:40 Airplane: 351
//            Class: F Price: 4000
//            Class: Y Price: 4000
//         */
//
//        String req2 = "search THR MHD 05Feb 1 0 1";
//        server.handleSearch(req2);
//        Actual.clear();
//        Actual.add(4000);
//        Actual.add(6000);
//        Actual.add(4000);
//        Actual.add(4000);
//        assertEquals(server.testPrices, Actual);
//
//
//        /*
//            search THR MHD 05Feb 1 2 3
//
//            Flight: IR 452 Departure: 17:40 Arrival: 18:50 Airplane: M80
//            Class: Y Price: 10000
//            Class: M Price: 16000
//            ***
//            Flight: IR 822 Departure: 07:30 Arrival: 08:40 Airplane: 351
//            Class: F Price: 10000
//         */
//
//        String req3 = "search THR MHD 05Feb 1 2 3";
//        server.handleSearch(req3);
//        Actual.clear();
//        Actual.add(10000);
//        Actual.add(16000);
//        Actual.add(10000);
//        assertEquals(server.testPrices, Actual);
//    }
//
//}