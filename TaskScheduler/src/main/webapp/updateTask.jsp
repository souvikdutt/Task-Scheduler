<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Task</title>
<link rel="stylesheet" href="css/add.css">
</head>
<body>
	<form class="box" action="modifyTask" method="get">
		<h1>Update Task</h1>
		<input type="hidden" name="taskId" value="${task.taskId}">
        <input type="date" name="date" placeholder="Enter a date" value="${task.date}">
        <input type="text" name="taskTitle" placeholder="Enter a title" value="${task.taskTitle}">
        <input type="time" name="startTime" placeholder="Start Time" value="${task.startTime}">
        <input type="time" name="endTime" placeholder="End Time" value="${task.endTime}">
        <input type="submit" value="Update">
        <a href="displayAllTask">Go back</a>
	</form>
</body>
</html>