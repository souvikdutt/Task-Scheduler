<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Task Form</title>
<link rel="stylesheet" href="css/add.css">
</head>
<body>
	
	<form class="box" action="createTask" method="post">
        <h1>Create Task</h1>
        <input type="date" name="date" placeholder="Enter a date">
        <input type="text" name="taskTitle" placeholder="Enter a title">
        <input type="time" name="startTime" placeholder="Start Time">
        <input type="time" name="endTime" placeholder="End Time">
        <input type="submit" value="Save">
        <a href="displayAllTask">Go back</a>
    </form>
    
</body>
</html>