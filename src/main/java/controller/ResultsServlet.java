package controller;

import domain.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by melikaayoughi on 3/4/17.
 */

public class ResultsServlet extends HttpServlet {

    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        FlightProvider ca1HelperServer = new CA1HelperServer("188.166.78.119", 8081);
        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket();
        akbarTicket.setFlightProvider(ca1HelperServer);

        try {
            String source = request.getParameter("src");
            String destination = request.getParameter("dest");
            String departureDate = request.getParameter("departureDate");
            String returnDate = request.getParameter("returnDate");
            int adultCount = Integer.parseInt(request.getParameter("adult-count"));
            int childCount = Integer.parseInt(request.getParameter("child-count"));
            int infantCount = Integer.parseInt(request.getParameter("infant-count"));

            ArrayList<Flight> flightsList = akbarTicket.search(source,destination,departureDate,adultCount,childCount,infantCount);

            request.setAttribute("flightList", flightsList);
            request.getRequestDispatcher("Results.jsp").forward(request, response);
        }
        catch (Exception ex){
            request.getRequestDispatcher("ErrorPage.jsp?errorMessage=Bad%20number%20format3").forward(request, response);
            ex.printStackTrace();
        }

    }

    public void destroy()
    {
        // do nothing.
    }

}
