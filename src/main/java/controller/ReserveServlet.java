package controller;

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

//        <Adult Price> <Child Price> <Infant Price>
        logger.info("TMPRES "+request.getParameter("flight-id"));

    }

    public void destroy() {
    }
}
