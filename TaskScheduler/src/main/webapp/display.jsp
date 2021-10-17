<%@page import="com.epam.taskscheduler.service.TaskServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Display Tasks</title>
<link rel="stylesheet" href="css/home.css">
</head>
<body>
<div class="main">
	<div class="nav">
       <span style="color:#8a7c7c">Task<label style="color:#f75454">Scheduler</label></span>
       <span class="message">${message }</span>
       <a href="addTask.jsp" class="login">&#43; Create</a>
    </div>
    
    <div>
	    <div>
	    	<form action="viewTaskByDate" method="post">
			  <input type="date" class="textbox" name="date" placeholder="Search by date">
			  <input type="submit" class="button" value="Search">
			</form>
	    </div>
	<table>
	<thead>
		<tr>
			<th>ID</th>
			<th>Date</th>
			<th>Title</th>
			<th>Start Time</th>
			<th>End Time</th>
			<th>Notes</th>
			<th>Modify</th>
			<th>Remove</th>
		</tr>
	<thead>
	<tbody>
	
		<c:forEach items="${tasks}" var="task">
			<tr>
				<td><c:out value="${task.taskId}" /></td>
				<td><c:out value="${task.date}" /></td>
				<td><c:out value="${task.taskTitle}" /></td>
				<td><c:out value="${task.startTime}" /></td>
				<td><c:out value="${task.endTime}" /></td>
				<td>
	          		<a href="getNotes?taskId=${task.taskId}" class="status status-view">View</a>
	        	</td>
	        	<td>
		          <a href="forwardToUpdateTaskForm?taskId=${task.taskId}" class="status status-update">Update</a>
		        </td>
	        	<td>
		          	<a href="deleteTask?taskId=${task.taskId}" class="status status-delete">Delete</a>
		        </td>
			</tr>
		</c:forEach>
	  
	</tbody>
	</table>
	</div>
	<div class="footer">
            <span>&#xa9; Designed by: Souvik Dutta</span>
    </div>
</div>	
</body>
</html>