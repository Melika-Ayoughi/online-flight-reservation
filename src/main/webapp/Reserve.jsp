<%--
  Created by IntelliJ IDEA.
  User: melikaayoughi
  Date: 3/6/17
  Time: 19:43
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
    <script src="http://code.jquery.com/jquery.js"></script>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script src="https://unpkg.com/babel-standalone@6/babel.min.js"></script>
    <%--<script src="https://cdnjs.cloudflare.com/ajax/libs/babel-standalone/6.18.1/babel.min.js"></script>--%>

    <title>ورود اطلاعات مسافران</title>

    <link rel="stylesheet" type="text/css" href="CSS-Reset.css">
    <link rel="stylesheet" type="text/css" href="Reserve.css">


    <link rel="stylesheet" href="Fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <meta name="description" content="enter information">
    <meta name="keywords" content="ticket, book, booking, from, to, akbarticket">
    <meta name="author" content="Melika Ayoughi, Ali Iman">
</head>
<body>

<%
    Flight flight = (Flight)request.getAttribute("flight");
    SeatClass seatClass = (SeatClass) request.getAttribute("seat-class");
    Integer totalPassengerCount = Integer.parseInt((String)request.getAttribute("adult-count")) +
            Integer.parseInt((String)request.getAttribute("child-count")) +
            Integer.parseInt((String)request.getAttribute("infant-count")) ;
%>


<%@include file="header.jsp" %>

