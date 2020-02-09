<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h1>Meals</h1>

<jsp:useBean id="meals" scope="request" type="java.util.List"/>
<table style="border-spacing: 30px 7px; text-align: left">
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <tr style="color: ${meal.excess == true ?  'darkred' : 'green'} ">
            <td>${meal.dateTime.format( DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
