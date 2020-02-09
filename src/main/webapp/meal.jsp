<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <%--    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>
    <%--    <link type="text/css"--%>
    <%--          href="css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet"/>--%>
    <%--    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>--%>
    <%--    <script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>--%>
    <title>Add new meal</title>
</head>
<body>
<style>
    .field {
        clear: both;
        text-align: right;
        line-height: 25px;
    }

    label {
        float: left;
        padding-right: 10px;
    }

    .main {
        float: left
    }
</style>


<form action="${pageContext.request.contextPath}/meals" method="POST">
    <input type="hidden" name="id" value="${meal.id}">
    <div class="main">
        <div class="field">
            <label for="dateTime">Date/Time:</label>
            <input type="datetime-local" name="dateTime" id="dateTime" value="${meal.dateTime}"/>
        </div>
        <br/>
        <div class="field">
            <label for="description">Description:</label>
            <input type="text" name="description" id="description" size="23px" value="${meal.description}"/>
        </div>
        <br/>
        <div class="field">
            <label for="calories">Calories:</label>
            <input type="text" name="calories" id="calories" size="23px" value="${meal.calories}"/>
        </div>
        <div class="field">
            <input type="submit" value="Добавить еду"/>
        </div>
    </div>
</form>
</body>
</html>