<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Something went wrong</title>
<link rel="stylesheet" href="css/error.css">
</head>
<body>
	<section class="centered">
	  <h1>Oops! Something went wrong...</h1>
	  <div class="container">
	    <div>${err_msg}</div>
	  </div><br>
	  <div><a href="/" class="go_back">Go Home</a></div>
	</section>
</body>
</html>