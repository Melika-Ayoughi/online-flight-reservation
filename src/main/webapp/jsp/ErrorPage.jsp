<%--
  Created by IntelliJ IDEA.
  User: melikaayoughi
  Date: 3/6/17
  Time: 19:43
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=UTF-8" import="domain.*"%>

<!DOCTYPE html>
<html lang="ar">
<head>
    <!-- Bootstrap -->
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">

    <title>صفحه ی خطا</title>
    <link rel="stylesheet" type="text/css" href="../CSS-Reset.css">
    <link rel="stylesheet" type="text/css" href="../ErrorPage.css">


    <link rel="stylesheet" href="../Fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <meta name="description" content="enter information">
    <meta name="keywords" content="ticket, book, booking, from, to, akbarticket">
    <meta name="author" content="Melika Ayoughi, Ali Iman">
</head>

<body>
<%@include file="header.jsp" %>

<div class="mylabel">خطایی رخ داده است. شرح آن در زیر قابل مشاهده است:</div>
<div class="under-label"><%= request.getParameter("errorMessage") %></div>

<%@include file="footer.jsp" %>
</body>

</html>