<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Add new meal</title>
    <link rel="stylesheet" href="styles.css" type="text/css">
</head>
<body>

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