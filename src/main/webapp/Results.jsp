<%@page contentType="text/html;charset=UTF-8" import="domain.*"%>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="ar">
<head>
    <!-- Bootstrap -->
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <title>انتخاب پرواز</title>
    <link rel="stylesheet" type="text/css" href="CSS-Reset.css">
    <link rel="stylesheet" type="text/css" href="Results.css">


    <link rel="stylesheet" href="Fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <meta name="description" content="enter information">
    <meta name="keywords" content="ticket, book, booking, from, to, akbarticket">
    <meta name="author" content="Melika Ayoughi, Ali Iman">
</head>
<body>

<%
    ArrayList<Flight> flightArrayList = (ArrayList<Flight>) request.getAttribute("flightList");

    int numOfFlights = 0;
    for(Flight flight : flightArrayList){
        numOfFlights += flight.getMapSeatClassCapacities().size();
    }
%>
<header>
    <div class="hidden-xs hidden-sm col-md-2 pull-right"></div>
    <div class="col-xs-6 col-sm-4 col-md-2 pull-right">
        <div class="brand-logo">
            <img src="Images/LogoBlack.png" alt="">اکبر تیکت
            &reg;
        </div>
    </div>
    <div class="hidden-xs col-sm-4 col-md-4 pull-right"></div>
    <div class="col-xs-6 col-sm-4 col-md-2 pull-right">
        <div class="username-dropdown">
            <button class="dropbtn"><i class="fa fa-user fa-fw"></i>نام کاربر</button>
            <div class="dropdown-content">
                <a href="#">بلیت های من</a>
                <a href="#">خروج</a>
            </div>
        </div>
    </div>
    <div class="hidden-xs hidden-sm col-md-2 pull-right"></div>
    <br> <br> <br>
</header>
<div class="container">
    <div class="heading">
        <div id="search" class="hidden-xs">جستجوی پرواز  ></div>
        <div id="choose">انتخاب پرواز  ></div>
        <div id="reserve" class="hidden-xs">ورود اطلاعات  ></div>
        <div id="ticket" class="hidden-xs">دریافت بلیت</div>
    </div>
    <div class="toolbar">
        <div class="hidden-xs hidden-sm col-md-1 pull-right"></div>
        <div class="col-xs-6 col-sm-5 col-md-3 pull-right">
            <!--<span class="glyphicon glyphicon-arrow-right" aria-hidden="true">  صفحه ی نتایج </span>-->
            <i class="fa fa-arrow-right" aria-hidden="true"></i>
            <div class="backtosearch"><a href="Home-Search.html" class="hidden-xs"> برگشت به صفحه ی جستجو</a></div>
        </div>
        <div class="hidden-xs col-sm-2 col-md-3 pull-right"></div>
        <div class="col-xs-6 col-sm-5 col-md-4 pull-right">
            <div class="number-of-flights">
                <span> <%= numOfFlights%> </span>
                <span>پرواز </span>
                <span class="hidden-xs hidden-sm">با مشخصات دلخواه شما  </span>
                <span>پیدا شد</span>
            </div>
        </div>
        <div class="hidden-xs hidden-sm col-md-1 pull-right"></div>
    </div>

    <div class="gray-back row">

    <%
        for(Flight flight : flightArrayList){
            for(MapSeatClassCapacity mapSeatClassCapacity : flight.getMapSeatClassCapacities()){
                %>
                <div class="white-section col-md-9 pull-right row-distance">
                    <div class="row">
                        <div class="col-md-3 place-middle font-large lbl-color-black pull-right">
                            <%=flight.getAirlineCode() +" "+flight.getFlightNumber()%>
                        </div>

                        <div class="col-md-9 place-middle font-large lbl-color-dark-gray pull-right">
                            <%=flight.getSrcCode()+" "+flight.getDepartureTime()+" >> "+flight.getDestCode()+" "+flight.getArrivalTime()%>
                            <%--تهران ۱۷:۴۰ >> مشهد ۱۸:۵۰--%>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-3 place-middle lbl-color-black pull-right">
                            <span><i class="fa fa-calendar-o" aria-hidden="true"></i></span>
                            <span><%=flight.getDate()%></span>
                            <%--<span> دوشنبه ۱۷ اسفند</span>--%>
                        </div>

                        <div class="col-md-3 place-middle font-small lbl-color-light-gray pull-right">
                            <span><i class="fa fa-plane fa-size"></i></span>
                            <span><%=flight.getAirplaneModel()%></span>
                            <%--<span>بوئینگ MD83</span>--%>
                        </div>

                        <div class="col-md-6 place-middle font-small lbl-color-light-gray pull-right">
                            <span><i class="fa fa-suitcase" aria-hidden="true"></i></span>
                            <span><%=mapSeatClassCapacity.getCapacity()%></span>
                            <span>صندلی باقی مانده کلاس</span>
                            <span>
                                <%=mapSeatClassCapacity.getSeatClass().getName()%>
                            </span>
                        </div>
                    </div>
                </div>

                <div class="price-btn col-md-3 pull-right row-distance">
                    <div class="col-md-12 place-middle">
                        <span><%=mapSeatClassCapacity.getSeatClass().getAdultPrice()%></span>
                        <span>ریال</span>

                    </div>
                    <br>
                    <br>
                    <div class="col-md-12 place-middle">
                        رزرو آنلاین
                    </div>
                </div>
                <%
            }
        }
    %>

    </div>


</div>

<div>
    <footer>ملیکا عیوقی، علی ایمان | دانشکده فنی دانشگاه تهران، بهار ۱۳۹۶</footer>
</div>

</body>
</html>