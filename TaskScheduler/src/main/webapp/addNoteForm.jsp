<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add note</title>
<link rel="stylesheet" href="css/add.css">
</head>
<body>
	<form class="box" action="addNote" method="get">
		<input type="hidden" name="taskId" value="${taskId}" >
		<input type="text" name="note" placeholder="Enter a note" >
		<input type="submit" value="Save">
	</form>
</body>
</html>