<div class="container">
    <div class="heading">
        <div id="search" class="hidden-xs">جستجوی پرواز  ></div>
        <div id="choose" class="hidden-xs">انتخاب پرواز  ></div>
        <div id="reserve">ورود اطلاعات  ></div>
        <div id="ticket" class="hidden-xs">دریافت بلیت</div>
    </div>
    <div class="toolbar">
        <div class="hidden-xs hidden-sm col-md-1 pull-right"></div>
        <div class="col-xs-6 col-sm-5 col-md-3 pull-right">
            <i class="fa fa-arrow-right" aria-hidden="true"></i>
            <div class="backtosearch"><span class="hidden-xs">برگشت به</span><span>صفحه ی نتایج </span></div>
        </div>
        <div class="hidden-xs col-sm-2 col-md-4 pull-right"></div>
        <div class="col-xs-6 col-sm-5 col-md-3 pull-right">
            <div class="timing">
                <span>زمان </span>
                <span class="hidden-xs">باقی مانده</span>
                <span>: </span>
                <span id="time">
                    <%--۰۴:۵۹--%>
                </span>
            </div>
        </div>
        <div class="hidden-xs hidden-sm col-md-1 pull-right"></div>
    </div>
    <div class="gray-back">
        <div class="gray-font">
            مشخصات پرواز انتخابی شما
        </div>
        <div class="row" id="flight-info">
            <div class="col-xs-12 col-sm-12 col-md-4 pull-right">
                <div class="place-middle">
                    <%=flight.getAirlineCode() +" "+flight.getFlightNumber()%>
                </div>
                <div class="place-middle"><i class="fa fa-calendar-o" aria-hidden="true"></i> <%=flight.getDate()%></div>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-4 pull-right">
                <div class="place-middle">
                    <%=flight.getSrcCode()+" "+flight.getDepartureTime()+" >> "+flight.getDestCode()+" "+flight.getArrivalTime()%>
                </div>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-4 pull-right">
                <div class="place-middle"><i class="fa fa-suitcase" aria-hidden="true"></i>کلاس
                <%=seatClass.getName()%>
                </div>
                <div class="place-middle"><i class="fa fa-plane" aria-hidden="true"></i>
                    <%=flight.getAirplaneModel()%>
                </div>
            </div>
        </div>
        <div class="gray-font" id="paycheck">
            صورتحساب سفر
        </div>
        <div class="row" id="transfer-paycheck-info">
            <div id="paycheck-header">
                <div class="col-xs-6 col-sm-4 col-md-6 pull-right">
                </div>
                <div class="hidden-xs hidden-sm col-md-2 pull-right">
                    به ازای هر نفر
                </div>
                <div class="hidden-xs col-sm-4 col-md-2 pull-right">
                    تعداد
                </div>
                <div class="col-xs-6 col-sm-4 col-md-2 pull-right">
                    مجموع
                </div>
            </div>
            <div class="paycheck-line" id="first-line">
                <div class="col-xs-6 col-sm-4 col-md-6 pull-right">
                    بزرگسال
                </div>
                <div class="hidden-xs hidden-sm col-md-2 pull-right">
                    <span>
                    <%=seatClass.getAdultPrice()%>
                    </span>
                    <span>ریال</span>
                    <%--۱,۳۵۰,۰۰۰ ریال--%>
                </div>
                <div class="hidden-xs col-sm-4 col-md-2 pull-right">
                    <span><%=request.getAttribute("adult-count")%></span>
                    <span>نفر</span>
                </div>
                <div class="col-xs-6 col-sm-4 col-md-2 pull-right">
                    <span>
                    <%=seatClass.getAdultPrice() * Integer.parseInt((String)request.getAttribute("adult-count"))%>
                    </span>
                    <span>ریال</span>
                    <%--۲,۷۰۰,۰۰۰ ریال--%>
                </div>
            </div>
            <div class="paycheck-line" id="second-line">
                <div class="col-xs-6 col-sm-4 col-md-6 pull-right">
                    کودک زیر ۱۲ سال
                </div>
                <div class="hidden-xs hidden-sm col-md-2 pull-right">
                    <span>
                    <%=seatClass.getChildPrice()%>
                    </span>
                    <span>ریال</span>
                    <%--۱,۰۰۰,۰۰۰ ریال--%>
                </div>
                <div class="hidden-xs col-sm-4 col-md-2 pull-right">
                    <span><%=request.getAttribute("child-count")%></span>
                    <span>نفر</span>
                </div>
                <div class="col-xs-6 col-sm-4 col-md-2 pull-right">
                    <span>
                    <%=seatClass.getChildPrice() * Integer.parseInt((String)request.getAttribute("child-count"))%>
                    </span>
                    <span>ریال</span>
                    <%--۵,۰۰۰,۰۰۰ ریال--%>
                </div>
            </div>
            <div class="paycheck-line" id="third-line">
                <div class="col-xs-6 col-sm-4 col-md-6 pull-right">
                    نوزاد زیر ۲ سال
                </div>
                <div class="hidden-xs hidden-sm col-md-2 pull-right">
                    <span><%=seatClass.getInfantPrice()%>
                    </span>
                    <span>ریال</span>
                    <%--۲,۵۰۰ ریال--%>
                </div>
                <div class="hidden-xs col-sm-4 col-md-2 pull-right">
                    <span><%=request.getAttribute("infant-count")%></span>
                    <span>نفر</span>
                </div>
                <div class="col-xs-6 col-sm-4 col-md-2 pull-right">
                    <span><%=seatClass.getInfantPrice() * Integer.parseInt((String)request.getAttribute("infant-count"))%>
                    </span>
                    <span>ریال</span>
                    <%--۷,۵۰۰ ریال--%>

                </div>
            </div>
            <div class="paycheck-line" id="last-line">
                <div class="col-xs-6 col-sm-4 col-md-6 pull-right">
                    مجموع
                </div>
                <div class="hidden-xs hidden-sm col-md-2 pull-right">
                </div>
                <div class="hidden-xs col-sm-4 col-md-2 pull-right">
                </div>
                <div class="col-xs-6 col-sm-4 col-md-2 pull-right">
                    <span><%=seatClass.getAdultPrice() * Integer.parseInt((String)request.getAttribute("adult-count"))
                            +seatClass.getChildPrice() * Integer.parseInt((String)request.getAttribute("child-count"))
                            +seatClass.getInfantPrice() * Integer.parseInt((String)request.getAttribute("infant-count"))%>
                    </span>
                    <span>ریال</span>
                    <%--۷,۷۰۷,۵۰۰ ریال--%>
                </div>
            </div>
        </div>
        <div class="gray-font" id="information">
            اطلاعات
        </div>

        <div class="alert alert-danger" hidden="true" id="wrongPassengerInfoAlert">
            مشخصات مسافران غلط وارد شده است! لطفا دوباره پر کنید.
        </div>

        <form id="passenger-form"  action="Tickets.do?<%="flight-id="+flight.getFlightId()+"&flight-number="
                +flight.getFlightNumber()+"&date="+flight.getDate()+"&src-code="+flight.getSrcCode()+"&dest-code="
                +flight.getDestCode()+"&airline-code="+flight.getAirlineCode()+"&seat-class="
                +seatClass.getName()+"&adult-count="+request.getAttribute("adult-count")
                +"&child-count="+request.getAttribute("child-count")+"&infant-count="
                +request.getAttribute("infant-count")+"&total-count="+totalPassengerCount%>" method="POST" >
        <div class="passenger-info-list">
        <%
            for(int i=1; i<= totalPassengerCount;i++){

                %>
            <div class="gray-row row">
                <div class="col-xs-6 col-sm-6 col-md-2 pull-right place-middle">
        <%
                if(i<=Integer.parseInt((String)request.getAttribute("adult-count"))){
                    %>

                        <span class="hidden-mobile"><i class="fa fa-user fa-male"></i></span>
                        <span><%=i+"-"%></span><span>بزرگسال:</span>
                        <%--۱- بزرگسال:--%>
                        <%
                }


                if(i>Integer.parseInt((String)request.getAttribute("adult-count")) &&
                        i<=Integer.parseInt((String)request.getAttribute("adult-count"))
                                +Integer.parseInt((String)request.getAttribute("child-count"))){
                    %>

                        <span class="hidden-mobile"><i class="fa fa-user fa-child"></i></span>
                        <span><%=i+"-"%></span><span>خردسال:</span>
                        <%--۳- خردسال:--%>
                        <%
                }

                if(i>Integer.parseInt((String)request.getAttribute("adult-count"))
                        +Integer.parseInt((String)request.getAttribute("child-count"))
                        && i<=totalPassengerCount){
                    %>
                        <span class="hidden-mobile" id="infant-8"><i class="fa fa-user fa-child"></i></span>
                        <span><%=i+"-"%></span><span>نوزاد:</span>
                        <%--۸- نوزاد:--%>
                        <%
                }

                %>
                    <%--</div>--%>
                </div>
                <select name="gender-<%=i%>" class="col-xs-6 col-sm-6 col-md-1 pull-right place-middle">
                        <option value="Mrs.">خانم</option>
                        <option value="Mr.">آقای</option>
                        <%--<nav class="passenger-info">--%>
                        <%--<ul>--%>
                        <%--<li><a href="#">آقای</a>--%>
                        <%--<ul>--%>
                        <%--<li><a href="#">خانم</a></li>--%>
                        <%--</ul>--%>
                        <%--</li>--%>
                        <%--</ul>--%>
                        <%--</nav>--%>
                </select>

                <div class="col-xs-12 col-sm-12 col-md-3 pull-right place-middle">
                        <input class="passenger-info" name="name-<%=i%>" type="text" placeholder="نام(انگلیسی)" title="first name with characters only">
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right place-middle">
                        <input class="passenger-info" name="surname-<%=i%>" type="text" placeholder="نام خانوادگی(انگلیسی)" title="last name with characters only">
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right place-middle">
                        <input class="passenger-info" name="id-<%=i%>" type="text" placeholder="شماره ملی" title="national id requires only numbers">
                </div>
            </div>
                        <%
            }
        %>
        </div>

        <div class="button-row">
            <div class="hidden-xs hidden-sm hidden-md hidden-lg"><%=flight.getSeatClassCapacity(seatClass.getName())%></div>
            <div class="hidden-xs hidden-sm col-md-4 pull-right"></div>
            <div class="col-xs-6 col-sm-6 col-md-4 pull-right place-middle">
                <button class="bottom-page-btn" id="reject-button">
                    <a href="Home-Search.html">انصراف</a>
                    <%--<span>انصراف</span>--%>
                </button>
            </div>
            <div class="col-xs-6 col-sm-6 col-md-4 pull-right place-middle">
                    <button class="bottom-page-btn" id="pay-button" onclick="checkAndSubmit(<%=totalPassengerCount%>)">
                        <span>پرداخت</span>
                        <span class="hidden-xs hidden-sm"> و ثبت نهایی  ></span>
                    </button>
            </div>
        </div>
        </form>
    </div>

    <div class="filterButtons" id="setPassengerCounts">
        <div class="col-xs-6 col-sm-4 col-md-3  place-middle">
            <span class="inline">تعداد بزرگسالان</span>
            <select name="تعداد بزرگسالان" id="adultCount" class="filterInput">
                <option value=0>۰</option>
                <option value=1>۱</option>
                <option value=2>۲</option>
                <option value=3>۳</option>
                <option value=4>۴</option>
                <option value=5>۵</option>
                <option value=6>۶</option>
                <option value=7>۷</option>
                <option value=8>۸</option>
                <option value=9>۹</option>
            </select>
        </div>
        <div class="col-xs-6 col-sm-4 col-md-3  place-middle">
            <span class="inline">تعداد کودکان</span>
            <select name="تعداد کودکان" id="childCount" class="filterInput">
                <option value=0>۰</option>
                <option value=1>۱</option>
                <option value=2>۲</option>
                <option value=3>۳</option>
                <option value=4>۴</option>
                <option value=5>۵</option>
                <option value=6>۶</option>
                <option value=7>۷</option>
                <option value=8>۸</option>
                <option value=9>۹</option>
            </select>
        </div>
        <div class="col-xs-6 col-sm-4 col-md-3  place-middle">
            <span class="inline">تعداد خردسالان</span>
            <select name="تعداد خردسالان" id="infantCount" class="filterInput">
                <option value=0>۰</option>
                <option value=1>۱</option>
                <option value=2>۲</option>
                <option value=3>۳</option>
                <option value=4>۴</option>
                <option value=5>۵</option>
                <option value=6>۶</option>
                <option value=7>۷</option>
                <option value=8>۸</option>
                <option value=9>۹</option>
            </select>
        </div>
        <div class="col-xs-6 col-sm-12 col-md-3  place-middle">
            <button class="applyFilters" onclick="update()">اعمال فیلتر ها</button>
        </div>
    </div>

</div>

<script type="text/babel" src="Reserve.js"></script>

<%@include file="footer.jsp" %>

</body>
</html>
