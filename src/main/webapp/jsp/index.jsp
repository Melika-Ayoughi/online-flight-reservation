<%--
  Created by IntelliJ IDEA.
  User: melikaayoughi
  Date: 3/17/17
  Time: 08:45
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html lang="ar">
<head>
    <meta charset="utf-8">
    <title>جستجوی بلیت</title>
    <link rel="stylesheet" type="text/css" href="../CSS-Reset.css">
    <link rel="stylesheet" type="text/css" href="../Home-Search.css">


    <link rel="stylesheet" href="../Fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <meta name="description" content="ticket booking">
    <meta name="keywords" content="ticket, book, booking, from, to, akbarticket">
    <meta name="author" content="Melika Ayoughi, Ali Iman">
</head>
<body>
<div class="heading">
    <div class="brand-logo">
        <img src="../Images/LogoWhite.png" alt="">
        اکبر تیکت
        &reg;
    </div>
    <div class="username" id="container">
        <nav>
            <ul>
                <li><a href="#" class="font-medium"><i class="fa fa-user fa-fw"></i> نام کاربر</a>
                    <ul>
                        <li><a href="#">بلیت های من</a></li>
                        <li><a href="#">خروج</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
    </div>
</div>
<div class="line">
    <hr>
</div>
<div class="buttons-search-form">
    <div class="pair-buttons" id="one-or-two-way">
        <button class="clicked-buttons" id="one-way">یک طرفه</button>
        <button class="clicked-buttons" id="two-way">رفت و برگشت</button>
    </div>
    <form class="search-form"  action="Search-Results.do" method="POST">
        <div class="form-line">
            <div class="airplane-logo">
                <i class="fa fa-plane fa-size"></i>
            </div>
            <span>مبدا</span>
            <select class="airport" name="src" id="from">
                <option value="THR">تهران - فرودگاه امام</option>
                <option value="MHD">مشهد - فرودگاه امام</option>
                <option value="IFN">اصفهان - فرودگاه امام</option>
            </select>
            <!--<div class="airport" id="from">-->
            <!--<nav>-->
            <!--<ul>-->
            <!--<li><a href="#">تهران - فرودگاه مهراباد</a>-->
            <!--<ul>-->
            <!--<li><a href="#">تهران - فرودگاه امام</a></li>-->
            <!--<li><a href="#">مشهد - فرودگاه امام</a></li>-->
            <!--<li><a href="#">اصفهان - فرودگاه امام</a></li>-->
            <!--</ul>-->
            <!--</li>-->
            <!--</ul>-->
            <!--</nav>-->
            <!--</div>-->
        </div>
        <div class="form-line">
            <div class="airplane-logo">
                <i class="fa fa-plane fa-rotate-90 fa-size"></i>
            </div>
            <span>مقصد</span>
            <select class="airport" name="dest" id="to">
                <option value="THR">تهران - فرودگاه امام</option>
                <option value="MHD">مشهد - فرودگاه امام</option>
                <option value="IFN">اصفهان - فرودگاه امام</option>
            </select>
            <!--<div class="airport" id="to">-->
            <!--<nav>-->
            <!--<ul>-->
            <!--<li><a href="#">تهران - فرودگاه مهراباد</a>-->
            <!--<ul>-->
            <!--<li><a href="#">تهران - فرودگاه امام</a></li>-->
            <!--<li><a href="#">مشهد - فرودگاه امام</a></li>-->
            <!--<li><a href="#">اصفهان - فرودگاه امام</a></li>-->
            <!--</ul>-->
            <!--</li>-->
            <!--</ul>-->
            <!--</nav>-->
            <!--</div>-->
        </div>
        <div class="form-line">
            <div class="airplane-logo">
                <i class="fa fa-calendar fa-size"></i>
            </div>
            <span>رفت</span>
            <input class="date" type="text" id="departure-date" name="departureDate" value="05Feb" required>
        </div>
        <div class="form-line">
            <div class="airplane-logo">
                <i class="fa fa-calendar fa-size"></i>
            </div>
            <span>برگشت</span>
            <input class="date" type="text" id="return-date" name="returnDate" value="07Feb">
        </div>
        <div class="passenger-counts">
            <div class="age-classes" id="adult">
                <span class="block">بزرگسال</span>
                <div class="age-logo" id="age-adult">
                    <i class="fa fa-user fa-male"></i>
                </div>
                <div class="age-count">
                    <select name="adult-count" class="num-count">
                        <option value="1">۱</option>
                        <option value="2">۲</option>
                        <option value="3">۳</option>
                        <option value="4">۴</option>
                        <option value="5">۴</option>
                        <option value="6">۶</option>
                        <option value="7">۷</option>
                        <option value="8">۸</option>
                        <option value="9">۹</option>
                        <option value="0">۰</option>
                    </select>
                </div>
            </div>
            <div class="age-classes" id="child">
                <span class="block">کودک زیر ۱۲ سال</span>
                <div class="age-logo" id="age-child">
                    <i class="fa fa-user fa-child"></i>
                </div>
                <div class="age-count">
                    <select name="child-count" class="num-count">
                        <option value="0">۰</option>
                        <option value="1">۱</option>
                        <option value="2">۲</option>
                        <option value="3">۳</option>
                        <option value="4">۴</option>
                        <option value="5">۵</option>
                        <option value="6">۶</option>
                        <option value="7">۷</option>
                        <option value="8">۸</option>
                        <option value="9">۹</option>
                    </select>
                </div>
            </div>
            <div class="age-classes" id="infant">
                <span class="block">نوزاد زیر ۲ سال</span>
                <div class="age-logo" id="age-infant">
                    <i class="fa fa-user fa-child"></i>
                </div>
                <div class="age-count">
                    <select name="infant-count" class="num-count">
                        <option value="0">۰</option>
                        <option value="1">۱</option>
                        <option value="2">۲</option>
                        <option value="3">۳</option>
                        <option value="4">۴</option>
                        <option value="5">۵</option>
                        <option value="6">۶</option>
                        <option value="7">۷</option>
                        <option value="8">۸</option>
                        <option value="9">۹</option>
                    </select>
                </div>
            </div>
        </div>
        <div class = "search-button">
            <input type="submit" value="جستجو">
        </div>
    </form>
</div>
<div class="day-logo">
    <div class="slide-show">
        <img src="../Images/HomeSlideShow/8.png" alt="" class="image">
        <img src="../Images/HomeSlideShow/7.png" alt="" class="image">
        <img src="../Images/HomeSlideShow/6.png" alt="" class="image">
        <img src="../Images/HomeSlideShow/5.png" alt="" class="image">
        <img src="../Images/HomeSlideShow/4.png" alt="" class="image">
        <img src="../Images/HomeSlideShow/3.png" alt="" class="image">
        <img src="../Images/HomeSlideShow/2.png" alt="" class="image">
        <img src="../Images/HomeSlideShow/1.png" alt="" class="image">
    </div>
    <span class="cheap-text">سفر ارزان و آسان با اکبر تیکت</span>
</div>
<footer>ملیکا عیوقی، علی ایمان | دانشکده فنی دانشگاه تهران، بهار ۱۳۹۶</footer>
</body>
</html>
