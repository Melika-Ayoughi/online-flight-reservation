//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.StringTokenizer;
//
///**
// * Created by Ali_Iman on 2/10/17.
// */
//
//public class Server {
//
//    // ******************************************************************************************************
//    //  public because of "Test"
//    public static Socket helperServerSocket;
//    private static BufferedReader inHelperServer;
//    private static PrintWriter outHelperServer;
//
//    private static ServerSocket serverSocket;
//    private static Socket clientSocket;
//    private static BufferedReader inClient;
//    private static PrintWriter outClient;
//
//    private static List<Flight> flightList = new ArrayList<Flight>();
//    private static List<Reservation>  reservationList = new ArrayList<Reservation>();
//
//    // ******************************************************************************************************
//    //  created because of "Test"
//    public static ArrayList<Integer> testPrices = new ArrayList<Integer>();
//
//
//    // ******************************************************************************************************
//    //  public because of "Test"
//    public static void connectToHelperServer(String HelperIP, int HelperPort) throws IOException {
//        helperServerSocket = new Socket(HelperIP, HelperPort);
//        inHelperServer = new BufferedReader(new InputStreamReader(helperServerSocket.getInputStream()));
//        outHelperServer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(helperServerSocket.getOutputStream())), true);
//    }
//
//    // ******************************************************************************************************
//    //  created because of "Test"
//    public static void setOutClient(PrintWriter outClientParameter){
//        outClient = outClientParameter;
//    }
//
//
//
//    private static void listenToClient(int Port) throws IOException {
//        serverSocket = new ServerSocket(Port);
//        clientSocket = serverSocket.accept();
//        inClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//        outClient = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
//    }
//
//
//
//    // ******************************************************************************************************
//    //  public because of "Test"
//    public static void handleSearch(String request) throws IOException {
//
//        List<String> requestFields = Arrays.asList(request.split("\\s* \\s*"));
//
//        if(requestFields.size() != 7) {
//            outClient.println("inclient - format error - search format is wrong.");
//            return;
//        }
//
//        String srcCode=requestFields.get(1);
//        String destCode=requestFields.get(2);
//        String date=requestFields.get(3);
//        String adultCount=requestFields.get(4);
//        String childCount=requestFields.get(5);
//        String infantCount=requestFields.get(6);
//
//        String avRequest = "AV " + srcCode + " " + destCode + " " + date;
//
//        ArrayList<Flight> resultFlights = getFlightList(avRequest);
//        getPrice(resultFlights);
//
//        // *************************************************************************************************************
//
//        ArrayList<ArrayList<Boolean> > enoughSizeSeatClass = new ArrayList<ArrayList<Boolean> >();
//        ArrayList<Boolean> enoughSizeFlight = new ArrayList<Boolean>();
//        int sumCount = Integer.parseInt(adultCount)+Integer.parseInt(childCount)+Integer.parseInt(infantCount);
//
//        for(int i=0; i<resultFlights.size(); i+=1) {
//            ArrayList<Boolean> line = new ArrayList<Boolean>();
//            Boolean tmp = false;
//            for(int j=0; j<resultFlights.get(i).getSeatClasses().size(); j+=1) {
//                char sizeLeft = resultFlights.get(i).getSeatClasses().get(j).getLeftNumber();
//                if(sumCount > 9 || (sizeLeft=='C' && sumCount!=0))
//                    line.add(false);
//                else if(sizeLeft=='A') {
//                    line.add(true);
//                    tmp = true;
//                }
//                else {
//                    line.add((Character.getNumericValue(sizeLeft) >= sumCount));
//                    if((Character.getNumericValue(sizeLeft) >= sumCount))
//                        tmp = true;
//                }
//            }
//            enoughSizeSeatClass.add(line);
//            enoughSizeFlight.add(tmp);
//        }
//
//        // *************************************************************************************************************
//
//        Boolean firstTime = true;
//        testPrices.clear();
//
//        for(int i=0; i<resultFlights.size(); i+=1) {
//            if(!enoughSizeFlight.get(i))
//                continue;
//            if(!firstTime) {
//                outClient.println("***");
//            }
//
//            firstTime = false;
//            outClient.println("Flight: " + resultFlights.get(i).getAirlineCode() + " " + resultFlights.get(i).getFlightNumber() + " Departure: "
//                                         + resultFlights.get(i).getDepartureTime().substring(0,2) + ":" + resultFlights.get(i).getDepartureTime().substring(2,4)
//                                         + " Arrival: " + resultFlights.get(i).getArrivalTime().substring(0,2) + ":" + resultFlights.get(i).getArrivalTime().substring(2,4)
//                                         + " Airplane: " + resultFlights.get(i).getAirplaneModel());
//            for (int j = 0; j < resultFlights.get(i).getSeatClasses().size(); j += 1) {
//                if(!enoughSizeSeatClass.get(i).get(j))
//                    continue;
//                int totalPrice = resultFlights.get(i).getSeatClasses().get(j).getAdultPrice()*Integer.parseInt(adultCount) +
//                                 resultFlights.get(i).getSeatClasses().get(j).getChildPrice()*Integer.parseInt(childCount) +
//                                 resultFlights.get(i).getSeatClasses().get(j).getInfantPrice()*Integer.parseInt(infantCount);
//                outClient.println("Class: " + resultFlights.get(i).getSeatClasses().get(j).getName() + " Price: " + totalPrice);
//                testPrices.add(totalPrice);
//            }
//        }
//
//        if(firstTime)
//            outClient.println("");
//
//        // *************************************************************************************************************
//
//    }
//
//
//
//    private static ArrayList<Flight> getFlightList(String avRequest) throws IOException {
//
//        ArrayList<Flight> resultFlights = new ArrayList<Flight>();
//        ArrayList<String> response = new ArrayList<String>();
//
//        outHelperServer.println(avRequest);
//
//        try {
//            while (true) {
//                response.add(inHelperServer.readLine());
//                if (!inHelperServer.ready()) {
//                    response.clear();
//                    break;
//                }
//                response.add(inHelperServer.readLine());
//                if (!inHelperServer.ready())
//                    break;
//            }
//        }
//        catch (Exception ex) {
//            outClient.println("inHelperServer - socket - Error in reading AV response!");
//        }
//
//        String firstLine;
//        String secondLine;
//
//        String airlineCode;
//        String flightNo;
//        String date;
//        String srcCode;
//        String destCode;
//        String depTime;
//        String arrTime;
//        String planeModel;
//
//
//        for(int i=0; i<response.size(); i+=2){
//
//            firstLine = response.get(i);
//            secondLine = response.get(i+1);
//            List<String> firstLineFields = Arrays.asList(firstLine.split("\\s* \\s*"));
//
//            if(firstLineFields.size() != 8) {
//                System.out.println("inHelperServer - format error - AV response from helper server format is wrong - flight!!!");
//                outClient.println("inHelperServer - format error - AV response from helper server format is wrong - flight!!!");
//            }
//
//            airlineCode = firstLineFields.get(0);
//            flightNo = firstLineFields.get(1);
//            date = firstLineFields.get(2);
//            srcCode = firstLineFields.get(3);
//            destCode = firstLineFields.get(4);
//            depTime = firstLineFields.get(5);
//            arrTime = firstLineFields.get(6);
//            planeModel = firstLineFields.get(7);
//
//            ArrayList<SeatClass> seatClasses = new ArrayList<SeatClass>();
//            char SeatClassName, SeatClassLeftNumber;
//
//            List<String> secondLineFields = Arrays.asList(secondLine.split("\\s* \\s*"));
//            char[] chars;
//
//            for(int j=0; j<secondLineFields.size(); j++) {
//                chars = secondLineFields.get(j).toCharArray();
//                SeatClassName = chars[0];
//                SeatClassLeftNumber = chars[1];
//                seatClasses.add(new SeatClass(SeatClassName, SeatClassLeftNumber));
//            }
//
//            for (Flight flight : flightList) {
//                if(flight.getFlightNumber().equals(flightNo) && flight.getSrcCode().equals(srcCode) &&
//                   flight.getDestCode().equals(destCode) && flight.getDate().equals(date))
//                    flightList.remove(flight);
//            }
//
//            Flight flight = new Flight(airlineCode, flightNo, date, srcCode, destCode, depTime, arrTime, planeModel, seatClasses);
//            flightList.add(flight);
//            resultFlights.add(flight);
//        }
//
//        return resultFlights;
//    }
//
//    private static void getPrice(ArrayList<Flight> resultFlights) throws IOException {
//        String prices;
//
//        for(int i=0; i<resultFlights.size(); i++)  {
//            for(int j=0; j<resultFlights.get(i).getSeatClasses().size(); j++){
//
//                System.out.println("PRICE "+ resultFlights.get(i).getSrcCode() + " "
//                        + resultFlights.get(i).getDestCode() + " " + resultFlights.get(i).getAirlineCode() + " "
//                        + resultFlights.get(i).getSeatClasses().get(j).getName());
//
//                outHelperServer.println("PRICE "+ resultFlights.get(i).getSrcCode() + " "
//                        + resultFlights.get(i).getDestCode() + " " + resultFlights.get(i).getAirlineCode() + " "
//                        + resultFlights.get(i).getSeatClasses().get(j).getName());
//
//                try {
//                    prices = inHelperServer.readLine();
//
//                    List<String> pricesFields = Arrays.asList(prices.split("\\s* \\s*"));
//
//                    if(pricesFields.size() != 3) {
//                        System.out.println("inHelperServer - format error - PRICE response from helper server format is wrong!!!");
//                        outClient.println("inHelperServer - format error - PRICE response from helper server format is wrong!!!");
//                    }
//
//                    resultFlights.get(i).getSeatClasses().get(j).setAdultPrice(Integer.parseInt(pricesFields.get(0)));
//                    resultFlights.get(i).getSeatClasses().get(j).setChildPrice(Integer.parseInt(pricesFields.get(1)));
//                    resultFlights.get(i).getSeatClasses().get(j).setInfantPrice(Integer.parseInt(pricesFields.get(2)));
//                }
//                catch (Exception ex) {
//                    System.out.println("inHelperServer - socket - Error in reading Price response!");
//                    outClient.println("inHelperServer - socket - Error in reading Price response!");
//                    ex.printStackTrace();
//                }
//            }
//        }
//
//    }
//
//    private static void handleReserve(String request) throws IOException {
//
//        String srcCode;
//        String destCode;
//        String date;
//        String airlineCode;
//        String flightNumber;
//        String seatClassName;
//        String adultCount;
//        String childCount;
//        String infantCount;
//
//        List<String> requestFields = Arrays.asList(request.split("\\s* \\s*"));
//
//        if(requestFields.size() != 10) {
//            outClient.println("inclient - format error - reserve format is wrong.");
//            return;
//        }
//
//        srcCode = requestFields.get(1);
//        destCode = requestFields.get(2);
//        date = requestFields.get(3);
//        airlineCode = requestFields.get(4);
//        flightNumber = requestFields.get(5);
//        seatClassName = requestFields.get(6);
//        adultCount = requestFields.get(7);
//        childCount = requestFields.get(8);
//        infantCount = requestFields.get(9);
//
//        int passengerCount = Integer.parseInt(adultCount) + Integer.parseInt(childCount) + Integer.parseInt(infantCount);
//
//        String firstName;
//        String surName;
//        String nationalId;
//
//        ArrayList<Passenger> passengersList = new ArrayList<Passenger>();
//
//        for(int i=0; i<passengerCount; i+=1) {
//            try {
//                String passengerReq = inClient.readLine();
//                List<String> passengerFields = Arrays.asList(passengerReq.split("\\s* \\s*"));
//
//                if(passengerFields.size() != 3) {
//                    outClient.println("inclient - format error - passenger format in reserve request is wrong.");
//                    return;
//                }
//                firstName = passengerFields.get(0);
//                surName = passengerFields.get(1);
//                nationalId = passengerFields.get(2);
//                passengersList.add(new Passenger(firstName, surName, nationalId));
//            }
//            catch (Exception ex) {
//                System.out.println("inClinet - socket - error reading passenger info from inClient");
//                ex.printStackTrace();
//            }
//        }
//
//        Reservation reservation = new Reservation(srcCode,destCode,date,airlineCode,flightNumber,seatClassName,adultCount,childCount,infantCount, passengersList);
//
//        System.out.println("RES "+reservation.toString());
//        outHelperServer.println("RES "+reservation.toString());
//
//        for(int i=0; i<reservation.getPassengerList().size(); i++){
//            System.out.println(reservation.getPassengerList().get(i).toString());
//            outHelperServer.println(reservation.getPassengerList().get(i).toString());
//        }
//
//        try {
//            String RESResponse = inHelperServer.readLine();
//            if(RESResponse==null) {
//                outClient.println("inHelperServer - probably connection lost  - RES Response is null!");
//                return;
//            }
//
//            List<String> RESResponseFields = Arrays.asList(RESResponse.split("\\s* \\s*"));
//
//            if(RESResponseFields.size() != 4) {
//                outClient.println("inHelperServer - format error - RES result format is wrong!!!");
//                return;
//            }
//
//            reservation.setToken(RESResponseFields.get(0));
//            int totalPrice = reservation.getTotalPrice(RESResponseFields.get(1),RESResponseFields.get(2),RESResponseFields.get(3));
//
//            outClient.println(reservation.getToken() + " " + totalPrice);
//            reservationList.add(reservation);
//        }
//
//        catch (Exception ex){
//            System.out.println("inHelperServer - socket - error in reading RES response from server");
//            ex.printStackTrace();
//        }
//
//    }
//
//    private static void handleFinalize(String request) throws IOException {
//
//        List<String> finalizeFields = Arrays.asList(request.split("\\s* \\s*"));
//
//        if(finalizeFields.size() != 2) {
//            outClient.println("inclient - format error - finalize format is wrong.");
//            return;
//        }
//        String token = finalizeFields.get(1);
//
//        Reservation reservation = null;
//
//        // find the corresponding reservation for this token
//        for (Reservation res : reservationList) {
//            if (res.getToken().equals(token)) {
//                reservation = res;
//                break;
//            }
//        }
//
//        if(reservation == null) {
//            outClient.println("inclient - logic error - there is no corresponding reservation with this Token");
//            return;
//        }
//
//        outHelperServer.println("FIN " + token);
//        String referenceCode = "";
//
//        try{
//            referenceCode = inHelperServer.readLine();
//        }
//        catch (Exception ex) {
//            outClient.println("inHelperServer - socket - error reading reference code from helper server");
//        }
//
//        reservation.setReferenceCode(referenceCode);
//        ArrayList<String> ticketNumbersList = new ArrayList<String>();
//
//        for(int i=0; i< reservation.getPassengerList().size(); i++){
//            ticketNumbersList.add(inHelperServer.readLine());
//        }
//
//        reservation.setTicketNumbersList(ticketNumbersList);
//
//        String avRequest = "AV " + reservation.getSrcCode() + " " + reservation.getDestCode() + " " + reservation.getDate();
//        ArrayList<Flight> resultFlight = getFlightList(avRequest);
//
//        String depTime ="";
//        String arrTime ="";
//        String airplaneModel ="";
//
//        for (Flight flight : resultFlight) {
//            if(flight.getFlightNumber().equals(reservation.getFlightNumber()) && flight.getSrcCode().equals(reservation.getSrcCode()) &&
//                    flight.getDestCode().equals(reservation.getDestCode()) && flight.getDate().equals(reservation.getDate())){
//                depTime = flight.getDepartureTime();
//                arrTime = flight.getArrivalTime();
//                airplaneModel = flight.getAirplaneModel();
//                break;
//            }
//
//        }
//        ArrayList<String> tickets = reservation.getTickets();
//
//        for(int i=0; i< reservation.getPassengerList().size(); i++){
//            outClient.println(tickets.get(i) + " " + depTime + " " + arrTime + " " + airplaneModel);
//        }
//
//    }
//
//    public static void main(String[] args) throws IOException {
//
//        connectToHelperServer(args[1], Integer.parseInt(args[2]));
//        listenToClient(Integer.parseInt(args[0]));
//
//        while (true) {
//
//            String request = "";
//
//            try {
//                request = inClient.readLine();
//            }
//            catch (Exception ex) {
//                outClient.println("inClient - socket - error reading request from inClient");
//            }
//
//            if(request.startsWith("search")){
//                handleSearch(request);
//            }
//            else if(request.startsWith("reserve")){
//                handleReserve(request);
//            }
//            else if(request.startsWith("finalize")){
//                handleFinalize(request);
//            }
//            else if(request.startsWith("quit")) {
//                break;
//            }
//            else {
//                outClient.println("No Such Command!");
//            }
//        }
//
//        clientSocket.close();
//        helperServerSocket.close();
//    }
//}