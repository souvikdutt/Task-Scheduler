<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update note</title>
<link rel="stylesheet" href="css/add.css">
</head>
<body>
	<form class="box" action="modifyNote" method="get">
		<h1>Update Note</h1>
		<span>Note ID</span>
		<input type="text" name="id" value="${id}" readonly>
		<span>Note description</span>
		<input type="text" name="note" value="${note}" >
		<input type="submit" value="Update">
	</form>
</body>
</html>