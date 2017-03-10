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

import static org.junit.Assert.assertEquals;


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
    public void doPost1() throws Exception {
        request = new HttpServletRequestMock();
        response = new HttpServletResponseMock();

        request.addParameter("src", "THR");
        request.addParameter("dest", "MHD");
        request.addParameter("departureDate", "06Feb");
        request.addParameter("returnDate", "07Feb");
        request.addParameter("adult-count", "1");
        request.addParameter("child-count", "1");
        request.addParameter("infant-count", "1");

        resultsServlet.doPost(request, response);
        String responseHtml = response.getData().toString();
        Document responseDoc = Jsoup.parse(responseHtml);
        Elements responseElements = responseDoc.getElementsByClass("results-form");
        assertEquals("There should be no flight from THR to MHD on 06Feb",
                                                                0, responseElements.size());
    }

    @Test
    public void doPost2() throws Exception {
        request = new HttpServletRequestMock();
        response = new HttpServletResponseMock();

        request.addParameter("src", "MHD");
        request.addParameter("dest", "THR");
        request.addParameter("departureDate", "06Feb");
        request.addParameter("returnDate", "07Feb");
        request.addParameter("adult-count", "1");
        request.addParameter("child-count", "1");
        request.addParameter("infant-count", "1");

        resultsServlet.doPost(request, response);
        String responseHtml = response.getData().toString();
        Document responseDoc = Jsoup.parse(responseHtml);
        Elements responseElements = responseDoc.getElementsByClass("results-form");
        assertEquals("There should be one flight with 3 seatClasses but just one of them can handle 3 persons",
                                                                1, responseElements.size());
        Element responseElement = responseElements.get(0);
    }

    @Test
    public void doPost4() throws Exception {
        request = new HttpServletRequestMock();
        response = new HttpServletResponseMock();

        request.addParameter("src", "THR");
        request.addParameter("dest", "MHD");
        request.addParameter("departureDate", "05Feb");
        request.addParameter("returnDate", "07Feb");
        request.addParameter("adult-count", "4");
        request.addParameter("child-count", "3");
        request.addParameter("infant-count", "3");

        resultsServlet.doPost(request, response);
        String responseHtml = response.getData().toString();
        Document responseDoc = Jsoup.parse(responseHtml);
        Elements responseElements = responseDoc.getElementsByClass("results-form");
        assertEquals("There are 2 flights and 6 seatClasses but none of them can handle 10 person",
                                                                0, responseElements.size());
    }
}