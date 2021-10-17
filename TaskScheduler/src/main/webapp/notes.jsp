<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Notes</title>
<link rel="stylesheet" href="css/home.css">
</head>
<body>
<div class="main">
	<div class="nav">
            <span>View<label style="color:blue">Notes</label></span>
            <span class="message">${message}</span>
            <a href="forwardToAddNoteForm?taskId=${taskId}" class="login">&#43; Add Note</a>
    </div>
    <div>
    <label id="taskid">Task ID : <span style="color:dodgerblue">${taskId}</span></label>
	<table>
	<thead>
		<tr>
			<th>ID</th>
			<th>Description</th>
			<th>Modify</th>
			<th>Remove</th>
		</tr>
	<thead>
	<tbody>
		<c:choose>
		  <c:when test="${status eq true}">
		    <c:forEach items="${notes}" var="note">
			<tr>
				<td><c:out value="${note.id}" /></td>
				<td><c:out value="${note.note}" /></td>
	        	<td>
		          <a href="updateNoteForm?id=${note.id}&note=${note.note}" class="status status-update">Update</a>
		        </td>
	        	<td>
		          <a href="deleteNote?taskId=${taskId}&id=${note.id}" class="status status-delete">Delete</a>
		        </td>
			</tr>
			</c:forEach>
		  </c:when>
		  <c:when test="${status eq false}">
		    <tr>
		    	<td>No notes available</td>
		    </tr>
		  </c:when>
		</c:choose>
	</tbody>
	
	</table>
	<div class="go_back"><a href="displayAllTask" class="login">Go back</a></div>
	</div>
	
</div>
</body>
</html>