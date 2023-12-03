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
	<h1>
	<%
		
		if (session.getAttribute("exist") != null){
			String firstname = null;
			String lastname = null;
			Cookie[] cookies = request.getCookies();
			if(cookies !=null){
				for(Cookie cookie : cookies){
					if(cookie.getName().equals("firstname")) firstname = cookie.getValue();
					if(cookie.getName().equals("lastname")) lastname = cookie.getValue();
				}
			out.println("Welcome " + firstname + " " + lastname);
			}
		}
		else {
			response.sendRedirect("Login.jsp");
		}
	%>
	</h1>
	
</body>
</html>