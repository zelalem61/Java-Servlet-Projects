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
	<form action="login">
		<div>
			<label for="email">Email: </label>
			<input id="email" type="email" name="email"/>
		</div>
		<div>
			<label for="password">Password: </label>
			<input id="password" type="password" name="password"/>
		</div>
		<button>Login</button>
	</form>
</body>
</html>