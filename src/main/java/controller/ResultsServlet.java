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

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AkbarTicket akbarTicket = AkbarTicket.getAkbarTicket();
        try {
            String source = request.getParameter("src");
            String destination = request.getParameter("dest");
            String departureDate = request.getParameter("departureDate");
            String returnDate = request.getParameter("returnDate");
            Integer adultCount = Integer.parseInt(request.getParameter("adult-count"));
            Integer childCount = Integer.parseInt(request.getParameter("child-count"));
            Integer infantCount = Integer.parseInt(request.getParameter("infant-count"));
            ArrayList<Flight> flightsList = akbarTicket.search(source, destination, departureDate, adultCount, childCount, infantCount);
//            request.setAttribute("flightList", flightsList);
//            request.getRequestDispatcher("Results.jsp").forward(request, response);

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.write("\n");
            out.write("\n");
            out.write("\n");
            out.write("<!DOCTYPE html>\n");
            out.write("<html lang=\"ar\">\n");
            out.write("<head>\n");
            out.write("    <!-- Bootstrap -->\n");
            out.write("    <meta charset=\"UTF-8\">\n");
            out.write("    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
            out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
            out.write("    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->\n");
            out.write("    <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">\n");
            out.write("\n");

            out.write("    <title>انتخاب پرواز</title>\n");
            out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"CSS-Reset.css\">\n");
            out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"Results.css\">\n");
            out.write("\n");
            out.write("\n");
            out.write("    <link rel=\"stylesheet\" href=\"Fonts/font-awesome-4.7.0/css/font-awesome.min.css\">\n");
            out.write("    <meta name=\"description\" content=\"enter information\">\n");
            out.write("    <meta name=\"keywords\" content=\"ticket, book, booking, from, to, akbarticket\">\n");
            out.write("    <meta name=\"author\" content=\"Melika Ayoughi, Ali Iman\">\n");
            out.write("</head>\n");
            out.write("<body>\n");
            out.write("\n");

            int numOfFlights = 0;
            for(Flight flight : flightsList){
                numOfFlights += flight.getMapSeatClassCapacities().size();
            }

            out.write("\n");
            out.write("<header>\n");
            out.write("    <div class=\"hidden-xs hidden-sm col-md-2 pull-right\"></div>\n");
            out.write("    <div class=\"col-xs-6 col-sm-4 col-md-2 pull-right\">\n");
            out.write("        <div class=\"brand-logo\">\n");
            out.write("            <img src=\"Images/LogoBlack.png\" alt=\"\">اکبر تیکت\n");
            out.write("            &reg;\n");
            out.write("        </div>\n");
            out.write("    </div>\n");
            out.write("    <div class=\"hidden-xs col-sm-4 col-md-4 pull-right\"></div>\n");
            out.write("    <div class=\"col-xs-6 col-sm-4 col-md-2 pull-right\">\n");
            out.write("        <div class=\"username-dropdown\">\n");
            out.write("            <button class=\"dropbtn\"><i class=\"fa fa-user fa-fw\"></i>نام کاربر</button>\n");
            out.write("            <div class=\"dropdown-content\">\n");
            out.write("                <a href=\"#\">بلیت های من</a>\n");
            out.write("                <a href=\"#\">خروج</a>\n");
            out.write("            </div>\n");
            out.write("        </div>\n");
            out.write("    </div>\n");
            out.write("    <div class=\"hidden-xs hidden-sm col-md-2 pull-right\"></div>\n");
            out.write("    <br> <br> <br>\n");
            out.write("</header>\n");
            out.write("<div class=\"container\">\n");
            out.write("    <div class=\"heading\">\n");
            out.write("        <div id=\"search\" class=\"hidden-xs\">جستجوی پرواز  ></div>\n");
            out.write("        <div id=\"choose\">انتخاب پرواز  ></div>\n");
            out.write("        <div id=\"reserve\" class=\"hidden-xs\">ورود اطلاعات  ></div>\n");
            out.write("        <div id=\"ticket\" class=\"hidden-xs\">دریافت بلیت</div>\n");
            out.write("    </div>\n");
            out.write("    <div class=\"toolbar\">\n");
            out.write("        <div class=\"hidden-xs hidden-sm col-md-1 pull-right\"></div>\n");
            out.write("        <div class=\"col-xs-6 col-sm-5 col-md-3 pull-right\">\n");
            out.write("            <!--<span class=\"glyphicon glyphicon-arrow-right\" aria-hidden=\"true\">  صفحه ی نتایج </span>-->\n");
            out.write("            <i class=\"fa fa-arrow-right\" aria-hidden=\"true\"></i>\n");
            out.write("            <div class=\"backtosearch\"><a href=\"Home-Search.html\" class=\"hidden-xs\"> برگشت به صفحه ی جستجو</a></div>\n");
            out.write("        </div>\n");
            out.write("        <div class=\"hidden-xs col-sm-2 col-md-3 pull-right\"></div>\n");
            out.write("        <div class=\"col-xs-6 col-sm-5 col-md-4 pull-right\">\n");
            out.write("            <div class=\"number-of-flights\">\n");
            out.write("                <span> ");
            out.print( numOfFlights);
            out.write(" </span>\n");
            out.write("                <span>پرواز </span>\n");
            out.write("                <span class=\"hidden-xs hidden-sm\">با مشخصات دلخواه شما  </span>\n");
            out.write("                <span>پیدا شد</span>\n");
            out.write("            </div>\n");
            out.write("        </div>\n");
            out.write("        <div class=\"hidden-xs hidden-sm col-md-1 pull-right\"></div>\n");
            out.write("    </div>\n");
            out.write("\n");
            out.write("    <div class=\"gray-back row\">\n");
            out.write("\n");
            out.write("    ");

            for(Flight flight : flightsList){
                for(MapSeatClassCapacity mapSeatClassCapacity : flight.getMapSeatClassCapacities()){

                    out.write("\n");
                    out.write("\n");
                    out.write("        ");
                    out.write("\n");
                    out.write("        <form class=\"results-form\"  action=\"Reserve.do?");
                    out.print("flight-id="+flight.getFlightId()+"&flight-number="
                            +flight.getFlightNumber()+"&date="+flight.getDate()+"&src-code="+flight.getSrcCode()+"&dest-code="
                            +flight.getDestCode()+"&airline-code="+flight.getAirlineCode()+"&seat-class="
                            +mapSeatClassCapacity.getSeatClass().getName()+"&adult-count="+request.getParameter("adult-count")
                            +"&child-count="+request.getParameter("child-count")+"&infant-count="
                            +request.getParameter("infant-count"));
                    out.write("\" method=\"POST\">\n");
                    out.write("\n");
                    out.write("                <div class=\"white-section col-md-9 pull-right row-distance\">\n");
                    out.write("                    <div class=\"row\">\n");
                    out.write("                        <div class=\"col-md-3 place-middle font-large lbl-color-black pull-right\">\n");
                    out.write("                            ");
                    out.print(flight.getAirlineCode() +" "+flight.getFlightNumber());
                    out.write("\n");
                    out.write("                        </div>\n");
                    out.write("\n");
                    out.write("                        <div class=\"col-md-9 place-middle font-large lbl-color-dark-gray pull-right\">\n");
                    out.write("                            ");
                    out.print(flight.getSrcCode()+" "+flight.getDepartureTime()+" >> "+flight.getDestCode()+" "+flight.getArrivalTime());
                    out.write("\n");
                    out.write("                            ");
                    out.write("\n");
                    out.write("                        </div>\n");
                    out.write("                    </div>\n");
                    out.write("\n");
                    out.write("                    <div class=\"row\">\n");
                    out.write("                        <div class=\"col-md-3 place-middle lbl-color-black pull-right\">\n");
                    out.write("                            <span><i class=\"fa fa-calendar-o\" aria-hidden=\"true\"></i></span>\n");
                    out.write("                            <span>");
                    out.print(flight.getDate());
                    out.write("</span>\n");
                    out.write("                            ");
                    out.write("\n");
                    out.write("                        </div>\n");
                    out.write("\n");
                    out.write("                        <div class=\"col-md-3 place-middle font-small lbl-color-light-gray pull-right\">\n");
                    out.write("                            <span><i class=\"fa fa-plane fa-size\"></i></span>\n");
                    out.write("                            <span>");
                    out.print(flight.getAirplaneModel());
                    out.write("</span>\n");
                    out.write("                            ");
                    out.write("\n");
                    out.write("                        </div>\n");
                    out.write("\n");
                    out.write("                        <div class=\"col-md-6 place-middle font-small lbl-color-light-gray pull-right\">\n");
                    out.write("                            <span><i class=\"fa fa-suitcase\" aria-hidden=\"true\"></i></span>\n");
                    out.write("                            <span>");
                    out.print(mapSeatClassCapacity.getCapacity());
                    out.write("</span>\n");
                    out.write("                            <span>صندلی باقی مانده کلاس</span>\n");
                    out.write("                            <span>\n");
                    out.write("                                ");
                    out.print(mapSeatClassCapacity.getSeatClass().getName());
                    out.write("\n");
                    out.write("                            </span>\n");
                    out.write("                        </div>\n");
                    out.write("                    </div>\n");
                    out.write("                </div>\n");
                    out.write("\n");
                    out.write("                <button type=\"submit\" class=\"price-btn col-md-3 pull-right row-distance\">\n");
                    out.write("                    <div class=\"col-md-12 place-middle\">\n");
                    out.write("                        <span>");
                    out.print(mapSeatClassCapacity.getSeatClass().getAdultPrice());
                    out.write("</span>\n");
                    out.write("                        <span>ریال</span>\n");
                    out.write("\n");
                    out.write("                    </div>\n");
                    out.write("                    <br>\n");
                    out.write("                    <br>\n");
                    out.write("                    <div class=\"col-md-12 place-middle\">\n");
                    out.write("                        رزرو آنلاین\n");
                    out.write("                    </div>\n");
                    out.write("                </button>\n");
                    out.write("        </form>\n");
                    out.write("                ");

                }
            }

            out.write("\n");
            out.write("\n");
            out.write("    </div>\n");
            out.write("\n");
            out.write("\n");
            out.write("</div>\n");
            out.write("\n");
            out.write("<div>\n");
            out.write("    <footer>ملیکا عیوقی، علی ایمان | دانشکده فنی دانشگاه تهران، بهار ۱۳۹۶</footer>\n");
            out.write("</div>\n");
            out.write("\n");
            out.write("</body>\n");
            out.write("</html>");

        }
        catch (Exception ex) {
            request.getRequestDispatcher("ErrorPage.jsp?errorMessage=Bad%20number%20format3").forward(request, response);
            ex.printStackTrace();
        }
    }

    public void destroy() {
    }
}