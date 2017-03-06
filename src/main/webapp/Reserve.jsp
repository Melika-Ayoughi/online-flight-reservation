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
    <title>ورود اطلاعات مسافران</title>

    <link rel="stylesheet" type="text/css" href="CSS-Reset.css">
    <link rel="stylesheet" type="text/css" href="Reserve.css">


    <link rel="stylesheet" href="Fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <meta name="description" content="enter information">
    <meta name="keywords" content="ticket, book, booking, from, to, akbarticket">
    <meta name="author" content="Melika Ayoughi, Ali Iman">
</head>
<body>
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
        <div id="choose" class="hidden-xs">انتخاب پرواز  ></div>
        <div id="reserve">ورود اطلاعات  ></div>
        <div id="ticket" class="hidden-xs">دریافت بلیت</div>
    </div>
    <div class="toolbar">
        <div class="hidden-xs hidden-sm col-md-1 pull-right"></div>
        <div class="col-xs-6 col-sm-5 col-md-3 pull-right">
            <!--<span class="glyphicon glyphicon-arrow-right" aria-hidden="true">  صفحه ی نتایج </span>-->
            <i class="fa fa-arrow-right" aria-hidden="true"></i>
            <div class="backtosearch"><span class="hidden-xs">برگشت به</span><span>صفحه ی نتایج </span></div>
        </div>
        <div class="hidden-xs col-sm-2 col-md-4 pull-right"></div>
        <div class="col-xs-6 col-sm-5 col-md-3 pull-right">
            <div class="timing">
                <span>زمان </span>
                <span class="hidden-xs">باقی مانده</span>
                <span>: </span>
                <span class="time">۰۴:۵۹</span>
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
                <div class="place-middle"> ایران ایر ۱۷۴۰</div>
                <div class="place-middle"><i class="fa fa-calendar-o" aria-hidden="true"></i> دوشنبه ۱۷ اسفند</div>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-4 pull-right"><div class="place-middle"> تهران ۱۷:۴۰ >> مشهد ۱۸:۵۰ </div></div>
            <div class="col-xs-12 col-sm-12 col-md-4 pull-right">
                <div class="place-middle"><i class="fa fa-suitcase" aria-hidden="true"></i> کلاس اقتصادی</div>
                <div class="place-middle"><i class="fa fa-plane" aria-hidden="true"></i> بوئینگ MD83</div>
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
                    ۱,۳۵۰,۰۰۰ ریال
                </div>
                <div class="hidden-xs col-sm-4 col-md-2 pull-right">
                    ۲ نفر
                </div>
                <div class="col-xs-6 col-sm-4 col-md-2 pull-right">
                    ۲,۷۰۰,۰۰۰ ریال
                </div>
            </div>
            <div class="paycheck-line">
                <div class="col-xs-6 col-sm-4 col-md-6 pull-right">
                    کودک زیر ۱۲ سال
                </div>
                <div class="hidden-xs hidden-sm col-md-2 pull-right">
                    ۱,۰۰۰,۰۰۰ ریال
                </div>
                <div class="hidden-xs col-sm-4 col-md-2 pull-right">
                    ۵ نفر
                </div>
                <div class="col-xs-6 col-sm-4 col-md-2 pull-right">
                    ۵,۰۰۰,۰۰۰ ریال
                </div>
            </div>
            <div class="paycheck-line" id="third-line">
                <div class="col-xs-6 col-sm-4 col-md-6 pull-right">
                    نوزاد زیر ۲ سال
                </div>
                <div class="hidden-xs hidden-sm col-md-2 pull-right">
                    ۲,۵۰۰ ریال
                </div>
                <div class="hidden-xs col-sm-4 col-md-2 pull-right">
                    ۳ نفر
                </div>
                <div class="col-xs-6 col-sm-4 col-md-2 pull-right">
                    ۷,۵۰۰ ریال
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
                    ۷,۷۰۷,۵۰۰ ریال
                </div>
            </div>
        </div>
        <div class="gray-font" id="information">
            اطلاعات
        </div>

        <div class="passenger-info-list">
            <div class="gray-row">
                <div class="col-xs-6 col-sm-6 col-md-2 pull-right">
                    <div class="place-middle">
                        <span class="hidden-mobile"><i class="fa fa-user fa-male"></i></span>
                        ۱- بزرگسال:
                    </div>
                </div>
                <div class="col-xs-6 col-sm-6 col-md-1 pull-right">
                    <div class="place-middle">
                        <nav class="passenger-info">
                            <ul>
                                <li><a href="#">آقای</a>
                                    <ul>
                                        <li><a href="#">خانم</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام خانوادگی(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="شماره ملی" >
                    </div>
                </div>
            </div>
            <div class="gray-row">
                <div class="col-xs-6 col-sm-6 col-md-2 pull-right">
                    <div class="place-middle">
                        <span class="hidden-mobile"><i class="fa fa-user fa-male"></i></span>
                        ۲- بزرگسال:
                    </div>
                </div>
                <div class="col-xs-6 col-sm-6 col-md-1 pull-right">
                    <div class="place-middle">
                        <nav class="passenger-info">
                            <ul>
                                <li><a href="#">آقای</a>
                                    <ul>
                                        <li><a href="#">خانم</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام خانوادگی(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="شماره ملی" >
                    </div>
                </div>
            </div>
            <div class="gray-row">
                <div class="col-xs-6 col-sm-6 col-md-2 pull-right">
                    <div class="place-middle">
                        <span class="hidden-mobile"><i class="fa fa-user fa-child"></i></span>
                        ۳- خردسال:
                    </div>
                </div>
                <div class="col-xs-6 col-sm-6 col-md-1 pull-right">
                    <div class="place-middle">
                        <nav class="passenger-info">
                            <ul>
                                <li><a href="#">آقای</a>
                                    <ul>
                                        <li><a href="#">خانم</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام خانوادگی(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="شماره ملی" >
                    </div>
                </div>
            </div>
            <div class="gray-row">
                <div class="col-xs-6 col-sm-6 col-md-2 pull-right">
                    <div class="place-middle">
                        <span class="hidden-mobile"><i class="fa fa-user fa-child"></i></span>
                        ۴- خردسال:
                    </div>
                </div>
                <div class="col-xs-6 col-sm-6 col-md-1 pull-right">
                    <div class="place-middle">
                        <nav class="passenger-info">
                            <ul>
                                <li><a href="#">آقای</a>
                                    <ul>
                                        <li><a href="#">خانم</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام خانوادگی(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="شماره ملی" >
                    </div>
                </div>
            </div>
            <div class="gray-row">
                <div class="col-xs-6 col-sm-6 col-md-2 pull-right">
                    <div class="place-middle">
                        <span class="hidden-mobile"><i class="fa fa-user fa-child"></i></span>
                        ۵- خردسال:
                    </div>
                </div>
                <div class="col-xs-6 col-sm-6 col-md-1 pull-right">
                    <div class="place-middle">
                        <nav class="passenger-info">
                            <ul>
                                <li><a href="#">آقای</a>
                                    <ul>
                                        <li><a href="#">خانم</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام خانوادگی(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="شماره ملی" >
                    </div>
                </div>
            </div>
            <div class="gray-row">
                <div class="col-xs-6 col-sm-6 col-md-2 pull-right">
                    <div class="place-middle">
                        <span class="hidden-mobile"><i class="fa fa-user fa-child"></i></span>
                        ۶- خردسال:
                    </div>
                </div>
                <div class="col-xs-6 col-sm-6 col-md-1 pull-right">
                    <div class="place-middle">
                        <nav class="passenger-info">
                            <ul>
                                <li><a href="#">آقای</a>
                                    <ul>
                                        <li><a href="#">خانم</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام خانوادگی(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="شماره ملی" >
                    </div>
                </div>
            </div>
            <div class="gray-row">
                <div class="col-xs-6 col-sm-6 col-md-2 pull-right">
                    <div class="place-middle">
                        <span class="hidden-mobile"><i class="fa fa-user fa-child"></i></span>
                        ۷- خردسال:
                    </div>
                </div>
                <div class="col-xs-6 col-sm-6 col-md-1 pull-right">
                    <div class="place-middle">
                        <nav class="passenger-info">
                            <ul>
                                <li><a href="#">آقای</a>
                                    <ul>
                                        <li><a href="#">خانم</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام خانوادگی(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="شماره ملی" >
                    </div>
                </div>
            </div>
            <div class="gray-row">
                <div class="col-xs-6 col-sm-6 col-md-2 pull-right">
                    <div class="place-middle">
                        <span class="hidden-mobile" id="infant-8"><i class="fa fa-user fa-child"></i></span>
                        ۸- نوزاد:
                    </div>
                </div>
                <div class="col-xs-6 col-sm-6 col-md-1 pull-right">
                    <div class="place-middle">
                        <nav class="passenger-info">
                            <ul>
                                <li><a href="#">آقای</a>
                                    <ul>
                                        <li><a href="#">خانم</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام خانوادگی(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="شماره ملی" >
                    </div>
                </div>
            </div>
            <div class="gray-row">
                <div class="col-xs-6 col-sm-6 col-md-2 pull-right">
                    <div class="place-middle">
                        <span class="hidden-mobile" id="infant-9"><i class="fa fa-user fa-child"></i></span>
                        ۹- نوزاد:
                    </div>
                </div>
                <div class="col-xs-6 col-sm-6 col-md-1 pull-right">
                    <div class="place-middle">
                        <nav class="passenger-info">
                            <ul>
                                <li><a href="#">آقای</a>
                                    <ul>
                                        <li><a href="#">خانم</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام خانوادگی(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="شماره ملی" >
                    </div>
                </div>
            </div>
            <div class="gray-row">
                <div class="col-xs-6 col-sm-6 col-md-2 pull-right">
                    <div class="place-middle">
                        <span class="hidden-mobile" id="infant-10"><i class="fa fa-user fa-child"></i></span>
                        ۱۰- نوزاد:
                    </div>
                </div>
                <div class="col-xs-6 col-sm-6 col-md-1 pull-right">
                    <div class="place-middle">
                        <nav class="passenger-info">
                            <ul>
                                <li><a href="#">آقای</a>
                                    <ul>
                                        <li><a href="#">خانم</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="نام خانوادگی(انگلیسی)" >
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 pull-right">
                    <div class="place-middle">
                        <input class="passenger-info" type="text" placeholder="شماره ملی" >
                    </div>
                </div>
            </div>
        </div>
        <div class="button-row">
            <div class="hidden-xs hidden-sm col-md-4 pull-right"></div>
            <div class="col-xs-6 col-sm-6 col-md-4 pull-right">
                <div class="place-middle">
                    <button class="bottom-page-btn" id="reject-button" type="button" onclick="">
                        <span>انصراف</span>
                    </button>
                </div>
            </div>
            <div class="col-xs-6 col-sm-6 col-md-4 pull-right">
                <div class="place-middle">
                    <button class="bottom-page-btn" id="pay-button" type="button" onclick="">
                        <span>پرداخت</span>
                        <span class="hidden-xs hidden-sm"> و ثبت نهایی  ></span>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<footer>ملیکا عیوقی، علی ایمان | دانشکده فنی دانشگاه تهران، بهار ۱۳۹۶</footer>

</body>
</html>
