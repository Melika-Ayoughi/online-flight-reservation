import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ali_Iman on 3/1/17.
 */
public class CA1HelperServer extends OnlineFlightProvider {
    public CA1HelperServer(String helperServerIP, Integer helperServerPort) throws IOException {
        super(helperServerIP, helperServerPort);
    }

    public ArrayList<Flight> getFlightsList(String originCode, String destinationCode, String date) {
        ArrayList<Flight> flights = new ArrayList<Flight>();
        ArrayList<String> avResponse = new ArrayList<String>();
        String avRequest = "AV " + originCode + " " + destinationCode + " " + date;

        outHelperServer.println(avRequest);
        try {
            while (true) {
                avResponse.add(inHelperServer.readLine());
                if (!inHelperServer.ready()) {
                    avResponse.clear();
                    break;
                }
                avResponse.add(inHelperServer.readLine());
                if (!inHelperServer.ready())
                    break;
            }
        }
        catch (IOException ex) {
            System.out.println("inHelperServer - socket - Error in reading AV response!");
        }

        String firstLine, secondLine;
        String airlineCode, flightNo, srcCode, destCode, depTime, arrTime, planeModel;

        for(int i=0; i<avResponse.size(); i+=2){
            firstLine = avResponse.get(i);
            secondLine = avResponse.get(i+1);
            List<String> firstLineFields = Arrays.asList(firstLine.split("\\s* \\s*"));

            if(firstLineFields.size() != 8)
                System.out.println("inHelperServer - format error - AV response from helper server format is wrong - flight!!!");

            airlineCode = firstLineFields.get(0);
            flightNo = firstLineFields.get(1);
            date = firstLineFields.get(2);
            srcCode = firstLineFields.get(3);
            destCode = firstLineFields.get(4);
            depTime = firstLineFields.get(5);
            arrTime = firstLineFields.get(6);
            planeModel = firstLineFields.get(7);

            ArrayList<SeatClass> seatClasses = new ArrayList<SeatClass>();
            char SeatClassName, SeatClassLeftNumber;
            List<String> secondLineFields = Arrays.asList(secondLine.split("\\s* \\s*"));
            char[] chars;

            for(int j=0; j<secondLineFields.size(); j++) {
                chars = secondLineFields.get(j).toCharArray();
                SeatClassName = chars[0];
                SeatClassLeftNumber = chars[1];
                seatClasses.add(new SeatClass(SeatClassName, SeatClassLeftNumber));
            }
            flights.add(new Flight(airlineCode, flightNo, date, srcCode, destCode, depTime, arrTime, planeModel, seatClasses));
        }
        return flights;
    }

    public ArrayList<Integer> getPricesList(Flight flight) {
        return new ArrayList<Integer>();
    }

    public ReserveValueObject reserve(Flight flight) {
        return new ReserveValueObject("1", 1,2,3);
    }

    public FinalizeValueObject finalize(Flight flight) {
        return new FinalizeValueObject("khar", new ArrayList<String>());
    }
}
