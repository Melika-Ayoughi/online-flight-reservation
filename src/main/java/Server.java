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
        getFlightList();
        getPrice();

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

    private static void handleReserve(){

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
                handleReserve();
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
