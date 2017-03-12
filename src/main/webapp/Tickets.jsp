<%--
  Created by IntelliJ IDEA.
  User: melikaayoughi
  Date: 3/9/17
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
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

    <title>بلیت مسافران</title>
    <link rel="stylesheet" type="text/css" href="CSS-Reset.css">
    <link rel="stylesheet" type="text/css" href="Tickets.css">


    <link rel="stylesheet" href="Fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <meta name="description" content="enter information">
    <meta name="keywords" content="ticket, book, booking, from, to, akbarticket">
    <meta name="author" content="Melika Ayoughi, Ali Iman">
</head>
<body>
<%
    ArrayList<TicketBean> tickets = (ArrayList<TicketBean>)request.getAttribute("tickets");
%>

<%@include file="header.jsp" %>

<%--<header>--%>
    <%--<div class="brand-logo">--%>
        <%--<img src="Images/LogoBlack.png" alt="">اکبر تیکت--%>
        <%--&reg;--%>
    <%--</div>--%>
    <%--<div class="username-dropdown">--%>
        <%--<button class="dropbtn"><i class="fa fa-user fa-fw"></i>نام کاربر</button>--%>
        <%--<div class="dropdown-content">--%>
            <%--<a href="#">بلیت های من</a>--%>
            <%--<a href="#">خروج</a>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<br> <br> <br>--%>
<%--</header>--%>
<div class="container">
    <div class="heading">
        <div id="search" class="hidden-xs">جستجوی پرواز  ></div>
        <div id="choose" class="hidden-xs">انتخاب پرواز  ></div>
        <div id="reserve" class="hidden-xs">ورود اطلاعات  ></div>
        <div id="ticket">دریافت بلیت</div>
    </div>

    <div class="toolbar">
        <h4 >
            <span>بلیت ها </span>
            <span class="hidden-xs">ی صادر شده</span>
        </h4>
        <button  id="print-button" type="button" onclick=""><a href="#openModal"> چاپ همه </a></button>
        <div id="openModal" class="modalDialog">
            <div> <a href="#close" title="Close" class="close">X</a>
                <button type="button" id="PDFButton" onclick="">
                    <span>PDF</span>
                </button>
                <button type="button" id="EmailButton" onclick="">
                    <span>Email</span>
                </button>
            </div>
        </div>
    </div>

    <%
        for (TicketBean ticket : tickets){
    %>
            <div class="gray-ticket-box">
                <div class="ticket-rows row-1-6 place-middle-label">
                    شماره بلیت
                </div>
                <div class="ticket-rows row-1-3 place-middle-value">
                    <b><%=ticket.ticketNo%></b>
                    <%--<b>۲۰۰۴۲۴۱۵</b>--%>
                </div>
                <div class="ticket-rows row-1-6 place-middle-label">
                    کد مرجع
                </div>
                <div class="ticket-rows row-1-3 place-middle-value">
                    <b><%=ticket.referenceCode%></b>
                    <%--<b>۲۳۴۱۰۰۳</b>--%>
                </div>

                <hr>
                <!----------------------------------------------------------->
                <div class="ticket-rows row-1-12 place-middle-label">
                    نام
                </div>
                <div class="ticket-rows row-1-4 place-middle-value">
                    <%=ticket.gender+" "+ticket.firstName+" "+ticket.surname+" - "+ticket.type%>
                    <%--MR Adult Iranpour--%>
                </div>
                <div class="ticket-rows row-1-12 place-middle-label">
                    پرواز
                </div>
                <div class="ticket-rows row-1-6 place-middle-value">
                    <%=ticket.airlineCode+" "+ticket.flightNo%>
                    <%--معراج ۲۸۰۲--%>
                </div>
                <div class="ticket-rows row-1-6 place-middle-value">
                    <span>کلاس</span>
                    <span><%=ticket.seatClassName%></span>
                    <%--کلاس اقتصادی--%>
                </div>
                <div class="ticket-rows row-1-4 place-middle-value">
                    <%=ticket.airplaneModel%>
                    <%--بوئینگ MD83--%>
                </div>

                <hr>
                <!----------------------------------------------------------->
                <div class="ticket-rows row-1-6 place-middle-label hidden-mobile">
                    از
                </div>
                <div class="ticket-rows row-1-6 place-middle-label hidden-mobile">
                    به
                </div>
                <div class="ticket-rows row-1-3 place-middle-label hidden-mobile">
                    تاریخ
                </div>
                <div class="ticket-rows row-1-6 place-middle-label hidden-mobile">
                    خروج
                </div>
                <div class="ticket-rows row-1-6 place-middle-label hidden-mobile">
                    ورود
                </div>
                <!----------------------------------------------------------->
                <div class="ticket-rows row-1-6 place-middle-label white-box">
                    <%=ticket.originCode%>
                    <%--تهران--%>
                </div>
                <div class="ticket-rows row-1-6 place-middle-label white-box">
                    <%=ticket.destinationCode%>
                    <%--مشهد--%>
                </div>
                <div class="ticket-rows row-1-3 place-middle-label white-box">
                    <%=ticket.date%>
                    <%--دوشنبه ۲۵/۱۱/۱۳۹۵--%>
                </div>
                <div class="ticket-rows row-1-6 place-middle-label white-box">
                   <%=ticket.departureTime%>
                    <%--۱۵:۱۵--%>
                </div>
                <div class="ticket-rows row-1-6 place-middle-label white-box">
                    <%=ticket.arrivalTime%>
                    <%--۱۶:۳۵--%>
                </div>
            </div>

    <%
        }
    %>

</div>
<%--<div>--%>
    <%--<footer>ملیکا عیوقی، علی ایمان | دانشکده فنی دانشگاه تهران، بهار ۱۳۹۶</footer>--%>
<%--</div>--%>
<%@include file="footer.jsp" %>
</body>
</html>