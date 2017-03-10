package controller;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Created by Ali_Iman on 3/7/17.
 */
public class ResultsServletTest {
    ResultsServlet resultsServlet;
    HttpServletRequestMock request;
    HttpServletResponseMock response;

    @Before
    public void setUp() throws Exception {
        resultsServlet = new ResultsServlet();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void doPost() throws Exception {
        request = new HttpServletRequestMock();
        response = new HttpServletResponseMock();

        request.addParameter("src", "THR");
        request.addParameter("dest", "MHD");
        request.addParameter("departureDate", "05Feb");
        request.addParameter("returnDate", "07Feb");
        request.addParameter("adult-count", "1");
        request.addParameter("child-count", "1");
        request.addParameter("infant-count", "1");

        resultsServlet.doPost(request, response);
        String responseHtml = response.getData().toString();
        Document responseDoc = Jsoup.parse(responseHtml);
        Elements elements = responseDoc.getElementsByClass();
        System.out.println(responseDoc);
    }
}