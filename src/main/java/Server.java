import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Ali_Iman on 2/10/17.
 */
public class Server {

    private static Socket helperServerSocket;
    private static BufferedReader inHelperServer;
    private static PrintWriter outHelperServer;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static BufferedReader inClient;
    private static PrintWriter outClient;

    private static List<Flight> flightList = new ArrayList<Flight>();

    private static void connectToHelperServer(String HelperIP, int HelperPort) throws IOException {
        helperServerSocket = new Socket(HelperIP, HelperPort);
        inHelperServer = new BufferedReader(new InputStreamReader(helperServerSocket.getInputStream()));
        outHelperServer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(helperServerSocket.getOutputStream())), true);
    }

    private static void listenToClient(int Port) throws IOException {
        serverSocket = new ServerSocket(Port);
        clientSocket = serverSocket.accept();
        inClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outClient = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
    }

    private static void handleSearch(String request) throws IOException {
        String srcCode="";
        String destCode="";
        String date="";
        String adultCount="";
        String childCount="";
        String infantCount="";

        StringTokenizer st = new StringTokenizer(request," ");
        try {
            st.nextToken();
            srcCode = st.nextToken();
            destCode = st.nextToken();
            date = st.nextToken();
            adultCount = st.nextToken();
            childCount = st.nextToken();
            infantCount = st.nextToken();
        }
        catch (Exception ex){
            System.out.println("Search Command Not Correct!");
            ex.printStackTrace();
        }


        outHelperServer.println("AV " + srcCode + " " + destCode + " " + date);

        flightList.clear();
        getFlightList();
        getPrice();

        // FlightList is ready

        // *************************************************************************************************************

        ArrayList<ArrayList<Boolean> > enoughSizeSeatClass = new ArrayList<ArrayList<Boolean> >();
        ArrayList<Boolean> enoughSizeFlight = new ArrayList<Boolean>();
        int sumCount = Integer.parseInt(adultCount)+Integer.parseInt(childCount)+Integer.parseInt(infantCount);

        for(int i=0; i<flightList.size(); i+=1) {
            ArrayList<Boolean> line = new ArrayList<Boolean>();
            Boolean tmp = false;
            for(int j=0; j<flightList.get(i).getSeatClasses().size(); j+=1) {
                char sizeLeft = flightList.get(i).getSeatClasses().get(j).getLeftNumber();
                if(sumCount > 9 || sizeLeft=='C')
                    line.add(false);
                else if(sizeLeft=='A') {
                    line.add(true);
                    tmp = true;
                }
                else {
                    line.add((Character.getNumericValue(sizeLeft) >= sumCount));
                    if((Character.getNumericValue(sizeLeft) >= sumCount))
                        tmp = true;
                }
            }
            enoughSizeSeatClass.add(line);
            enoughSizeFlight.add(tmp);
        }

        // *************************************************************************************************************

        Boolean firstTime = true;

        for(int i=0; i<flightList.size(); i+=1) {
            if(!enoughSizeFlight.get(i))
                continue;
            if(!firstTime) {
                outClient.println("***");
            }

            firstTime = false;
            outClient.println("Flight: " + flightList.get(i).getAirlineCode() + " " + flightList.get(i).getFlightNumber() + " Departure: "
                                         + flightList.get(i).getDepartureTime() + " Arrival: " + flightList.get(i).getArrrivalTime()
                                         + " Airplane: " + flightList.get(i).getAirplaneModel());
            for (int j = 0; j < flightList.get(i).getSeatClasses().size(); j += 1) {
                if(!enoughSizeSeatClass.get(i).get(j))
                    continue;
                int totalPrice = flightList.get(i).getSeatClasses().get(j).getAdultPrice()*Integer.parseInt(adultCount) +
                                 flightList.get(i).getSeatClasses().get(j).getChildPrice()*Integer.parseInt(childCount) +
                                 flightList.get(i).getSeatClasses().get(j).getInfantPrice()*Integer.parseInt(infantCount);
                outClient.println("Class: " + flightList.get(i).getSeatClasses().get(j).getName() + " Price: " + totalPrice);
            }
        }

        // *************************************************************************************************************

    }

    private static void getFlightList() throws IOException {
        ArrayList<String> response = new ArrayList<String>();

        while(true){
            response.add(inHelperServer.readLine());
            response.add(inHelperServer.readLine());
            if(!inHelperServer.ready())
                break;
        }

        String firstLine = "";
        String secondLine = "";
        String airlineCode ="";
        String flightNo ="";
        String date = "";
        String srcCode = "";
        String destCode ="";
        String depTime ="";
        String arrTime = "";
        String planeModel ="";


        for(int i=0; i<response.size(); i+=2){
            firstLine = response.get(i);
            secondLine = response.get(i+1);

            StringTokenizer st2 = new StringTokenizer(firstLine," ");

            try {
                airlineCode = st2.nextToken();
                flightNo = st2.nextToken();
                date = st2.nextToken();
                srcCode = st2.nextToken();
                destCode = st2.nextToken();
                depTime = st2.nextToken();
                arrTime = st2.nextToken();
                planeModel = st2.nextToken();
            }
            catch (Exception ex){
                System.out.println("Flight List Response Not Correct From Helper Server!");
                ex.printStackTrace();
            }



            ArrayList<SeatClass> seatClasses = new ArrayList<SeatClass>();
            char SeatClassName, SeatClassLeftNumber;

            StringTokenizer st3 = new StringTokenizer(secondLine," ");
            char[] chars;

            while(st3.hasMoreTokens()) {
                try {
                    chars = st3.nextToken().toCharArray();
                    SeatClassName = chars[0];
                    SeatClassLeftNumber = chars[1];
                    seatClasses.add(new SeatClass(SeatClassName, SeatClassLeftNumber));
                } catch (Exception ex) {
                    System.out.println("Flight List Response Not Correct From Helper Server!");
                    ex.printStackTrace();
                }
            }

            flightList.add(new Flight(airlineCode, flightNo, date, srcCode, destCode, depTime, arrTime, planeModel, seatClasses));
        }
    }

    private static void getPrice() throws IOException {
        String prices ="";

        for(int i=0; i<flightList.size(); i++){
            for(int j=0; j<flightList.get(i).getSeatClasses().size(); j++){
                System.out.println("PRICE "+ flightList.get(i).getSrcCode() + " "
                        + flightList.get(i).getDestCode() + " " + flightList.get(i).getAirlineCode() + " "
                        + flightList.get(i).getSeatClasses().get(j).getName());

                outHelperServer.println("PRICE "+ flightList.get(i).getSrcCode() + " "
                        + flightList.get(i).getDestCode() + " " + flightList.get(i).getAirlineCode() + " "
                        + flightList.get(i).getSeatClasses().get(j).getName());

                prices = inHelperServer.readLine();
                StringTokenizer st = new StringTokenizer(prices," ");
                flightList.get(i).getSeatClasses().get(j).setAdultPrice(Integer.parseInt(st.nextToken()));
                flightList.get(i).getSeatClasses().get(j).setChildPrice(Integer.parseInt(st.nextToken()));
                flightList.get(i).getSeatClasses().get(j).setInfantPrice(Integer.parseInt(st.nextToken()));
            }
        }


    }

    private static void handleReserve(String request) throws IOException {
        String srcCode="";
        String destCode="";
        String date="";
        String airlineCode ="";
        String flightNumber = "";
        String seatClassName = "";
        String adultCount="";
        String childCount="";
        String infantCount="";

        StringTokenizer st = new StringTokenizer(request," ");
        Reservation reservation = new Reservation();

        try {
            // ignore reserve
            st.nextToken();
            srcCode = st.nextToken();
            destCode = st.nextToken();
            date = st.nextToken();
            airlineCode = st.nextToken();
            flightNumber = st.nextToken();
            seatClassName = st.nextToken();
            adultCount = st.nextToken();
            childCount = st.nextToken();
            infantCount = st.nextToken();
            reservation = new Reservation(srcCode,destCode,date,airlineCode,flightNumber,seatClassName,adultCount,childCount,infantCount);
        }
        catch (Exception ex){
            System.out.println("Reserve Command Not Correct!");
            ex.printStackTrace();
        }

        int passengerCount = Integer.parseInt(adultCount) + Integer.parseInt(childCount) + Integer.parseInt(infantCount);

        String firstName = "";
        String surName = "";
        String nationalId = "";

        ArrayList<Passenger> passengersList = new ArrayList<Passenger>();

        for(int i=0; i<passengerCount; i+=1) {
            try {
                String passengerReq = inClient.readLine();
                StringTokenizer st2 = new StringTokenizer(passengerReq," ");
                firstName = st2.nextToken();
                surName = st2.nextToken();
                nationalId = st2.nextToken();
                passengersList.add(new Passenger(firstName, surName, nationalId));
            }
            catch (Exception ex) {
                System.out.println("Passenger Info Not Correct!");
                ex.printStackTrace();
            }
        }
        reservation.setPassengerList(passengersList);

        outHelperServer.println("RES "+reservation.toString());
        for(int i=0; i<reservation.getPassengerList().size(); i++){
            outHelperServer.println(reservation.getPassengerList().get(i).toString());
        }

        String response = inHelperServer.readLine();
        StringTokenizer st3 = new StringTokenizer(response, " ");

        reservation.setToken(st3.nextToken());
        outClient.println(reservation.getToken() + " " + reservation.getTotalPrice(st3.nextToken(),st3.nextToken(),st3.nextToken()));
        System.out.println(response);
    }

    private static void handleFinalize(){

    }

    public static void main(String[] args) throws IOException {

        connectToHelperServer("188.166.78.119", 8081);
        listenToClient(9090);

        while (true) {
            String request = inClient.readLine();
            if(request.startsWith("search")){
                handleSearch(request);
            }
            else if(request.startsWith("reserve")){
                handleReserve(request);
            }
            else if(request.startsWith("finalize")){
                handleFinalize();
            }
            else if(request.startsWith("quit")) {
                break;
            }
            else {
                outClient.println("No Such Command!");
            }
        }

        helperServerSocket.close();
        clientSocket.close();
    }
}
