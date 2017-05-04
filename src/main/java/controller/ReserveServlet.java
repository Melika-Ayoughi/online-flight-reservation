package controller;

import domain.AkbarTicket;
import domain.Flight;
import domain.SeatClass;
import domain.MapSeatClassCapacity;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by melikaayoughi on 3/6/17.
 */
public class ReserveServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(ReserveServlet.class);

    public void init() throws ServletException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Flight flight = AkbarTicket.getAkbarTicket().searchFlight(request.getParameter("src-code"),
                    request.getParameter("dest-code"), request.getParameter("date"),
                    request.getParameter("airline-code"), request.getParameter("flight-number"));

            SeatClass seatClass = null;

            for (MapSeatClassCapacity mapSeatClassCapacity : flight.getMapSeatClassCapacities()) {
                if (mapSeatClassCapacity.getSeatClass().getName().toString().equals(request.getParameter("seat-class"))) {
                    seatClass = mapSeatClassCapacity.getSeatClass();
                }
            }

            logger.info("TMPRES " + request.getParameter("flight-id") + " " + seatClass.getAdultPrice()
                    + " " + seatClass.getChildPrice() + " " + seatClass.getInfantPrice());
            request.setAttribute("flight", flight);
            request.setAttribute("seat-class", seatClass);
            request.setAttribute("adult-count", request.getParameter("adult-count"));
            request.setAttribute("child-count", request.getParameter("child-count"));
            request.setAttribute("infant-count", request.getParameter("infant-count"));
            request.getRequestDispatcher("Reserve.jsp").forward(request, response);
        }
        catch (IOException ex){
            request.getRequestDispatcher("ErrorPage.jsp?errorMessage=Helper%20Server%20Is%20Unreachable").forward(request, response);
        }
    }

    public void destroy() {
    }
}
