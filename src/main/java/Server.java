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
    private static List<Reservation>  reservationList = new ArrayList<Reservation>();

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


        System.out.println("AV " + srcCode + " " + destCode + " " + date);
        outHelperServer.println("AV " + srcCode + " " + destCode + " " + date);


        ArrayList<Flight> resultFlight = getFlightList();

        getPrice();

        // FlightList is ready

        // *************************************************************************************************************

        ArrayList<ArrayList<Boolean> > enoughSizeSeatClass = new ArrayList<ArrayList<Boolean> >();
        ArrayList<Boolean> enoughSizeFlight = new ArrayList<Boolean>();
        int sumCount = Integer.parseInt(adultCount)+Integer.parseInt(childCount)+Integer.parseInt(infantCount);

        for(int i=0; i<resultFlight.size(); i+=1) {
            ArrayList<Boolean> line = new ArrayList<Boolean>();
            Boolean tmp = false;
            for(int j=0; j<resultFlight.get(i).getSeatClasses().size(); j+=1) {
                char sizeLeft = resultFlight.get(i).getSeatClasses().get(j).getLeftNumber();
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

        for(int i=0; i<resultFlight.size(); i+=1) {
            if(!enoughSizeFlight.get(i))
                continue;
            if(!firstTime) {
                outClient.println("***");
            }

            firstTime = false;
            outClient.println("Flight: " + resultFlight.get(i).getAirlineCode() + " " + resultFlight.get(i).getFlightNumber() + " Departure: "
                                         + resultFlight.get(i).getDepartureTime().substring(0,2) + ":" + resultFlight.get(i).getDepartureTime().substring(2,4)
                                         + " Arrival: " + resultFlight.get(i).getArrrivalTime().substring(0,2) + ":" + resultFlight.get(i).getArrrivalTime().substring(2,4)
                                         + " Airplane: " + resultFlight.get(i).getAirplaneModel());
            for (int j = 0; j < resultFlight.get(i).getSeatClasses().size(); j += 1) {
                if(!enoughSizeSeatClass.get(i).get(j))
                    continue;
                int totalPrice = resultFlight.get(i).getSeatClasses().get(j).getAdultPrice()*Integer.parseInt(adultCount) +
                                 resultFlight.get(i).getSeatClasses().get(j).getChildPrice()*Integer.parseInt(childCount) +
                                 resultFlight.get(i).getSeatClasses().get(j).getInfantPrice()*Integer.parseInt(infantCount);
                outClient.println("Class: " + resultFlight.get(i).getSeatClasses().get(j).getName() + " Price: " + totalPrice);
            }
        }

        // *************************************************************************************************************

    }

    private static ArrayList<Flight> getFlightList() throws IOException {

        ArrayList<Flight> resultFlights = new ArrayList<Flight>();
        ArrayList<String> response = new ArrayList<String>();

        while(true) {
            response.add(inHelperServer.readLine());
            if(!inHelperServer.ready()) {
                response.clear();
                break;
            }
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

            for (Flight flight : flightList) {
                if(flight.getFlightNumber().equals(flightNo) && flight.getSrcCode().equals(srcCode) &&
                   flight.getDestCode().equals(destCode) && flight.getDate().equals(date))
                    flightList.remove(flight);
            }

            flightList.add(new Flight(airlineCode, flightNo, date, srcCode, destCode, depTime, arrTime, planeModel, seatClasses));
            resultFlights.add(new Flight(airlineCode, flightNo, date, srcCode, destCode, depTime, arrTime, planeModel, seatClasses));
        }

        return resultFlights;
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

        System.out.println("RES "+reservation.toString());
        outHelperServer.println("RES "+reservation.toString());
        for(int i=0; i<reservation.getPassengerList().size(); i++){
            System.out.println(reservation.getPassengerList().get(i).toString());
            outHelperServer.println(reservation.getPassengerList().get(i).toString());
        }

        try {
            String response = inHelperServer.readLine();
            while(response == null)
                response = inHelperServer.readLine();

//            System.out.println("response: "+response);
            StringTokenizer st3 = new StringTokenizer(response, " ");

            reservation.setToken(st3.nextToken());
            outClient.println(reservation.getToken() + " " + reservation.getTotalPrice(st3.nextToken(),st3.nextToken(),st3.nextToken()));
            reservationList.add(reservation);
//            System.out.println(response);
        }
        catch (Exception ex){
            System.out.println("error in string tokenizer");
            ex.printStackTrace();
        }

    }

    private static void handleFinalize(String request) throws IOException {
        StringTokenizer st = new StringTokenizer(request, " ");

        //ignore fanalize
        st.nextToken();
        String token = st.nextToken();

        Reservation reservation = new Reservation();

        // find the corresponding reservation for this token
        for (Reservation res : reservationList) {
            if (res.getToken().equals(token)) {
                reservation = res;
                break;
            }
        }

        outHelperServer.println("FIN " + token);
        String referenceCode = inHelperServer.readLine();
        reservation.setReferenceCode(referenceCode);
        ArrayList<String> ticketNumbersList = new ArrayList<String>();

        for(int i=0; i< reservation.getPassengerList().size(); i++){
            ticketNumbersList.add(inHelperServer.readLine());
        }

        reservation.setTicketNumbersList(ticketNumbersList);


        outHelperServer.println("AV " + reservation.getSrcCode() + " " + reservation.getDestCode() + " " + reservation.getDate());
        ArrayList<Flight> resultFlight = getFlightList();

        String depTime ="";
        String arrTime ="";
        String airplaneModel ="";

        for (Flight flight : resultFlight) {
            if(flight.getFlightNumber().equals(reservation.getFlightNumber()) && flight.getSrcCode().equals(reservation.getSrcCode()) &&
                    flight.getDestCode().equals(reservation.getDestCode()) && flight.getDate().equals(reservation.getDate())){
                depTime = flight.getDepartureTime();
                arrTime = flight.getArrrivalTime();
                airplaneModel = flight.getAirplaneModel();
                break;
            }

        }


        ArrayList<String> tickets = reservation.getTickets();
        for(int i=0; i< reservation.getPassengerList().size(); i++){
            outClient.println(tickets.get(i) + " " + depTime + " " + arrTime + " " + airplaneModel);
        }

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
                handleFinalize(request);
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
