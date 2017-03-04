package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by melikaayoughi on 3/4/17.
 */
public class ResultsServlet extends HttpServlet {

    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String source = request.getParameter("src");
            String destination = request.getParameter("dest");
            int departureDate = Integer.parseInt(request.getParameter("departureDate")) ;
            String returnDate = request.getParameter("returnDate");
            String adultCount = request.getParameter("adult-count");
            String childCount = request.getParameter("child-count");
            String infantCount = request.getParameter("infant-count");
            System.out.println(source + destination + departureDate + returnDate + adultCount + childCount + infantCount);

            request.getRequestDispatcher("Results.jsp").forward(request, response);
        }
        catch (Exception ex){
            request.getRequestDispatcher("ErrorPage.jsp?errorMessage=Bad%20number%20format3").forward(request, response);
        }

    }

    public void destroy()
    {
        // do nothing.
    }

}
