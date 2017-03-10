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
        Elements elements;

        elements = responseElement.getElementsByClass("col-md-3 place-middle font-large lbl-color-black pull-right");
        Element airlineCodeFlightNumber = elements.get(0);
        String airlineCodeFlightNumberString = airlineCodeFlightNumber.childNode(0).toString();
        assertEquals("Flight number and airline code", "\n W5 822 ", airlineCodeFlightNumberString);

        elements = responseElement.getElementsByClass("col-md-9 place-middle font-large lbl-color-dark-gray pull-right");
        Element srcCodeDestCodeArrTimeDepTime = elements.get(0);
        String srcCodeDestCodeArrTimeDepTimeString = srcCodeDestCodeArrTimeDepTime.childNode(0).toString();
        assertEquals("SrcCode destCode arrTime depTime", "\n MHD 0730 &gt;&gt; THR 0840 ", srcCodeDestCodeArrTimeDepTimeString);

        elements = responseElement.getElementsByClass("col-md-3 place-middle lbl-color-black pull-right");
        Element date = elements.get(0);
        String dateString = date.childNode(3).toString();
        assertEquals("Date", "<span>06Feb</span>", dateString);

        elements = responseElement.getElementsByClass("col-md-3 place-middle font-small lbl-color-light-gray pull-right");
        Element planeModel = elements.get(0);
        String plainModelString = planeModel.childNode(3).toString();
        assertEquals("Plain model", "<span>351</span>", plainModelString);

        elements = responseElement.getElementsByClass("col-md-6 place-middle font-small lbl-color-light-gray pull-right");
        Element seatClassCapacity = elements.get(0);
        String capacityString = seatClassCapacity.childNode(3).toString();
        String seatClassString = seatClassCapacity.childNode(7).toString();
        assertEquals("Capacity", "<span>9</span>", capacityString);
        assertEquals("SeatClass name", "<span> F </span>", seatClassString);

        elements = responseElement.getElementsByClass("col-md-12 place-middle");
        Element totalPrice = elements.get(0);
        String totalPriceString = totalPrice.childNode(1).toString();
        assertEquals("Total price = 3000 + 2000 + 1000", "<span>6000</span>", totalPriceString);
    }

    @Test
    public void doPost3() throws Exception {
        request = new HttpServletRequestMock();
        response = new HttpServletResponseMock();

        request.addParameter("src", "THR");
        request.addParameter("dest", "MHD");
        request.addParameter("departureDate", "05Feb");
        request.addParameter("returnDate", "07Feb");
        request.addParameter("adult-count", "4");
        request.addParameter("child-count", "3");
        request.addParameter("infant-count", "2");

        resultsServlet.doPost(request, response);
        String responseHtml = response.getData().toString();
        Document responseDoc = Jsoup.parse(responseHtml);
        Elements responseElements = responseDoc.getElementsByClass("results-form");
        assertEquals("There are 2 flights and 2 seatClasses that can handle 9 persons",
                2, responseElements.size());

        Element responseOneElement = responseElements.get(0);
        Element responseTwoElement = responseElements.get(1);
        Elements elements;

        elements = responseOneElement.getElementsByClass("col-md-3 place-middle font-large lbl-color-black pull-right");
        Element airlineCodeFlightNumber = elements.get(0);
        String airlineCodeFlightNumberString = airlineCodeFlightNumber.childNode(0).toString();
        assertEquals("Flight number and airline code", "\n IR 452 ", airlineCodeFlightNumberString);

        elements = responseOneElement.getElementsByClass("col-md-9 place-middle font-large lbl-color-dark-gray pull-right");
        Element srcCodeDestCodeArrTimeDepTime = elements.get(0);
        String srcCodeDestCodeArrTimeDepTimeString = srcCodeDestCodeArrTimeDepTime.childNode(0).toString();
        assertEquals("SrcCode destCode arrTime depTime", "\n THR 1740 &gt;&gt; MHD 1850 ", srcCodeDestCodeArrTimeDepTimeString);

        elements = responseOneElement.getElementsByClass("col-md-3 place-middle lbl-color-black pull-right");
        Element date = elements.get(0);
        String dateString = date.childNode(3).toString();
        assertEquals("Date", "<span>05Feb</span>", dateString);

        elements = responseOneElement.getElementsByClass("col-md-3 place-middle font-small lbl-color-light-gray pull-right");
        Element planeModel = elements.get(0);
        String plainModelString = planeModel.childNode(3).toString();
        assertEquals("Plain model", "<span>M80</span>", plainModelString);

        elements = responseOneElement.getElementsByClass("col-md-6 place-middle font-small lbl-color-light-gray pull-right");
        Element seatClassCapacity = elements.get(0);
        String capacityString = seatClassCapacity.childNode(3).toString();
        String seatClassString = seatClassCapacity.childNode(7).toString();
        assertEquals("Capacity", "<span>9</span>", capacityString);
        assertEquals("SeatClass name", "<span> M </span>", seatClassString);

        elements = responseOneElement.getElementsByClass("col-md-12 place-middle");
        Element totalPrice = elements.get(0);
        String totalPriceString = totalPrice.childNode(1).toString();
        assertEquals("Total price = 4*4000 + 3*3000 + 2*2000", "<span>29000</span>", totalPriceString);


        elements = responseTwoElement.getElementsByClass("col-md-3 place-middle font-large lbl-color-black pull-right");
        airlineCodeFlightNumber = elements.get(0);
        airlineCodeFlightNumberString = airlineCodeFlightNumber.childNode(0).toString();
        assertEquals("Flight number and airline code", "\n IR 822 ", airlineCodeFlightNumberString);

        elements = responseTwoElement.getElementsByClass("col-md-9 place-middle font-large lbl-color-dark-gray pull-right");
        srcCodeDestCodeArrTimeDepTime = elements.get(0);
        srcCodeDestCodeArrTimeDepTimeString = srcCodeDestCodeArrTimeDepTime.childNode(0).toString();
        assertEquals("SrcCode destCode arrTime depTime", "\n THR 0730 &gt;&gt; MHD 0840 ", srcCodeDestCodeArrTimeDepTimeString);

        elements = responseTwoElement.getElementsByClass("col-md-3 place-middle lbl-color-black pull-right");
        date = elements.get(0);
        dateString = date.childNode(3).toString();
        assertEquals("Date", "<span>05Feb</span>", dateString);

        elements = responseTwoElement.getElementsByClass("col-md-3 place-middle font-small lbl-color-light-gray pull-right");
        planeModel = elements.get(0);
        plainModelString = planeModel.childNode(3).toString();
        assertEquals("Plain model", "<span>351</span>", plainModelString);

        elements = responseTwoElement.getElementsByClass("col-md-6 place-middle font-small lbl-color-light-gray pull-right");
        seatClassCapacity = elements.get(0);
        capacityString = seatClassCapacity.childNode(3).toString();
        seatClassString = seatClassCapacity.childNode(7).toString();
        assertEquals("Capacity", "<span>9</span>", capacityString);
        assertEquals("SeatClass name", "<span> F </span>", seatClassString);

        elements = responseTwoElement.getElementsByClass("col-md-12 place-middle");
        totalPrice = elements.get(0);
        totalPriceString = totalPrice.childNode(1).toString();
        assertEquals("Total price = 4*3000 + 3*2000 + 2*1000", "<span>20000</span>", totalPriceString);
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