<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<link rel="stylesheet" href="index.css" />
<title>Insert title here</title>
</head>
<body>
	<%@ include file="Header.jsp" %>
	<form action="register">
		<div>
			
			<label for="name">Name: </label>
			<input type="text" id="name" name="name" />
		</div>
		<div>
			<label for="email">Email: </label>
			<input type="email" id="email" name="email" />
		</div>
		<div>
			<label for="password">Password: </label>
			<input type="password" id="password" name="password" />
		</div>
		<button>Submit</button>
	</form>

</body>
</html>