package controller;

import domain.AkbarTicket;
import domain.Passenger;
import domain.Reservation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import domain.TicketBean;
import org.apache.log4j.Logger;

/**
 * Created by melikaayoughi on 3/7/17.
 */
public class TicketsServlet extends HttpServlet{

    private final Logger logger = Logger.getLogger(TicketsServlet.class);
    public void init() throws ServletException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//    response.sendRedirect("Home-Search.html");

    Integer totalPassengerCount = Integer.parseInt(request.getParameter("total-count"));
    ArrayList<Passenger> passengerList = new ArrayList<Passenger>();

    ArrayList<String> genderList = new ArrayList<String>();

    for (int i = 1; i <= totalPassengerCount; i++) {
        String gender = request.getParameter("gender");
        genderList.add(gender);
        
        Passenger passenger = new Passenger(request.getParameter("name-" + i),request.getParameter("surname-" + i)
                ,request.getParameter("id-" + i),request.getParameter("gender-"+i));
        passengerList.add(passenger);
        logger.debug("PASSENGER " + i + " passed on to ticket.jsp :" + passenger.toString());
    }


    Reservation reservation = new Reservation(request.getParameter("src-code"),
            request.getParameter("dest-code"), request.getParameter("date"),
            request.getParameter("airline-code"), request.getParameter("flight-number"),
            request.getParameter("seat-class"), request.getParameter("adult-count"),
            request.getParameter("child-count"), request.getParameter("infant-count"),
            passengerList);

    logger.debug("RESERVATION passed on to AkbarTicket.reserve()    " + reservation.toString());

    Reservation finalReservation = AkbarTicket.getAkbarTicket().reserve(reservation);

    logger.debug("FINAL RESERVATION: " + finalReservation.toString());

    ArrayList<TicketBean> ticketBeans = AkbarTicket.getAkbarTicket().finalize(finalReservation.getToken());

    logger.debug("Ticketbeans: "+ ticketBeans.toString());

    request.setAttribute("tickets", ticketBeans);
    request.getRequestDispatcher("Tickets.jsp").forward(request, response);

    }

    public void destroy() {
    }

}
