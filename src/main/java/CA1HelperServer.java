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
            while(true) {
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

            ArrayList<MapSeatClassCapacity> mapSeatClassCapacities = new ArrayList<MapSeatClassCapacity>();
            Character seatClassName;
            Integer seatClassCapacity;
            List<String> secondLineFields = Arrays.asList(secondLine.split("\\s* \\s*"));
            char[] chars;

            for(int j=0; j<secondLineFields.size(); j++) {
                chars = secondLineFields.get(j).toCharArray();
                seatClassName = chars[0];
                seatClassCapacity = chars[1]=='C' ? 0 : chars[1]=='A' ? 9 : Character.getNumericValue(chars[1]);
                mapSeatClassCapacities.add(new MapSeatClassCapacity(new SeatClass(seatClassName, srcCode, destCode, airlineCode), seatClassCapacity));
            }
            flights.add(new Flight(airlineCode, flightNo, date, srcCode, destCode, depTime, arrTime, planeModel, mapSeatClassCapacities));
        }
        return flights;
    }


    public PriceValueObject getPricesList(SeatClass seatClass) {
        String prices;
        String priceRequest = "PRICE "+ seatClass.getOriginCode() + " " + seatClass.getDestinationCode() + " " +
                                        seatClass.getAirlineCode() + " " + seatClass.getName();
        outHelperServer.println(priceRequest);
        try {
            prices = inHelperServer.readLine();
        }
        catch (IOException ex) {
            System.out.println("inHelperServer - socket - Error in reading Price response!");
            return getPricesList(seatClass);
        }
        List<String> pricesFields = Arrays.asList(prices.split("\\s* \\s*"));
        if(pricesFields.size() != 3)
            System.out.println("inHelperServer - format error - PRICE response from helper server format is wrong!!!");

        Integer adultPrice = Integer.parseInt(pricesFields.get(0));
        Integer childPrice = Integer.parseInt(pricesFields.get(1));
        Integer infantPrice = Integer.parseInt(pricesFields.get(2));

        return new PriceValueObject(adultPrice, childPrice, infantPrice);
    }


    public ReserveValueObject doReservation(Reservation reservation) {
        String resRequest = "RES " + reservation.getSrcCode() + " " + reservation.getDestCode() + " " +
                                     reservation.getDate() + " " + reservation.getAirlineCode() + " " +
                                     reservation.getFlightNumber() + " " + reservation.getSeatClassName() + " " +
                                     reservation.getAdultCount() + " "  + reservation.getChildCount() + " " +
                                     reservation.getInfantCount();
        outHelperServer.println(resRequest);
        for(int i=0; i<reservation.getPassengerList().size(); i++)
            outHelperServer.println(reservation.getPassengerList().get(i).toString());

        String resResponse;
        try {
            resResponse = inHelperServer.readLine();
            if (resResponse == null) {
                System.out.println("inHelperServer - probably connection lost  - RES Response is null!");
                return null;
            }
        }
        catch (IOException ex){
            System.out.println("inHelperServer - socket - error in reading RES response from server");
            return null;
        }
        List<String> resResponseFields = Arrays.asList(resResponse.split("\\s* \\s*"));
        if(resResponseFields.size() != 4) {
            System.out.println("inHelperServer - format error - RES result format is wrong!!!");
            return null;
        }

        String token = resResponseFields.get(0);
        Integer adultPrice = Integer.parseInt(resResponseFields.get(1));
        Integer childPrice = Integer.parseInt(resResponseFields.get(2));
        Integer infantPrice = Integer.parseInt(resResponseFields.get(3));

        return new ReserveValueObject(token, adultPrice, childPrice, infantPrice);
    }


    public FinalizeValueObject doFinalization(Reservation reservation) {
        String finRequest = "FIN " + reservation.getToken();
        outHelperServer.println(finRequest);

        ArrayList<String> ticketNumbersList = new ArrayList<String>();
        String referenceCode = "";
        try{
            referenceCode = inHelperServer.readLine();
            for(int i=0; i<reservation.getPassengerList().size(); i++)
                ticketNumbersList.add(inHelperServer.readLine());
        }
        catch (IOException ex) {
            System.out.println("inHelperServer - socket - error reading reference code or ticket numbers from helper server");
        }
        return new FinalizeValueObject(referenceCode, ticketNumbersList);
    }
}
